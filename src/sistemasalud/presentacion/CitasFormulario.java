package sistemasalud.presentacion;

import sistemasalud.datos.CitaCRUD;

public class CitasFormulario extends FormularioModulo {

    public CitasFormulario() {
        super("Citas de pacientes", "Cita",
                new String[]{"Paciente", "Personal médico", "Fecha", "Hora", "Motivo"},
                new String[]{"ID", "Paciente", "Personal médico", "Fecha", "Hora", "Motivo"},
                CitaCRUD.getInstance());
    }
}
