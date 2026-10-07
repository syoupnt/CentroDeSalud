package sistemasalud.datos;

public final class RecetaCRUD extends RegistroCRUD {

    private static final RecetaCRUD INSTANCE = new RecetaCRUD();

    private RecetaCRUD() {
        super("recetas.txt", 4);
    }

    public static RecetaCRUD getInstance() {
        return INSTANCE;
    }
}
