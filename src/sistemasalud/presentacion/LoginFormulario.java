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
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import sistemasalud.datos.UsuarioCRUD;
import sistemasalud.negocio.Login;
import sistemasalud.negocio.model.Usuario;

public class LoginFormulario extends javax.swing.JFrame {
    private static final Color COLOR_PRIMARIO = new Color(20, 105, 111);
    private static final Color COLOR_TEXTO = new Color(37, 55, 62);
    private static final Color COLOR_SECUNDARIO = new Color(104, 124, 130);

    private final JLabel etiquetaBienvenida = new JLabel();
    private final JLabel etiquetaUsuario = new JLabel();
    private final JLabel etiquetaContrasena = new JLabel();
    private final JTextField campoUsuario = new JTextField();
    private final JPasswordField campoContrasena = new JPasswordField();
    private final JButton botonIniciarSesion = new JButton();
    private int intentosFallidos;

    public LoginFormulario() {
        configurarInterfaz();
    }

    private void configurarInterfaz() {
        setTitle("Centro de Salud Ganimedes | Inicio de sesión");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Color.WHITE);

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

        etiquetaBienvenida.setText("Bienvenido");
        etiquetaBienvenida.setFont(new Font("SansSerif", Font.BOLD, 29));
        etiquetaBienvenida.setForeground(COLOR_TEXTO);
        etiquetaBienvenida.setAlignmentX(LEFT_ALIGNMENT);
        formulario.add(etiquetaBienvenida);
        formulario.add(Box.createVerticalStrut(8));

        JLabel instrucciones = new JLabel("Ingresa tus datos para continuar.");
        instrucciones.setFont(new Font("SansSerif", Font.PLAIN, 14));
        instrucciones.setForeground(COLOR_SECUNDARIO);
        instrucciones.setAlignmentX(LEFT_ALIGNMENT);
        formulario.add(instrucciones);
        formulario.add(Box.createVerticalStrut(34));

        estilizarEtiqueta(etiquetaUsuario, "Usuario");
        formulario.add(etiquetaUsuario);
        formulario.add(Box.createVerticalStrut(8));
        estilizarCampo(campoUsuario);
        campoUsuario.setToolTipText("Escribe tu nombre de usuario");
        campoUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        formulario.add(campoUsuario);
        formulario.add(Box.createVerticalStrut(20));

        estilizarEtiqueta(etiquetaContrasena, "Contraseña");
        formulario.add(etiquetaContrasena);
        formulario.add(Box.createVerticalStrut(8));
        estilizarCampo(campoContrasena);
        campoContrasena.setToolTipText("Escribe tu contraseña");
        campoContrasena.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        formulario.add(campoContrasena);
        formulario.add(Box.createVerticalStrut(28));

        botonIniciarSesion.setText("Iniciar sesión");
        botonIniciarSesion.setFont(new Font("SansSerif", Font.BOLD, 15));
        botonIniciarSesion.setForeground(Color.WHITE);
        botonIniciarSesion.setBackground(COLOR_PRIMARIO);
        botonIniciarSesion.setBorder(BorderFactory.createEmptyBorder(13, 18, 13, 18));
        botonIniciarSesion.setFocusPainted(false);
        botonIniciarSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botonIniciarSesion.setAlignmentX(LEFT_ALIGNMENT);
        botonIniciarSesion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        botonIniciarSesion.addActionListener(event -> iniciarSesion());
        formulario.add(botonIniciarSesion);

        JLabel nota = new JLabel("Sistema de gestión del centro de salud");
        nota.setFont(new Font("SansSerif", Font.PLAIN, 12));
        nota.setForeground(COLOR_SECUNDARIO);
        nota.setAlignmentX(LEFT_ALIGNMENT);
        formulario.add(Box.createVerticalStrut(24));
        formulario.add(nota);

        tarjeta.add(panelMarca, BorderLayout.WEST);
        tarjeta.add(formulario, BorderLayout.CENTER);
        add(tarjeta);
        setSize(840, 560);
        setResizable(false);
        setLocationRelativeTo(null);

        campoUsuario.addActionListener(event -> campoContrasena.requestFocusInWindow());
        campoContrasena.addActionListener(event -> botonIniciarSesion.doClick());
        getRootPane().setDefaultButton(botonIniciarSesion);
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
        campo.setBorder(BorderFactory.createLineBorder(new Color(211, 224, 226)));
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

    private void iniciarSesion() {
        String nombre = campoUsuario.getText();
        String contrasena = new String(campoContrasena.getPassword());
        
        UsuarioCRUD usuarioCRUD = UsuarioCRUD.getInstance();
        ArrayList<Usuario> usuarios;
        try {
            usuarios = usuarioCRUD.getUsuarios();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudieron cargar los usuarios: " + ex.getMessage(),
                    "Error de datos",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
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
        
        intentosFallidos++;
        if (intentosFallidos > 2) {
            campoUsuario.setText("");
            campoUsuario.setEditable(false);
            campoContrasena.setText("");
            campoContrasena.setEditable(false);
            botonIniciarSesion.setEnabled(false);
        }
        JOptionPane.showMessageDialog(rootPane, "Nombre de usuario o contraseña incorrectas (Intentos: " + intentosFallidos + "/3)", "Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
    }
}
