package sistemasalud.negocio.model;

public class Usuario {
    private final String nombre;
    private final String contrasena;
    
    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    @Override
    public String toString() {
        return "[Nombre: " + nombre + ", Contraseña: " + contrasena + "]";
    }
}
