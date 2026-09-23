package sistemasalud;

import javax.swing.UnsupportedLookAndFeelException;
import sistemasalud.presentacion.LoginFormulario;

public class SistemaSalud {
    public static void main(String[] args) {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Windows".equals(info.getName())) {
                try {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException ex) {
                    System.getLogger(SistemaSalud.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                } catch (InstantiationException ex) {
                    System.getLogger(SistemaSalud.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                } catch (IllegalAccessException ex) {
                    System.getLogger(SistemaSalud.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    System.getLogger(SistemaSalud.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
                break;
            }
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            LoginFormulario formulario = new LoginFormulario();
            formulario.setVisible(true);
            formulario.setLocationRelativeTo(null);
        });
    }
}
