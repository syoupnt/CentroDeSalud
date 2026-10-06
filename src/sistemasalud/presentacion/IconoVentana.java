package sistemasalud.presentacion;

import java.net.URL;
import java.awt.Image;
import javax.swing.ImageIcon;

final class IconoVentana {

    private static final String RECURSO_LOGO = "/imagenes/logo.png";

    private IconoVentana() {
    }

    static ImageIcon cargar() {
        URL recurso = IconoVentana.class.getResource(RECURSO_LOGO);
        if (recurso == null) {
            throw new IllegalStateException("No se encontró el logo: " + RECURSO_LOGO);
        }
        return new ImageIcon(recurso);
    }

    static ImageIcon cargarIconoMarco() {
        Image imagen = cargar().getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        return new ImageIcon(imagen);
    }
}
