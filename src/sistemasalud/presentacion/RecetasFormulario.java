package sistemasalud.presentacion;

import sistemasalud.datos.RecetaCRUD;

public class RecetasFormulario extends FormularioModulo {

    public RecetasFormulario() {
        super("Registro de diagnóstico (Receta)", "Receta",
                new String[]{"Paciente", "Personal médico", "Cita", "Medicamento"},
                new String[]{"ID", "Paciente", "Personal médico", "Cita", "Medicamento"},
                RecetaCRUD.getInstance());
    }
}
