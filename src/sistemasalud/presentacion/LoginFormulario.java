package sistemasalud.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import sistemasalud.datos.UsuarioCRUD;
import sistemasalud.negocio.Login;
import sistemasalud.negocio.model.Usuario;

public class LoginFormulario extends javax.swing.JFrame {
    private int intentos = 0;

    private static final Color COLOR_PRIMARIO = new Color(20, 105, 111);
    private static final Color COLOR_TEXTO = new Color(37, 55, 62);
    private static final Color COLOR_SECUNDARIO = new Color(104, 124, 130);

    /**
     * Creates new form LoginFormulario
     */
    public LoginFormulario() {
        initComponents();
        configurarInterfaz();
    }

    private void configurarInterfaz() {
        setTitle("Centro de Salud Ganimedes | Inicio de sesión");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(new Color(239, 245, 246));
        fondo.setBorder(new EmptyBorder(30, 30, 30, 30));

        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createLineBorder(new Color(220, 230, 232)));

        JPanel marca = new JPanel();
        marca.setLayout(new BoxLayout(marca, BoxLayout.Y_AXIS));
        marca.setPreferredSize(new Dimension(310, 500));
        marca.setBorder(new EmptyBorder(58, 32, 38, 32));
        marca.setOpaque(false);
        JPanel panelMarca = new PanelDegradado();
        panelMarca.setLayout(new BorderLayout());
        panelMarca.add(marca, BorderLayout.CENTER);

        JLabel logo = new JLabel(new LogoCentroSalud(), SwingConstants.CENTER);
        logo.setAlignmentX(CENTER_ALIGNMENT);
        marca.add(logo);
        marca.add(Box.createVerticalStrut(28));

        JLabel nombreCentro = new JLabel("<html><div style='text-align:center'>Centro de Salud<br>Ganimedes</div></html>");
        nombreCentro.setAlignmentX(CENTER_ALIGNMENT);
        nombreCentro.setForeground(Color.WHITE);
        nombreCentro.setFont(new Font("SansSerif", Font.BOLD, 25));
        nombreCentro.setHorizontalAlignment(SwingConstants.CENTER);
        marca.add(nombreCentro);
        marca.add(Box.createVerticalStrut(12));
 
        JLabel lema = new JLabel("<html><div style='text-align:center'>Cuidamos de ti y de tu familia</div></html>");
        lema.setAlignmentX(CENTER_ALIGNMENT);
        lema.setForeground(new Color(220, 241, 239));
        lema.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lema.setHorizontalAlignment(SwingConstants.CENTER);
        marca.add(lema);
        marca.add(Box.createVerticalGlue());

        JLabel pieMarca = new JLabel("ATENCIÓN · SALUD · BIENESTAR");
        pieMarca.setAlignmentX(CENTER_ALIGNMENT);
        pieMarca.setForeground(new Color(201, 230, 228));
        pieMarca.setFont(new Font("SansSerif", Font.BOLD, 10));
        marca.add(pieMarca);

        JPanel formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        formulario.setBackground(Color.WHITE);
        formulario.setBorder(new EmptyBorder(48, 48, 42, 48));

        JLabel sobreTitulo = new JLabel("ACCESO AL SISTEMA");
        sobreTitulo.setForeground(COLOR_PRIMARIO);
        sobreTitulo.setFont(new Font("SansSerif", Font.BOLD, 11));
        sobreTitulo.setAlignmentX(LEFT_ALIGNMENT);
        formulario.add(sobreTitulo);
        formulario.add(Box.createVerticalStrut(10));

        jLabel1.setText("Bienvenido");
        jLabel1.setFont(new Font("SansSerif", Font.BOLD, 29));
        jLabel1.setForeground(COLOR_TEXTO);
        jLabel1.setAlignmentX(LEFT_ALIGNMENT);
        formulario.add(jLabel1);
        formulario.add(Box.createVerticalStrut(8));

        JLabel instrucciones = new JLabel("Ingresa tus datos para continuar.");
        instrucciones.setFont(new Font("SansSerif", Font.PLAIN, 14));
        instrucciones.setForeground(COLOR_SECUNDARIO);
        instrucciones.setAlignmentX(LEFT_ALIGNMENT);
        formulario.add(instrucciones);
        formulario.add(Box.createVerticalStrut(34));

        estilizarEtiqueta(jLabel2, "Usuario");
        formulario.add(jLabel2);
        formulario.add(Box.createVerticalStrut(8));
        estilizarCampo(NombreField);
        NombreField.setToolTipText("Escribe tu nombre de usuario");
        NombreField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        formulario.add(NombreField);
        formulario.add(Box.createVerticalStrut(20));

        estilizarEtiqueta(jLabel3, "Contraseña");
        formulario.add(jLabel3);
        formulario.add(Box.createVerticalStrut(8));
        estilizarCampo(ContrasenaField);
        ContrasenaField.setToolTipText("Escribe tu contraseña");
        ContrasenaField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        formulario.add(ContrasenaField);
        formulario.add(Box.createVerticalStrut(28));

        jButton1.setText("Iniciar sesión");
        jButton1.setFont(new Font("SansSerif", Font.BOLD, 15));
        jButton1.setForeground(Color.WHITE);
        jButton1.setBackground(COLOR_PRIMARIO);
        jButton1.setBorder(BorderFactory.createEmptyBorder(13, 18, 13, 18));
        jButton1.setFocusPainted(false);
        jButton1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jButton1.setAlignmentX(LEFT_ALIGNMENT);
        jButton1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        formulario.add(jButton1);

        JLabel nota = new JLabel("Sistema de gestión del centro de salud");
        nota.setFont(new Font("SansSerif", Font.PLAIN, 12));
        nota.setForeground(COLOR_SECUNDARIO);
        nota.setAlignmentX(LEFT_ALIGNMENT);
        formulario.add(Box.createVerticalStrut(24));
        formulario.add(nota);

        tarjeta.add(panelMarca, BorderLayout.WEST);
        tarjeta.add(formulario, BorderLayout.CENTER);
        fondo.add(tarjeta, BorderLayout.CENTER);
        setContentPane(fondo);
        setMinimumSize(new Dimension(760, 500));
        setSize(840, 560);
        setResizable(false);
        setLocationRelativeTo(null);

        NombreField.addActionListener(event -> ContrasenaField.requestFocusInWindow());
        ContrasenaField.addActionListener(event -> jButton1.doClick());
        getRootPane().setDefaultButton(jButton1);
    }

    private void estilizarEtiqueta(JLabel etiqueta, String texto) {
        etiqueta.setText(texto);
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 13));
        etiqueta.setForeground(COLOR_TEXTO);
        etiqueta.setAlignmentX(LEFT_ALIGNMENT);
    }

    private void estilizarCampo(javax.swing.JTextField campo) {
        campo.setFont(new Font("SansSerif", Font.PLAIN, 15));
        campo.setForeground(COLOR_TEXTO);
        campo.setBackground(new Color(250, 252, 252));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(211, 224, 226)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
    }

    private static final class PanelDegradado extends JPanel {
        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D graphics2D = (Graphics2D) graphics.create();
            graphics2D.setPaint(new GradientPaint(
                    0, 0, new Color(16, 91, 100),
                    getWidth(), getHeight(), new Color(32, 143, 134)));
            graphics2D.fillRect(0, 0, getWidth(), getHeight());
            graphics2D.dispose();
            super.paintComponent(graphics);
        }

        @Override
        public boolean isOpaque() {
            return false;
        }
    }

    private static final class LogoCentroSalud implements Icon {
        @Override
        public void paintIcon(java.awt.Component component, Graphics graphics, int x, int y) {
            Graphics2D graphics2D = (Graphics2D) graphics.create();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setColor(new Color(255, 255, 255, 242));
            graphics2D.fillRoundRect(x, y, getIconWidth(), getIconHeight(), 25, 25);
            graphics2D.setColor(COLOR_PRIMARIO);
            graphics2D.fillRoundRect(x + 32, y + 15, 20, 54, 8, 8);
            graphics2D.fillRoundRect(x + 15, y + 32, 54, 20, 8, 8);
            graphics2D.dispose();
        }

        @Override
        public int getIconWidth() {
            return 84;
        }

        @Override
        public int getIconHeight() {
            return 84;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        NombreField = new javax.swing.JTextField();
        ContrasenaField = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inicio de Sesión");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Cascadia Code", 1, 24)); // NOI18N
        jLabel1.setText("Iniciar Sesión");

        jLabel2.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel2.setText("Nombre:");

        jLabel3.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        jLabel3.setText("Contraseña:");

        NombreField.setToolTipText("Nombre de usuario");

        ContrasenaField.setToolTipText("Contraseña");

        jButton1.setText("Iniciar sesión");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(NombreField)
                                    .addComponent(ContrasenaField, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE))))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(NombreField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(ContrasenaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String nombre = NombreField.getText();
        String contrasena = new String(ContrasenaField.getPassword());
        
        UsuarioCRUD usuarioCRUD = UsuarioCRUD.getInstance();
        ArrayList<Usuario> usuarios = usuarioCRUD.getUsuarios();
        
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equals(nombre) && usuario.getContrasena().equals(contrasena)) {
                Login login = Login.getInstance();
                login.iniciarSesion(usuario);
                
                dispose();
                MenuFormulario formulario = new MenuFormulario();
                formulario.setVisible(true);
                formulario.setLocationRelativeTo(null);
                
                return;
            }
        }
        
        intentos++;
        if (intentos > 2) {
            NombreField.setText("");
            NombreField.setEditable(false);
            ContrasenaField.setText("");
            ContrasenaField.setEditable(false);
            jButton1.setEnabled(false);
        }
        JOptionPane.showMessageDialog(rootPane, "Nombre de usuario o contraseña incorrectas (Intentos: " + intentos + "/3)", "Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField ContrasenaField;
    private javax.swing.JTextField NombreField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
