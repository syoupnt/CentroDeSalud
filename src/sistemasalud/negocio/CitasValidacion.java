package sistemasalud.negocio;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Locale;
import sistemasalud.datos.PacienteCRUD;
import sistemasalud.datos.PersonalCRUD;
import sistemasalud.negocio.model.Registro;

public final class CitasValidacion {

    private CitasValidacion() {
    }

    public static void validarReferencias(String paciente, String personal)
            throws IOException {
        ArrayList<Registro> pacientes = PacienteCRUD.getInstance().getRegistros();
        ArrayList<Registro> personalMedico = PersonalCRUD.getInstance().getRegistros();
        StringBuilder errores = new StringBuilder();

        if (!existeNombre(pacientes, paciente)) {
            errores.append("El paciente \"").append(paciente)
                    .append("\" no se encuentra en la base de datos.");
        }
        if (!existeNombre(personalMedico, personal)) {
            if (errores.length() > 0) {
                errores.append('\n');
            }
            errores.append("El personal médico \"").append(personal)
                    .append("\" no se encuentra en la base de datos.");
        }

        if (errores.length() > 0) {
            throw new IllegalArgumentException(errores.toString());
        }
    }

    private static boolean existeNombre(ArrayList<Registro> registros, String nombre) {
        String nombreNormalizado = normalizar(nombre);
        for (Registro registro : registros) {
            if (normalizar(registro.getCampo(0)).equals(nombreNormalizado)) {
                return true;
            }
        }
        return false;
    }

    private static String normalizar(String valor) {
        return Normalizer.normalize(valor.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT);
    }
}
