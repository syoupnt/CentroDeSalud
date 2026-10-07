package sistemasalud.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import sistemasalud.datos.CitaCRUD;
import sistemasalud.datos.MedicamentoCRUD;
import sistemasalud.datos.PacienteCRUD;
import sistemasalud.datos.PersonalCRUD;
import sistemasalud.datos.RecetaCRUD;
import sistemasalud.datos.RegistroCRUD;
import sistemasalud.negocio.CitasValidacion;
import sistemasalud.negocio.RecetaValidacion;
import sistemasalud.negocio.TelefonoValidacion;
import sistemasalud.negocio.model.Registro;

abstract class FormularioModulo extends JFrame {

    private final String[] campos;
    private final RegistroCRUD crud;
    private final CampoEntrada[] camposAlta;
    private final CampoEntrada[] camposActualizacion;
    private final CampoEntrada idEliminar = new CampoEntrada("ID");
    private final CampoEntrada idActualizar;
    private final DefaultTableModel modelo;
    private final boolean requiereReferencias;

    FormularioModulo(String titulo, String nombreRegistro, String[] campos, String[] columnas,
            RegistroCRUD crud) {
        this.campos = campos.clone();
        this.crud = crud;
        requiereReferencias = contieneCampo("Paciente") || contieneCampo("Personal médico")
                || contieneCampo("Cita") || contieneCampo("Medicamento");
        camposAlta = crearCampos(campos);
        String[] camposConId = new String[campos.length + 1];
        camposConId[0] = "ID";
        System.arraycopy(campos, 0, camposConId, 1, campos.length);
        camposActualizacion = crearCampos(camposConId);
        idActualizar = camposActualizacion[0];

        setTitle("Sistema de Salud " + titulo);
        setIconImage(IconoVentana.cargar().getImage());
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
                new String[]{"ID"}, new CampoEntrada[]{idEliminar}, "Eliminar",
                evento -> eliminarRegistro()));

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
        if (requiereReferencias) {
            try {
                actualizarOpcionesReferencias();
            } catch (IOException ex) {
                mostrarError(ex);
            }
        }
        actualizarTabla();
    }

    private boolean contieneCampo(String nombre) {
        for (String campo : campos) {
            if (campo.equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    private static boolean esCampoReferencia(String etiqueta) {
        return etiqueta.equals("Paciente") || etiqueta.equals("Personal médico")
                || etiqueta.equals("Cita") || etiqueta.equals("Medicamento");
    }

    private JComponent crearControl(CampoEntrada entrada, String etiqueta) {
        JComponent componente = entrada.crearComponente(etiqueta);
        if (componente instanceof JComboBox<?>) {
            ((JComboBox<?>) componente).addPopupMenuListener(new PopupMenuListener() {
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent evento) {
                    try {
                        actualizarOpcionesReferencias();
                    } catch (IOException ex) {
                        mostrarError(ex);
                    }
                }

                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent evento) {
                }

                @Override
                public void popupMenuCanceled(PopupMenuEvent evento) {
                }
            });
        }
        return componente;
    }

    private CampoEntrada[] crearCampos(String[] etiquetas) {
        CampoEntrada[] entradas = new CampoEntrada[etiquetas.length];
        for (int i = 0; i < etiquetas.length; i++) {
            entradas[i] = new CampoEntrada(etiquetas[i]);
        }
        return entradas;
    }

    private JPanel crearOperacion(String titulo, String[] etiquetas, CampoEntrada[] entradas,
            String textoBoton, ActionListener listener) {
        JPanel operacion = new JPanel(new BorderLayout(6, 4));
        operacion.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        operacion.add(new JLabel(titulo), BorderLayout.NORTH);

        JPanel controles = new JPanel(new GridLayout(1, etiquetas.length + 1, 6, 0));
        for (int i = 0; i < etiquetas.length; i++) {
            controles.add(crearControl(entradas[i], etiquetas[i]));
        }

        JButton boton = new JButton(textoBoton);
        boton.addActionListener(listener);
        controles.add(boton);
        operacion.add(controles, BorderLayout.CENTER);
        return operacion;
    }

    private void agregarRegistro() {
        try {
            actualizarOpcionesReferencias();
            String[] valores = leerCampos(camposAlta, 0);
            validarRegistro(valores);
            crud.addRegistro(new Registro(valores));
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
            idEliminar.limpiar();
        } catch (IOException | IllegalArgumentException | IndexOutOfBoundsException ex) {
            mostrarError(ex);
        }
    }

    private void actualizarRegistro() {
        try {
            actualizarOpcionesReferencias();
            int id = leerId(idActualizar);
            String[] valores = leerCampos(camposActualizacion, 1);
            validarRegistro(valores);
            crud.updateRegistro(id, new Registro(valores));
            actualizarTabla();
            limpiarCampos(camposActualizacion);
        } catch (IOException | IllegalArgumentException | IndexOutOfBoundsException ex) {
            mostrarError(ex);
        }
    }

    private void validarRegistro(String[] valores) throws IOException {
        for (int i = 0; i < campos.length; i++) {
            if (campos[i].equals("Teléfono")) {
                TelefonoValidacion.validar(valores[i]);
            }
        }
        if (crud instanceof CitaCRUD) {
            CitasValidacion.validarReferencias(valores[0], valores[1]);
        } else if (crud instanceof RecetaCRUD) {
            RecetaValidacion.validarReferencias(
                    valores[0], valores[1], valores[2], valores[3]);
        }
    }

    private String[] leerCampos(CampoEntrada[] entradas, int inicio) {
        String[] valores = new String[campos.length];
        for (int i = 0; i < valores.length; i++) {
            valores[i] = entradas[i + inicio].getValor();
        }
        return valores;
    }

    private int leerId(CampoEntrada campo) {
        try {
            return Integer.parseInt(campo.getValor().trim());
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

    private void actualizarOpcionesReferencias() throws IOException {
        if (!requiereReferencias) {
            return;
        }
        ArrayList<Registro> citas = CitaCRUD.getInstance().getRegistros();
        if (contieneCampo("Paciente")) {
            ArrayList<Referencia> pacientes = cargarReferencias(
                    PacienteCRUD.getInstance().getRegistros(), 0);
            for (Registro cita : citas) {
                agregarReferenciaAnterior(pacientes, cita.getCampo(0), "Paciente");
            }
            agregarReferenciasAnteriores(pacientes, "Paciente");
            establecerOpciones("Paciente", pacientes);
        }
        if (contieneCampo("Personal médico")) {
            ArrayList<Referencia> personal = cargarReferencias(
                    PersonalCRUD.getInstance().getRegistros(), 0);
            for (Registro cita : citas) {
                agregarReferenciaAnterior(personal, cita.getCampo(1), "Personal médico");
            }
            agregarReferenciasAnteriores(personal, "Personal médico");
            establecerOpciones("Personal médico", personal);
        }
        if (contieneCampo("Cita")) {
            ArrayList<Referencia> referenciasCitas = new ArrayList<>(citas.size());
            for (int i = 0; i < citas.size(); i++) {
                String valor = RecetaValidacion.crearReferenciaCita(i, citas.get(i));
                referenciasCitas.add(new Referencia(valor, valor));
            }
            agregarReferenciasAnteriores(referenciasCitas, "Cita");
            establecerOpciones("Cita", referenciasCitas);
        }
        if (contieneCampo("Medicamento")) {
            ArrayList<Referencia> medicamentos = cargarReferencias(
                    MedicamentoCRUD.getInstance().getRegistros(), 0);
            agregarReferenciasAnteriores(medicamentos, "Medicamento");
            establecerOpciones("Medicamento", medicamentos);
        }
    }

    private ArrayList<Referencia> cargarReferencias(
            List<Registro> registros, int campoNombre) {
        ArrayList<Referencia> referencias = new ArrayList<>(registros.size());
        for (int i = 0; i < registros.size(); i++) {
            String nombre = registros.get(i).getCampo(campoNombre);
            referencias.add(new Referencia(nombre, "ID " + i + " - " + nombre));
        }
        return referencias;
    }

    private void agregarReferenciaAnterior(ArrayList<Referencia> referencias,
            String nombre, String tipo) {
        for (Referencia referencia : referencias) {
            if (referencia.getValor().equals(nombre)) {
                return;
            }
        }
        referencias.add(new Referencia(nombre, "No disponible (" + tipo + "): " + nombre));
    }

    private void agregarReferenciasAnteriores(ArrayList<Referencia> referencias,
            String etiqueta) throws IOException {
        for (Registro registro : crud.getRegistros()) {
            String valor = registro.getCampo(indiceCampo(etiqueta));
            agregarReferenciaAnterior(referencias, valor, etiqueta);
        }
    }

    private int indiceCampo(String etiqueta) {
        for (int i = 0; i < campos.length; i++) {
            if (campos[i].equals(etiqueta)) {
                return i;
            }
        }
        throw new IllegalArgumentException("El campo no existe: " + etiqueta);
    }

    private void establecerOpciones(String etiqueta, List<Referencia> opciones) {
        for (CampoEntrada entrada : camposAlta) {
            if (entrada.etiqueta.equals(etiqueta)) {
                entrada.establecerOpciones(opciones);
            }
        }
        for (CampoEntrada entrada : camposActualizacion) {
            if (entrada.etiqueta.equals(etiqueta)) {
                entrada.establecerOpciones(opciones);
            }
        }
    }

    private void limpiarCampos(CampoEntrada[] entradas) {
        for (CampoEntrada entrada : entradas) {
            entrada.limpiar();
        }
    }

    private void mostrarError(Exception ex) {
        JOptionPane.showMessageDialog(this,
                "No se pudo completar la operación: " + ex.getMessage(),
                "Error de datos",
                JOptionPane.ERROR_MESSAGE);
    }

    private static final class CampoEntrada {

        private final String etiqueta;
        private final JComponent componente;

        private CampoEntrada(String etiqueta) {
            this.etiqueta = etiqueta;
            if (etiqueta.equals("Fecha") || etiqueta.equals("Fecha de nacimiento")) {
                componente = crearSelectorFecha();
            } else if (etiqueta.equals("Hora")) {
                componente = crearSelectorHora();
            } else if (esCampoReferencia(etiqueta)) {
                componente = new BuscadorReferencia();
            } else {
                componente = new JTextField();
            }
        }

        private JComponent crearComponente(String textoAyuda) {
            componente.setToolTipText(textoAyuda);
            return componente;
        }

        private String getValor() {
            if (componente instanceof JSpinner) {
                JSpinner selector = (JSpinner) componente;
                String formato = etiqueta.equals("Hora") ? "HH:mm" : "yyyy-MM-dd";
                return new SimpleDateFormat(formato).format((Date) selector.getValue());
            }
            if (componente instanceof BuscadorReferencia) {
                return ((BuscadorReferencia) componente).getTexto();
            }
            if (componente instanceof JComboBox<?>) {
                Object seleccionado = ((JComboBox<?>) componente).getSelectedItem();
                return seleccionado instanceof Referencia
                        ? ((Referencia) seleccionado).getValor() : "";
            }
            return ((JTextField) componente).getText();
        }

        private void establecerOpciones(List<Referencia> opciones) {
            if (componente instanceof BuscadorReferencia) {
                ((BuscadorReferencia) componente).establecerOpciones(opciones);
                return;
            }
            if (!(componente instanceof JComboBox<?>)) {
                return;
            }

            @SuppressWarnings("unchecked")
            JComboBox<Referencia> selector = (JComboBox<Referencia>) componente;
            String valorActual = getValor();
            selector.removeAllItems();
            selector.addItem(new Referencia("", "(Seleccionar " + etiqueta.toLowerCase() + ")"));
            for (Referencia opcion : opciones) {
                selector.addItem(opcion);
            }
            for (int i = 0; i < selector.getItemCount(); i++) {
                if (selector.getItemAt(i).getValor().equals(valorActual)) {
                    selector.setSelectedIndex(i);
                    return;
                }
            }
            selector.setSelectedIndex(0);
        }

        private void limpiar() {
            if (componente instanceof JSpinner) {
                Calendar ahora = Calendar.getInstance();
                if (etiqueta.equals("Hora")) {
                    ahora.set(Calendar.SECOND, 0);
                    ahora.set(Calendar.MILLISECOND, 0);
                } else {
                    ahora.set(Calendar.HOUR_OF_DAY, 0);
                    ahora.set(Calendar.MINUTE, 0);
                    ahora.set(Calendar.SECOND, 0);
                    ahora.set(Calendar.MILLISECOND, 0);
                }
                ((JSpinner) componente).setValue(ahora.getTime());
            } else if (componente instanceof BuscadorReferencia) {
                ((BuscadorReferencia) componente).limpiar();
            } else if (componente instanceof JComboBox<?>) {
                ((JComboBox<?>) componente).setSelectedIndex(0);
            } else {
                ((JTextField) componente).setText("");
            }
        }

        private static JSpinner crearSelectorFecha() {
            JSpinner selector = new JSpinner(new SpinnerDateModel(
                    new Date(), null, null, Calendar.DAY_OF_MONTH));
            selector.setEditor(new JSpinner.DateEditor(selector, "yyyy-MM-dd"));
            return selector;
        }

        private static JSpinner crearSelectorHora() {
            JSpinner selector = new JSpinner(new SpinnerDateModel(
                    new Date(), null, null, Calendar.MINUTE));
            selector.setEditor(new JSpinner.DateEditor(selector, "HH:mm"));
            return selector;
        }
    }

    private static final class BuscadorReferencia extends JTextField {

        private static final int MAX_SUGERENCIAS = 5;

        private final JPopupMenu menuSugerencias = new JPopupMenu();
        private final DefaultListModel<String> modeloSugerencias = new DefaultListModel<>();
        private final JList<String> listaSugerencias = new JList<>(modeloSugerencias);
        private List<Referencia> opciones = new ArrayList<>();

        private BuscadorReferencia() {
            setColumns(20);
            menuSugerencias.setFocusable(false);
            menuSugerencias.setRequestFocusEnabled(false);
            listaSugerencias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listaSugerencias.setVisibleRowCount(MAX_SUGERENCIAS);
            listaSugerencias.setFocusable(false);
            listaSugerencias.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
            listaSugerencias.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evento) {
                    if (evento.getClickCount() >= 1) {
                        seleccionarSugerencia();
                    }
                }
            });
            JScrollPane contenedor = new JScrollPane(listaSugerencias);
            contenedor.setBorder(BorderFactory.createEmptyBorder());
            contenedor.setFocusable(false);
            menuSugerencias.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
            menuSugerencias.add(contenedor);

            getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent evento) {
                    actualizarSugerencias();
                }

                @Override
                public void removeUpdate(DocumentEvent evento) {
                    actualizarSugerencias();
                }

                @Override
                public void changedUpdate(DocumentEvent evento) {
                    actualizarSugerencias();
                }
            });

            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent evento) {
                    if (evento.getKeyCode() == KeyEvent.VK_DOWN && !menuSugerencias.isVisible()) {
                        actualizarSugerencias();
                    }
                    if (evento.getKeyCode() == KeyEvent.VK_DOWN && menuSugerencias.isVisible()) {
                        int indice = listaSugerencias.getSelectedIndex();
                        if (indice < listaSugerencias.getModel().getSize() - 1) {
                            listaSugerencias.setSelectedIndex(indice + 1);
                        }
                    } else if (evento.getKeyCode() == KeyEvent.VK_UP && menuSugerencias.isVisible()) {
                        int indice = listaSugerencias.getSelectedIndex();
                        if (indice > 0) {
                            listaSugerencias.setSelectedIndex(indice - 1);
                        }
                    } else if (evento.getKeyCode() == KeyEvent.VK_ENTER && menuSugerencias.isVisible()) {
                        seleccionarSugerencia();
                    }
                }
            });
        }

        private String getTexto() {
            return getText();
        }

        private void establecerOpciones(List<Referencia> nuevasOpciones) {
            List<Referencia> copia = new ArrayList<>();
            if (nuevasOpciones != null) {
                copia.addAll(nuevasOpciones);
            }
            opciones = copia;
            actualizarSugerencias();
        }

        private void limpiar() {
            setText("");
            menuSugerencias.setVisible(false);
        }

        private void actualizarSugerencias() {
            String texto = getText() == null ? "" : getText().trim();
            if (texto.isEmpty()) {
                menuSugerencias.setVisible(false);
                return;
            }

            List<Referencia> coincidencias = new ArrayList<>();
            String textoNormalizado = normalizar(texto);
            for (Referencia opcion : opciones) {
                String valor = opcion.getValor();
                int prioridad = puntuarCoincidencia(valor, textoNormalizado);
                if (prioridad > 0) {
                    coincidencias.add(opcion);
                }
            }

            if (coincidencias.isEmpty()) {
                menuSugerencias.setVisible(false);
                return;
            }

            Collections.sort(coincidencias, Comparator.comparingInt((Referencia referencia)
                    -> puntuarCoincidencia(referencia.getValor(), textoNormalizado)).reversed());

            modeloSugerencias.clear();
            for (int i = 0; i < Math.min(MAX_SUGERENCIAS, coincidencias.size()); i++) {
                modeloSugerencias.addElement(coincidencias.get(i).toString());
            }

            listaSugerencias.setSelectedIndex(0);
            menuSugerencias.pack();
            menuSugerencias.show(this, 0, getHeight());
            requestFocusInWindow();
        }

        private void seleccionarSugerencia() {
            if (listaSugerencias.getSelectedIndex() >= 0) {
                String valorSeleccionado = listaSugerencias.getSelectedValue();
                for (Referencia opcion : opciones) {
                    if (opcion.toString().equals(valorSeleccionado)) {
                        setText(opcion.getValor());
                        menuSugerencias.setVisible(false);
                        return;
                    }
                }
            }
            menuSugerencias.setVisible(false);
        }

        private static int puntuarCoincidencia(String valor, String textoNormalizado) {
            if (valor == null || valor.isBlank()) {
                return 0;
            }
            String valorNormalizado = normalizar(valor);
            if (valorNormalizado.equals(textoNormalizado)) {
                return 1000;
            }
            if (valorNormalizado.startsWith(textoNormalizado)) {
                return 900;
            }
            if (valorNormalizado.contains(textoNormalizado)) {
                return 800;
            }
            String[] palabras = valorNormalizado.split("\\s+");
            for (String palabra : palabras) {
                if (palabra.startsWith(textoNormalizado)) {
                    return 700;
                }
            }
            return 0;
        }

        private static String normalizar(String valor) {
            if (valor == null) {
                return "";
            }
            return Normalizer.normalize(valor, Normalizer.Form.NFD)
                    .replaceAll("\\p{M}", "")
                    .toLowerCase();
        }
    }

    private static final class Referencia {

        private final String valor;
        private final String etiqueta;

        private Referencia(String valor, String etiqueta) {
            this.valor = valor;
            this.etiqueta = etiqueta;
        }

        private String getValor() {
            return valor;
        }

        @Override
        public String toString() {
            return etiqueta;
        }
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
