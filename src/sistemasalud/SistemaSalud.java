package sistemasalud;

import java.io.IOException;
import sistemasalud.datos.UsuarioCRUD;
import sistemasalud.presentacion.LoginFormulario;

public class SistemaSalud {
    public static void main(String[] args) throws IOException {
        UsuarioCRUD.getInstance().inicializarUsuarioPredeterminado();
        LoginFormulario formulario = new LoginFormulario();
        formulario.setVisible(true);
        formulario.setLocationRelativeTo(null);
    }
}
