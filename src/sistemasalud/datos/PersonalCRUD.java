package sistemasalud.datos;

public final class PersonalCRUD extends RegistroCRUD {

    private static final PersonalCRUD INSTANCE = new PersonalCRUD();

    private PersonalCRUD() {
        super("personal.txt", 4);
    }

    public static PersonalCRUD getInstance() {
        return INSTANCE;
    }
}
