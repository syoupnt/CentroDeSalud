package sistemasalud.datos;

public final class PacienteCRUD extends RegistroCRUD {

    private static final PacienteCRUD INSTANCE = new PacienteCRUD();

    private PacienteCRUD() {
        super("pacientes.txt", 4);
    }

    public static PacienteCRUD getInstance() {
        return INSTANCE;
    }
}
