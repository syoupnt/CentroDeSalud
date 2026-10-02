package sistemasalud.datos;

public final class MedicamentoCRUD extends RegistroCRUD {

    private static final MedicamentoCRUD INSTANCE = new MedicamentoCRUD();

    private MedicamentoCRUD() {
        super("medicamentos.txt", 4);
    }

    public static MedicamentoCRUD getInstance() {
        return INSTANCE;
    }
}
