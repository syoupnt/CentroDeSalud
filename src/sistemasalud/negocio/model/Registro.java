package sistemasalud.negocio.model;

import java.util.Arrays;

public final class Registro {

    private final String[] campos;

    public Registro(String... campos) {
        if (campos == null || campos.length == 0) {
            throw new IllegalArgumentException("El registro debe tener al menos un campo.");
        }
        this.campos = Arrays.copyOf(campos, campos.length);
        for (int i = 0; i < this.campos.length; i++) {
            String campo = this.campos[i];
            if (campo == null || campo.isBlank()) {
                throw new IllegalArgumentException("Todos los campos son obligatorios.");
            }
            if (campo.indexOf(';') >= 0 || campo.indexOf('\n') >= 0 || campo.indexOf('\r') >= 0) {
                throw new IllegalArgumentException(
                        "Los campos no pueden contener punto y coma ni saltos de línea.");
            }
            this.campos[i] = campo.trim();
        }
    }

    public int cantidadCampos() {
        return campos.length;
    }

    public String getCampo(int indice) {
        return campos[indice];
    }

    public String[] getCampos() {
        return Arrays.copyOf(campos, campos.length);
    }
}
