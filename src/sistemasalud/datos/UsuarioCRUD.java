package sistemasalud.datos;

import java.io.IOException;
import java.util.ArrayList;
import sistemasalud.negocio.model.Usuario;

public class UsuarioCRUD extends CRUD {
    private static final UsuarioCRUD INSTANCE = new UsuarioCRUD();
    
    private UsuarioCRUD() {
        super("datos/usuarios.txt");
    }
    
    public static UsuarioCRUD getInstance() {
        return INSTANCE;
    }
    
    public synchronized void inicializarUsuarioPredeterminado() throws IOException {
        if (readFullData().isEmpty()) {
            addUsuario(new Usuario("ADMIN", "ADMIN"));
        }
    }

    public synchronized ArrayList<Usuario> getUsuarios() throws IOException {
        inicializarUsuarioPredeterminado();
        ArrayList<Usuario> usuarios = new ArrayList<>();
        ArrayList<String[]> fullData = readFullData();

        for (int i = 0; i < fullData.size(); i++) {
            String[] data = fullData.get(i);
            if (data.length != 2) {
                throw new IOException("El registro " + (i + 1)
                        + " de usuarios.txt debe contener nombre y contraseña.");
            }
            try {
                usuarios.add(new Usuario(data[0], data[1]));
            } catch (IllegalArgumentException ex) {
                throw new IOException("El registro " + (i + 1)
                        + " de usuarios.txt contiene datos inválidos.", ex);
            }
        }
        return usuarios;
    }
    
    public void addUsuario(Usuario user) throws IOException {
        addRow(user.getNombre() + ";" + user.getContrasena());
    }
    
    public void removeUsuario(int index) throws IOException {
        removeRow(index);
    }
    
    public void updateUsuario(int index, Usuario user) throws IOException {
        updateRow(index, user.getNombre() + ";" + user.getContrasena());
    }
}
