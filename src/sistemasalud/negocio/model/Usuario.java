package sistemasalud.negocio.model;

public class Usuario {
    private final String nombre;
    private final String apellido;
    private final int edad;
    
    public Usuario(String nombre, String apellido, int edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getEdad() {
        return edad;
    }

    @Override
    public String toString() {
        return "[Nombre: " + nombre + ", Apellido: " + apellido + ", Edad: " + edad + "]";
    }
}
