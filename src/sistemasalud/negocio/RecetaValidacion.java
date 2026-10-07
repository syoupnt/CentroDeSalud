package sistemasalud.negocio;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Locale;
import sistemasalud.datos.CitaCRUD;
import sistemasalud.datos.MedicamentoCRUD;
import sistemasalud.datos.PacienteCRUD;
import sistemasalud.datos.PersonalCRUD;
import sistemasalud.negocio.model.Registro;

public final class RecetaValidacion {

    private RecetaValidacion() {
    }

    public static String crearReferenciaCita(int id, Registro cita) {
        return "ID " + id + " - " + String.join(" | ", cita.getCampos());
    }

    public static void validarReferencias(String paciente, String personal,
            String referenciaCita, String medicamento) throws IOException {
        ArrayList<Registro> citas = CitaCRUD.getInstance().getRegistros();
        Registro citaSeleccionada = null;
        for (int i = 0; i < citas.size(); i++) {
            if (crearReferenciaCita(i, citas.get(i)).equals(referenciaCita)) {
                citaSeleccionada = citas.get(i);
                break;
            }
        }
        if (citaSeleccionada == null) {
            throw new IllegalArgumentException("Seleccione una cita existente.");
        }
        if (!mismoNombre(paciente, citaSeleccionada.getCampo(0))
                || !mismoNombre(personal, citaSeleccionada.getCampo(1))) {
            throw new IllegalArgumentException(
                    "El paciente y el personal médico deben coincidir con la cita seleccionada.");
        }
        if (!existeNombre(PacienteCRUD.getInstance().getRegistros(), paciente)) {
            throw new IllegalArgumentException(
                    "El paciente \"" + paciente + "\" no se encuentra en la base de datos.");
        }
        if (!existeNombre(PersonalCRUD.getInstance().getRegistros(), personal)) {
            throw new IllegalArgumentException(
                    "El personal médico \"" + personal + "\" no se encuentra en la base de datos.");
        }
        if (!existeNombre(MedicamentoCRUD.getInstance().getRegistros(), medicamento)) {
            throw new IllegalArgumentException(
                    "El medicamento \"" + medicamento + "\" no se encuentra en la base de datos.");
        }
    }

    private static boolean existeNombre(ArrayList<Registro> registros, String nombre) {
        for (Registro registro : registros) {
            if (mismoNombre(registro.getCampo(0), nombre)) {
                return true;
            }
        }
        return false;
    }

    private static boolean mismoNombre(String primero, String segundo) {
        return normalizar(primero).equals(normalizar(segundo));
    }

    private static String normalizar(String valor) {
        return Normalizer.normalize(valor.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT);
    }
}
