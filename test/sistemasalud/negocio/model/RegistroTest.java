package sistemasalud.negocio.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class RegistroTest {

    @Test
    public void testRegistroGuardaCamposComoSeIndican() {
        Registro registro = new Registro("Ana", "1990-02-15", "555123", "Calle 1");

        assertEquals(4, registro.cantidadCampos());
        assertArrayEquals(new String[]{"Ana", "1990-02-15", "555123", "Calle 1"}, registro.getCampos());
        assertEquals("Ana", registro.getCampo(0));
        assertEquals("Calle 1", registro.getCampo(3));
    }

    @Test
    public void testRegistroTrimeaCamposYValidaVacios() {
        Registro registro = new Registro("  Ana  ", "1990-02-15", " 555123 ", "Calle 1");

        assertEquals("Ana", registro.getCampo(0));
        assertEquals("555123", registro.getCampo(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistroRechazaCampoEnBlanco() {
        new Registro("Ana", "", "555123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistroRechazaPuntoYComaEnUnCampo() {
        new Registro("Ana;Maria", "1990-02-15");
    }
}
