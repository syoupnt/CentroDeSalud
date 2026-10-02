package sistemasalud.presentacion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

abstract class FormularioModulo extends JFrame {

    FormularioModulo(String titulo, String nombreRegistro, String[] campos, String[] columnas) {
        setTitle("Sistema de Salud " + titulo);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contenido = new JPanel(new BorderLayout(8, 8));
        contenido.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel encabezado = new JLabel(titulo + " CRUD", SwingConstants.CENTER);
        encabezado.setFont(new Font("Cascadia Code", Font.BOLD, 24));
        contenido.add(encabezado, BorderLayout.NORTH);

        JPanel operaciones = new JPanel(new GridLayout(0, 1, 0, 6));
        operaciones.add(crearOperacion("Añadir " + nombreRegistro,
                campos, "Añadir"));
        operaciones.add(crearOperacion("Eliminar " + nombreRegistro,
                new String[]{"ID"}, "Eliminar"));

        String[] camposActualizacion = new String[campos.length + 1];
        camposActualizacion[0] = "ID";
        System.arraycopy(campos, 0, camposActualizacion, 1, campos.length);
        operaciones.add(crearOperacion("Actualizar " + nombreRegistro,
                camposActualizacion, "Actualizar"));

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        JTable tabla = new JTable(modelo);
        JPanel cuerpo = new JPanel(new BorderLayout(0, 8));
        cuerpo.add(operaciones, BorderLayout.NORTH);
        cuerpo.add(new JScrollPane(tabla), BorderLayout.CENTER);
        contenido.add(cuerpo, BorderLayout.CENTER);

        JButton atras = new JButton("Atrás");
        atras.addActionListener(crearAccionAtras());
        JPanel pie = new JPanel(new BorderLayout());
        pie.add(atras, BorderLayout.EAST);
        contenido.add(pie, BorderLayout.SOUTH);

        setContentPane(contenido);
        setSize(new Dimension(760, 560));
        setLocationRelativeTo(null);
    }

    private JPanel crearOperacion(String titulo, String[] campos, String textoBoton) {
        JPanel operacion = new JPanel(new BorderLayout(6, 4));
        operacion.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        operacion.add(new JLabel(titulo), BorderLayout.NORTH);

        JPanel controles = new JPanel(new GridLayout(1, campos.length + 1, 6, 0));
        for (String campo : campos) {
            javax.swing.JTextField entrada = new javax.swing.JTextField();
            entrada.setToolTipText(campo);
            controles.add(entrada);
        }

        JButton accion = new JButton(textoBoton);
        accion.setEnabled(false);
        accion.setToolTipText("Interfaz visual; esta operación aún no está disponible.");
        controles.add(accion);
        operacion.add(controles, BorderLayout.CENTER);
        return operacion;
    }

    private ActionListener crearAccionAtras() {
        return evento -> {
            java.awt.Component componente = (java.awt.Component) evento.getSource();
            java.awt.Container ventanaInterna = SwingUtilities.getAncestorOfClass(
                    javax.swing.JInternalFrame.class, componente);
            if (ventanaInterna instanceof javax.swing.JInternalFrame) {
                ((javax.swing.JInternalFrame) ventanaInterna).dispose();
                return;
            }

            dispose();
            MenuFormulario menu = new MenuFormulario();
            menu.setVisible(true);
            menu.setLocationRelativeTo(null);
        };
    }
}
