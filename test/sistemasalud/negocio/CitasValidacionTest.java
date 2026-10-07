package sistemasalud.negocio;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CitasValidacionTest {

    private Path pacientesPath;
    private Path personalPath;
    private byte[] pacientesOriginal;
    private byte[] personalOriginal;
    private boolean pacientesExistia;
    private boolean personalExistia;

    @Before
    public void setUp() throws IOException {
        Path datosDir = Path.of(System.getProperty("user.dir"), "datos");
        pacientesPath = datosDir.resolve("pacientes.txt");
        personalPath = datosDir.resolve("personal.txt");

        pacientesExistia = Files.exists(pacientesPath);
        personalExistia = Files.exists(personalPath);
        pacientesOriginal = pacientesExistia ? Files.readAllBytes(pacientesPath) : null;
        personalOriginal = personalExistia ? Files.readAllBytes(personalPath) : null;

        Files.createDirectories(datosDir);
        Files.write(pacientesPath, List.of(
                "Ana García;1990-01-15;555111;Calle 1",
                "Luis Pérez;1985-02-20;555222;Avenida 2"
        ), StandardCharsets.UTF_8);
        Files.write(personalPath, List.of(
                "Dra. Gómez;Cardiología;555333;dra.gomez@clinica.com",
                "Dr. Ruiz;Pediatría;555444;dr.ruiz@clinica.com"
        ), StandardCharsets.UTF_8);
    }

    @After
    public void tearDown() throws IOException {
        if (pacientesExistia) {
            Files.write(pacientesPath, pacientesOriginal);
        } else if (Files.exists(pacientesPath)) {
            Files.delete(pacientesPath);
        }

        if (personalExistia) {
            Files.write(personalPath, personalOriginal);
        } else if (Files.exists(personalPath)) {
            Files.delete(personalPath);
        }
    }

    @Test
    public void testValidarReferenciasAceptaPacienteYPersonalExistentes() throws IOException {
        CitasValidacion.validarReferencias("Ana García", "Dra. Gómez");
    }

    @Test
    public void testValidarReferenciasNormalizaNombreConEspaciosYMayusculas() throws IOException {
        CitasValidacion.validarReferencias("  ana garcía  ", "  DRA. GÓMEZ ");
    }

    @Test
    public void testValidarReferenciasLanzaErrorSiPacienteNoExiste() throws IOException {
        try {
            CitasValidacion.validarReferencias("Paciente Inexistente", "Dra. Gómez");
            fail("Se esperaba una excepción");
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("paciente"));
            assertTrue(ex.getMessage().contains("Paciente Inexistente"));
        }
    }

    @Test
    public void testValidarReferenciasLanzaErrorSiPersonalNoExiste() throws IOException {
        try {
            CitasValidacion.validarReferencias("Ana García", "Personal Inexistente");
            fail("Se esperaba una excepción");
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("personal médico"));
            assertTrue(ex.getMessage().contains("Personal Inexistente"));
        }
    }
}
