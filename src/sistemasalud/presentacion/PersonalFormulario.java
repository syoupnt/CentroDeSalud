package sistemasalud.presentacion;

public class PersonalFormulario extends FormularioModulo {

    public PersonalFormulario() {
        super("Personal médico", "Personal médico",
                new String[]{"Nombre", "Especialidad", "Teléfono", "Correo"},
                new String[]{"ID", "Nombre", "Especialidad", "Teléfono", "Correo"});
    }
}
