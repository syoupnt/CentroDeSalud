package sistemasalud.datos;

public final class CitaCRUD extends RegistroCRUD {

    private static final CitaCRUD INSTANCE = new CitaCRUD();

    private CitaCRUD() {
        super("citas.txt", 5);
    }

    public static CitaCRUD getInstance() {
        return INSTANCE;
    }
}
