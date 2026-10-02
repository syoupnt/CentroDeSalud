package sistemasalud.presentacion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import sistemasalud.datos.RegistroCRUD;
import sistemasalud.negocio.model.Registro;

abstract class FormularioModulo extends JFrame {

    private final String[] campos;
    private final RegistroCRUD crud;
    private final JTextField[] camposAlta;
    private final JTextField[] camposActualizacion;
    private final JTextField idEliminar = new JTextField();
    private final JTextField idActualizar;
    private final DefaultTableModel modelo;

    FormularioModulo(String titulo, String nombreRegistro, String[] campos, String[] columnas,
            RegistroCRUD crud) {
        this.campos = campos.clone();
        this.crud = crud;
        camposAlta = crearCampos(campos.length);
        camposActualizacion = crearCampos(campos.length + 1);
        idActualizar = camposActualizacion[0];

        setTitle("Sistema de Salud " + titulo);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contenido = new JPanel(new BorderLayout(8, 8));
        contenido.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel encabezado = new JLabel(titulo + " CRUD", SwingConstants.CENTER);
        encabezado.setFont(new Font("Cascadia Code", Font.BOLD, 24));
        contenido.add(encabezado, BorderLayout.NORTH);

        JPanel operaciones = new JPanel(new GridLayout(0, 1, 0, 6));
        operaciones.add(crearOperacion("Añadir " + nombreRegistro, campos,
                camposAlta, "Añadir", evento -> agregarRegistro()));
        operaciones.add(crearOperacion("Eliminar " + nombreRegistro,
                new String[]{"ID"}, new JTextField[]{idEliminar}, "Eliminar",
                evento -> eliminarRegistro()));

        String[] camposConId = new String[campos.length + 1];
        camposConId[0] = "ID";
        System.arraycopy(campos, 0, camposConId, 1, campos.length);
        operaciones.add(crearOperacion("Actualizar " + nombreRegistro,
                camposConId, camposActualizacion, "Actualizar",
                evento -> actualizarRegistro()));

        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        JTable tabla = new JTable(modelo);
        tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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
        actualizarTabla();
    }

    private JTextField[] crearCampos(int cantidad) {
        JTextField[] entradas = new JTextField[cantidad];
        for (int i = 0; i < cantidad; i++) {
            entradas[i] = new JTextField();
        }
        return entradas;
    }

    private JPanel crearOperacion(String titulo, String[] etiquetas, JTextField[] entradas,
            String textoBoton, ActionListener listener) {
        JPanel operacion = new JPanel(new BorderLayout(6, 4));
        operacion.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        operacion.add(new JLabel(titulo), BorderLayout.NORTH);

        JPanel controles = new JPanel(new GridLayout(1, etiquetas.length + 1, 6, 0));
        for (int i = 0; i < etiquetas.length; i++) {
            entradas[i].setToolTipText(etiquetas[i]);
            controles.add(entradas[i]);
        }

        JButton boton = new JButton(textoBoton);
        boton.addActionListener(listener);
        controles.add(boton);
        operacion.add(controles, BorderLayout.CENTER);
        return operacion;
    }

    private void agregarRegistro() {
        try {
            crud.addRegistro(new Registro(leerCampos(camposAlta, 0)));
            actualizarTabla();
            limpiarCampos(camposAlta);
        } catch (IOException | IllegalArgumentException ex) {
            mostrarError(ex);
        }
    }

    private void eliminarRegistro() {
        try {
            crud.removeRegistro(leerId(idEliminar));
            actualizarTabla();
            idEliminar.setText("");
        } catch (IOException | IllegalArgumentException | IndexOutOfBoundsException ex) {
            mostrarError(ex);
        }
    }

    private void actualizarRegistro() {
        try {
            int id = leerId(idActualizar);
            crud.updateRegistro(id, new Registro(leerCampos(camposActualizacion, 1)));
            actualizarTabla();
            limpiarCampos(camposActualizacion);
        } catch (IOException | IllegalArgumentException | IndexOutOfBoundsException ex) {
            mostrarError(ex);
        }
    }

    private String[] leerCampos(JTextField[] entradas, int inicio) {
        String[] valores = new String[campos.length];
        for (int i = 0; i < valores.length; i++) {
            valores[i] = entradas[i + inicio].getText();
        }
        return valores;
    }

    private int leerId(JTextField campo) {
        try {
            return Integer.parseInt(campo.getText().trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El ID debe ser un número entero.", ex);
        }
    }

    private void actualizarTabla() {
        try {
            ArrayList<Registro> registros = crud.getRegistros();
            modelo.setRowCount(0);
            for (int i = 0; i < registros.size(); i++) {
                Object[] fila = new Object[campos.length + 1];
                fila[0] = i;
                String[] valores = registros.get(i).getCampos();
                System.arraycopy(valores, 0, fila, 1, valores.length);
                modelo.addRow(fila);
            }
        } catch (IOException ex) {
            mostrarError(ex);
        }
    }

    private void limpiarCampos(JTextField[] entradas) {
        for (JTextField entrada : entradas) {
            entrada.setText("");
        }
    }

    private void mostrarError(Exception ex) {
        JOptionPane.showMessageDialog(this,
                "No se pudo completar la operación: " + ex.getMessage(),
                "Error de datos",
                JOptionPane.ERROR_MESSAGE);
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
