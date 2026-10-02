package sistemasalud.datos;

import java.io.IOException;
import java.util.ArrayList;
import sistemasalud.negocio.model.Registro;

public abstract class RegistroCRUD extends CRUD {

    private final int cantidadCampos;
    private final String nombreArchivo;

    protected RegistroCRUD(String archivo, int cantidadCampos) {
        super("datos/" + archivo);
        if (cantidadCampos < 1) {
            throw new IllegalArgumentException("El registro debe tener al menos un campo.");
        }
        this.cantidadCampos = cantidadCampos;
        this.nombreArchivo = archivo;
    }

    public final ArrayList<Registro> getRegistros() throws IOException {
        ArrayList<String[]> datos = readFullData();
        ArrayList<Registro> registros = new ArrayList<>(datos.size());
        for (int i = 0; i < datos.size(); i++) {
            String[] campos = datos.get(i);
            if (campos.length != cantidadCampos) {
                throw new IOException("El registro " + (i + 1) + " de " + nombreArchivo
                        + " debe contener " + cantidadCampos + " campos.");
            }
            try {
                registros.add(new Registro(campos));
            } catch (IllegalArgumentException ex) {
                throw new IOException("El registro " + (i + 1) + " de " + nombreArchivo
                        + " contiene campos inválidos.", ex);
            }
        }
        return registros;
    }

    public final void addRegistro(Registro registro) throws IOException {
        validarRegistro(registro);
        addRow(serializar(registro));
    }

    public final void updateRegistro(int id, Registro registro) throws IOException {
        validarRegistro(registro);
        updateRow(id, serializar(registro));
    }

    public final void removeRegistro(int id) throws IOException {
        removeRow(id);
    }

    private void validarRegistro(Registro registro) {
        if (registro == null || registro.cantidadCampos() != cantidadCampos) {
            throw new IllegalArgumentException("El registro debe contener "
                    + cantidadCampos + " campos.");
        }
    }

    private String serializar(Registro registro) {
        return String.join(";", registro.getCampos());
    }
}
