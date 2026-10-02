package sistemasalud.presentacion;

import sistemasalud.datos.PacienteCRUD;

public class PacientesFormulario extends FormularioModulo {

    public PacientesFormulario() {
        super("Pacientes", "Paciente",
                new String[]{"Nombre", "Fecha de nacimiento", "Teléfono", "Dirección"},
                new String[]{"ID", "Nombre", "Fecha de nacimiento", "Teléfono", "Dirección"},
                PacienteCRUD.getInstance());
    }
}
