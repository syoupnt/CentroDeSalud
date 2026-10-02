package sistemasalud.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import sistemasalud.negocio.Login;
import sistemasalud.negocio.model.Usuario;

public class MenuFormulario extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(MenuFormulario.class.getName());
    private static final Color COLOR_PRIMARIO = new Color(24, 105, 105);
    private static final Color COLOR_FONDO = new Color(239, 246, 246);
    private static final Color COLOR_FONDO_NAVEGACION = new Color(248, 251, 251);
    private static final Color COLOR_HOVER_NAVEGACION = new Color(229, 240, 240);
    private static final Dimension TAMANO_BOTON_MODULO = new Dimension(112, 78);

    private final JDesktopPane escritorio = new JDesktopPane();

    public MenuFormulario() {
        Login login = Login.getInstance();
        Usuario usuario = login.getUsuario();

        setTitle("Centro de Salud Ganimedes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 600));
        setSize(1180, 760);
        setLocationRelativeTo(null);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(COLOR_FONDO);
        contenido.add(crearEncabezado(usuario), BorderLayout.NORTH);

        escritorio.setBackground(COLOR_FONDO);
        escritorio.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        escritorio.add(crearBienvenida(), JDesktopPane.DEFAULT_LAYER);
        contenido.add(escritorio, BorderLayout.CENTER);
        setContentPane(contenido);
    }

    private JPanel crearEncabezado(Usuario usuario) {
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(Color.WHITE);
        encabezado.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 232, 232)));

        JPanel marca = new JPanel(new BorderLayout(12, 0));
        marca.setBackground(Color.WHITE);
        marca.setBorder(BorderFactory.createEmptyBorder(10, 18, 8, 18));

        JLabel insignia = new JLabel("+");
        insignia.setOpaque(true);
        insignia.setBackground(COLOR_PRIMARIO);
        insignia.setForeground(Color.WHITE);
        insignia.setFont(new Font("SansSerif", Font.BOLD, 28));
        insignia.setHorizontalAlignment(SwingConstants.CENTER);
        insignia.setPreferredSize(new Dimension(42, 42));
        marca.add(insignia, BorderLayout.WEST);

        JLabel titulo = new JLabel("Centro de Salud Ganimedes");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(new Color(35, 62, 67));
        marca.add(titulo, BorderLayout.CENTER);

        JPanel sesion = new JPanel(new BorderLayout(14, 0));
        sesion.setOpaque(false);
        JLabel nombreUsuario = new JLabel("Usuario: " + usuario.getNombre());
        nombreUsuario.setForeground(new Color(73, 91, 95));
        sesion.add(nombreUsuario, BorderLayout.CENTER);

        JButton cerrarSesion = new JButton("Cerrar sesión");
        Color colorCerrarSesion = new Color(173, 68, 68);
        cerrarSesion.setFont(new Font("SansSerif", Font.BOLD, 12));
        cerrarSesion.setForeground(Color.WHITE);
        cerrarSesion.setBackground(colorCerrarSesion);
        cerrarSesion.setOpaque(true);
        cerrarSesion.setBorder(BorderFactory.createEmptyBorder(9, 14, 9, 14));
        cerrarSesion.setFocusPainted(false);
        cerrarSesion.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        cerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evento) {
                cerrarSesion.setBackground(new Color(145, 52, 52));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evento) {
                cerrarSesion.setBackground(colorCerrarSesion);
            }
        });
        cerrarSesion.addActionListener(evento -> cerrarSesion());
        sesion.add(cerrarSesion, BorderLayout.EAST);
        marca.add(sesion, BorderLayout.EAST);
        encabezado.add(marca, BorderLayout.NORTH);

        JToolBar navegacion = new JToolBar();
        navegacion.setFloatable(false);
        navegacion.setBackground(COLOR_FONDO_NAVEGACION);
        navegacion.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(232, 239, 239)));
        navegacion.add(crearBotonModulo("Personal médico", TipoIcono.PERSONAL,
                evento -> abrirFormulario("Personal médico", new PersonalFormulario())));
        navegacion.add(crearBotonModulo("Medicamentos", TipoIcono.MEDICAMENTOS,
                evento -> abrirAvisoModulo("Medicamentos")));
        navegacion.add(crearBotonModulo("Pacientes", TipoIcono.PACIENTES,
                evento -> abrirFormulario("Pacientes", new PacientesFormulario())));
        navegacion.add(crearBotonModulo("Citas", TipoIcono.CITAS,
                evento -> abrirFormulario("Citas de pacientes", new CitasFormulario())));

        JButton usuarios = crearBotonModulo("Usuarios", TipoIcono.USUARIOS,
                evento -> abrirFormulario("Usuarios", new UsuariosFormulario()));
        usuarios.setEnabled(usuario.esAdmin());
        navegacion.add(usuarios);
        encabezado.add(navegacion, BorderLayout.SOUTH);
        return encabezado;
    }

    private JButton crearBotonModulo(String texto, TipoIcono tipo, ActionListener accion) {
        JButton boton = new JButton(texto, new IconoModulo(tipo));
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        boton.setForeground(new Color(47, 72, 76));
        boton.setBackground(COLOR_FONDO_NAVEGACION);
        boton.setOpaque(true);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        boton.setMinimumSize(TAMANO_BOTON_MODULO);
        boton.setPreferredSize(TAMANO_BOTON_MODULO);
        boton.setMaximumSize(TAMANO_BOTON_MODULO);
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evento) {
                if (boton.isEnabled()) {
                    boton.setBackground(COLOR_HOVER_NAVEGACION);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evento) {
                boton.setBackground(COLOR_FONDO_NAVEGACION);
            }
        });
        boton.addActionListener(accion);
        return boton;
    }

    private JPanel crearBienvenida() {
        JPanel bienvenida = new JPanel(new BorderLayout(0, 10));
        bienvenida.setOpaque(false);
        bienvenida.setBorder(BorderFactory.createEmptyBorder(70, 30, 70, 30));

        JLabel titulo = new JLabel("Bienvenido al sistema", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        titulo.setForeground(new Color(40, 79, 82));
        bienvenida.add(titulo, BorderLayout.CENTER);

        JLabel instruccion = new JLabel("Selecciona un módulo en la barra superior para comenzar.",
                SwingConstants.CENTER);
        instruccion.setFont(new Font("SansSerif", Font.PLAIN, 15));
        instruccion.setForeground(new Color(91, 111, 114));
        bienvenida.add(instruccion, BorderLayout.SOUTH);
        return bienvenida;
    }

    private void abrirFormulario(String titulo, JFrame formulario) {
        for (JInternalFrame ventana : escritorio.getAllFrames()) {
            if (titulo.equals(ventana.getClientProperty("modulo"))) {
                try {
                    ventana.setSelected(true);
                    ventana.toFront();
                } catch (java.beans.PropertyVetoException ex) {
                    LOGGER.log(Level.WARNING, "No se pudo seleccionar la ventana interna.", ex);
                }
                formulario.dispose();
                return;
            }
        }

        Container contenido = formulario.getContentPane();
        Dimension tamano = formulario.getSize();
        formulario.setContentPane(new JPanel());
        formulario.dispose();

        JInternalFrame ventana = new JInternalFrame(titulo, false, true, false, true);
        ventana.putClientProperty("modulo", titulo);
        ventana.setContentPane(contenido);
        ventana.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        prepararVentanaInterna(ventana, tamano);
        escritorio.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) {
            LOGGER.log(Level.WARNING, "No se pudo seleccionar la ventana interna.", ex);
        }
    }

    private void abrirAvisoModulo(String modulo) {
        JInternalFrame ventana = new JInternalFrame(modulo, false, true, false, true);
        ventana.putClientProperty("modulo", modulo);
        ventana.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        JLabel aviso = new JLabel("<html><div style='text-align:center'>"
                + "<h2>Módulo de " + modulo + "</h2>"
                + "<p>Esta sección estará disponible próximamente.</p></div></html>",
                SwingConstants.CENTER);
        ventana.setContentPane(aviso);
        prepararVentanaInterna(ventana, new Dimension(620, 420));
        escritorio.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) {
            LOGGER.log(Level.WARNING, "No se pudo seleccionar la ventana interna.", ex);
        }
    }

    private void prepararVentanaInterna(JInternalFrame ventana, Dimension tamanoPreferido) {
        Dimension areaDisponible = escritorio.getSize();
        int ancho = tamanoPreferido.width;
        int alto = tamanoPreferido.height;
        ventana.setSize(ancho, alto);
        ventana.setLocation((areaDisponible.width - ancho) / 2, (areaDisponible.height - alto) / 2);
    }

    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Deseas cerrar la sesión actual?",
                "Cerrar sesión",
                JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            dispose();
            LoginFormulario formulario = new LoginFormulario();
            formulario.setVisible(true);
            formulario.setLocationRelativeTo(null);
        }
    }

    private enum TipoIcono {
        PERSONAL, MEDICAMENTOS, PACIENTES, CITAS, USUARIOS
    }

    private static final class IconoModulo implements Icon {

        private final TipoIcono tipo;

        private IconoModulo(TipoIcono tipo) {
            this.tipo = tipo;
        }

        @Override
        public void paintIcon(Component componente, Graphics graphics, int x, int y) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.translate(x, y);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(COLOR_PRIMARIO);
            g.setStroke(new java.awt.BasicStroke(2.4f, java.awt.BasicStroke.CAP_ROUND,
                    java.awt.BasicStroke.JOIN_ROUND));

            switch (tipo) {
                case PERSONAL:
                case USUARIOS:
                    g.drawOval(10, 2, 12, 12);
                    g.drawArc(5, 16, 22, 16, 0, 180);
                    if (tipo == TipoIcono.PERSONAL) {
                        g.drawLine(16, 17, 16, 27);
                        g.drawLine(12, 22, 20, 22);
                    }
                    break;
                case PACIENTES:
                    g.drawOval(4, 5, 10, 10);
                    g.drawOval(18, 5, 10, 10);
                    g.drawArc(1, 18, 16, 12, 0, 180);
                    g.drawArc(15, 18, 16, 12, 0, 180);
                    break;
                case CITAS:
                    g.drawRoundRect(4, 5, 24, 24, 3, 3);
                    g.drawLine(4, 12, 28, 12);
                    g.drawLine(10, 2, 10, 8);
                    g.drawLine(22, 2, 22, 8);
                    g.drawLine(10, 17, 14, 21);
                    g.drawLine(14, 21, 22, 15);
                    break;
                case MEDICAMENTOS:
                    g.rotate(Math.toRadians(-40), 16, 16);
                    g.draw(new RoundRectangle2D.Double(6, 10, 20, 12, 6, 6));
                    g.drawLine(16, 10, 16, 22);
                    break;
                default:
                    throw new IllegalStateException("Tipo de icono desconocido: " + tipo);
            }
            g.dispose();
        }

        @Override
        public int getIconWidth() {
            return 32;
        }

        @Override
        public int getIconHeight() {
            return 32;
        }
    }
}
