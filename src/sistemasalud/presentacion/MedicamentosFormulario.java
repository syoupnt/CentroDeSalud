package sistemasalud.presentacion;

import sistemasalud.datos.MedicamentoCRUD;

public class MedicamentosFormulario extends FormularioModulo {

    public MedicamentosFormulario() {
        super("Medicamentos", "Medicamento",
                new String[]{"Nombre", "Presentación", "Existencia", "Indicaciones"},
                new String[]{"ID", "Nombre", "Presentación", "Existencia", "Indicaciones"},
                MedicamentoCRUD.getInstance());
    }
}
