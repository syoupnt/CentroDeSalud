package sistemasalud.negocio.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class UsuarioTest {

    @Test
    public void testUsuarioValidoSeCreaConNombreYContrasena() {
        Usuario usuario = new Usuario("juan", "secreto");

        assertEquals("juan", usuario.getNombre());
        assertEquals("secreto", usuario.getContrasena());
        assertFalse(usuario.esAdmin());
    }

    @Test
    public void testAdminSeDetectaCorrectamente() {
        Usuario usuario = new Usuario("ADMIN", "ADMIN");

        assertTrue(usuario.esAdmin());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUsuarioRechazaNombreVacio() {
        new Usuario("   ", "abc");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUsuarioRechazaContrasenaConSaltoDeLinea() {
        new Usuario("juan", "abc\n123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUsuarioRechazaNombreConPuntoYComa() {
        new Usuario("juan;admin", "abc");
    }
}
