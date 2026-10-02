package sistemasalud.negocio.model;

public class Usuario {
    private final String nombre;
    private final String contrasena;
    
    public Usuario(String nombre, String contrasena) {
        validarCampo(nombre, "El nombre de usuario");
        validarCampo(contrasena, "La contraseña");
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    private static void validarCampo(String valor, String etiqueta) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(etiqueta + " no puede estar vacío.");
        }
        if (valor.indexOf(';') >= 0 || valor.indexOf('\n') >= 0 || valor.indexOf('\r') >= 0) {
            throw new IllegalArgumentException(etiqueta
                    + " no puede contener punto y coma ni saltos de línea.");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getContrasena() {
        return contrasena;
    }
    
    public boolean esAdmin() {
        return nombre.equals("ADMIN") && contrasena.equals("ADMIN");
    }

    @Override
    public String toString() {
        return "[Nombre: " + nombre + ", Contraseña: " + contrasena + "]";
    }
}
