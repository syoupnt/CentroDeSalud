package sistemasalud.negocio;

import org.junit.Test;
import static org.junit.Assert.*;

public class TelefonoValidacionTest {

    @Test
    public void testAceptaTelefonoInternacionalConSeparadores() {
        TelefonoValidacion.validar("+52 (55) 1234-5678");
    }

    @Test
    public void testAceptaLimitesDeLongitud() {
        TelefonoValidacion.validar("1234567");
        TelefonoValidacion.validar("+123456789012345");
    }

    @Test
    public void testRechazaTelefonoConLetras() {
        assertInvalid("555-123-ABCD");
    }

    @Test
    public void testRechazaTelefonoDemasiadoCorto() {
        assertInvalid("123456");
    }

    @Test
    public void testRechazaPrefijoInternacionalEnPosicionInvalida() {
        assertInvalid("55+12345678");
    }

    @Test
    public void testRechazaParentesisSinCerrar() {
        assertInvalid("(555) 123-4567(");
    }

    @Test
    public void testRechazaTelefonoDemasiadoLargo() {
        assertInvalid("1234567890123456");
    }

    private void assertInvalid(String telefono) {
        try {
            TelefonoValidacion.validar(telefono);
            fail("Se esperaba que el teléfono inválido fuera rechazado.");
        } catch (IllegalArgumentException ex) {
            assertNotNull(ex.getMessage());
        }
    }
}
