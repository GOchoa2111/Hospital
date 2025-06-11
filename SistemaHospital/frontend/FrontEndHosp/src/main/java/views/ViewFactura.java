package views;

import controllers.ControllerFacturaDetalle;
import models.ModelPaciente;
import models.ModelServicios;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.ModelDetalleFactura;
import models.ModelFactura;
import services.ServiceFacturaDetalle;

public class ViewFactura extends JInternalFrame {

    public JTextField txtBuscarPaciente;
    public JTextField txtBuscarServicio;
    public JComboBox<ModelPaciente> comboPaciente;
    public JComboBox<String> comboUsuario;
    public JComboBox<ModelServicios> comboServicio;
    public JTextField txtCantidad;
    public JTextField txtSubtotal;
    public JButton btnAgregar;
    public JTable tablaServicios;
    public JButton btnGenerar;
    public JButton btnLimpiar;
    public JLabel lblTotalSinIVA;
    public JLabel lblIVA;
    public JLabel lblTotalConIVA;
    public JButton btnEliminar;

    private DefaultComboBoxModel<ModelPaciente> modeloPacientes = new DefaultComboBoxModel<>();
    private DefaultComboBoxModel<ModelServicios> modeloServicios = new DefaultComboBoxModel<>();
    private final List<ModelPaciente> listaPacientes = new ArrayList<>();
    private final List<ModelServicios> listaServicios = new ArrayList<>();

    public ViewFactura() {

        setTitle("Registro de Factura");
        setSize(800, 600);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setLayout(new BorderLayout());

        // === PANEL SUPERIOR ===
        JPanel panelTop = new JPanel();
        panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.X_AXIS));

        JPanel panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información"));
        GridBagConstraints gbcInfo = new GridBagConstraints();
        gbcInfo.insets = new Insets(5, 5, 5, 5);
        gbcInfo.fill = GridBagConstraints.HORIZONTAL;

        comboUsuario = new JComboBox<>();
        comboUsuario.setEnabled(false);
        comboUsuario.addItem("admin");

        txtBuscarPaciente = new JTextField(15);
        comboPaciente = new JComboBox<>(modeloPacientes);

        gbcInfo.gridx = 0;
        gbcInfo.gridy = 0;
        panelInfo.add(new JLabel("Usuario:"), gbcInfo);
        gbcInfo.gridx = 1;
        panelInfo.add(comboUsuario, gbcInfo);

        gbcInfo.gridx = 0;
        gbcInfo.gridy = 1;
        panelInfo.add(new JLabel("Buscar Paciente:"), gbcInfo);
        gbcInfo.gridx = 1;
        panelInfo.add(txtBuscarPaciente, gbcInfo);

        gbcInfo.gridx = 0;
        gbcInfo.gridy = 2;
        panelInfo.add(new JLabel("Paciente:"), gbcInfo);
        gbcInfo.gridx = 1;
        panelInfo.add(comboPaciente, gbcInfo);

        txtBuscarPaciente.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filtrarPacientes();
            }

            public void removeUpdate(DocumentEvent e) {
                filtrarPacientes();
            }

            public void changedUpdate(DocumentEvent e) {
                filtrarPacientes();
            }
        });

        // === PANEL DE SERVICIOS ===
        JPanel panelServicio = new JPanel(new GridBagLayout());
        panelServicio.setBorder(BorderFactory.createTitledBorder("Agregar Servicio"));
        GridBagConstraints gbcServicio = new GridBagConstraints();
        gbcServicio.insets = new Insets(5, 5, 5, 5);
        gbcServicio.fill = GridBagConstraints.HORIZONTAL;

        txtBuscarServicio = new JTextField(15);
        comboServicio = new JComboBox<>(modeloServicios);

        txtCantidad = new JTextField(5);
        txtSubtotal = new JTextField(10);
        btnAgregar = new JButton("Agregar");

        //Boton para agregar servicio, 
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarServicioATabla(); // Llama al método que maneja la lógica del botón
            }
        });

        gbcServicio.gridx = 0;
        gbcServicio.gridy = 0;
        panelServicio.add(new JLabel("Buscar Servicio:"), gbcServicio);
        gbcServicio.gridx = 1;
        panelServicio.add(txtBuscarServicio, gbcServicio);

        gbcServicio.gridx = 0;
        gbcServicio.gridy = 1;
        panelServicio.add(new JLabel("Servicio:"), gbcServicio);
        gbcServicio.gridx = 1;
        panelServicio.add(comboServicio, gbcServicio);

        gbcServicio.gridx = 0;
        gbcServicio.gridy = 2;
        panelServicio.add(new JLabel("Cantidad:"), gbcServicio);
        gbcServicio.gridx = 1;
        panelServicio.add(txtCantidad, gbcServicio);

        gbcServicio.gridx = 0;
        gbcServicio.gridy = 3;
        panelServicio.add(new JLabel("Subtotal Q.:"), gbcServicio);
        gbcServicio.gridx = 1;
        panelServicio.add(txtSubtotal, gbcServicio);

        gbcServicio.gridx = 1;
        gbcServicio.gridy = 4;
        gbcServicio.anchor = GridBagConstraints.WEST;
        panelServicio.add(btnAgregar, gbcServicio);

        panelTop.add(panelInfo);
        panelTop.add(Box.createHorizontalGlue());
        panelTop.add(panelServicio);
        add(panelTop, BorderLayout.NORTH);

        // === TABLA SERVICIOS ===
        tablaServicios = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Servicio", "Cantidad", "Subtotal"}
        ));
        add(new JScrollPane(tablaServicios), BorderLayout.CENTER);

        // === PANEL INFERIOR ===
        JPanel panelBottom = new JPanel(new BorderLayout());

        JPanel panelTotales = new JPanel(new GridLayout(3, 2));
        panelTotales.setBorder(BorderFactory.createTitledBorder("Resumen de Pago"));

        lblTotalSinIVA = new JLabel("0.00", SwingConstants.RIGHT);
        lblIVA = new JLabel("0.00", SwingConstants.RIGHT);
        lblTotalConIVA = new JLabel("0.00", SwingConstants.RIGHT);

        panelTotales.add(new JLabel("Total sin IVA:"));
        panelTotales.add(lblTotalSinIVA);
        panelTotales.add(new JLabel("IVA (12%):"));
        panelTotales.add(lblIVA);
        panelTotales.add(new JLabel("Total con IVA:"));
        panelTotales.add(lblTotalConIVA);

        JPanel panelBotones = new JPanel();
        btnGenerar = new JButton("Generar Factura");
        //Configuración de boton y carga del metodo para guardar factura
        btnGenerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarFactura();
            }
        });

        btnLimpiar = new JButton("Limpiar");
        //Configuracion de boton y carga de metodo para limpiar
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
        btnEliminar = new JButton("Eliminar");
        //Configuración de boton y carga del metodo para eliminar un registro
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarFilaSeleccionada();
            }

        });

        panelBotones.add(btnGenerar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnEliminar);

        panelBottom.add(panelTotales, BorderLayout.CENTER);
        panelBottom.add(panelBotones, BorderLayout.SOUTH);

        add(panelBottom, BorderLayout.SOUTH);

        // === Cargar pacientes y servicios desde API ===
        cargarDatosIniciales();

        txtBuscarServicio.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filtrarServicios();
            }

            public void removeUpdate(DocumentEvent e) {
                filtrarServicios();
            }

            public void changedUpdate(DocumentEvent e) {
                filtrarServicios();
            }
        });
    }

    //cargar datos
    private void cargarDatosIniciales() {
        try {
            ControllerFacturaDetalle controller = new ControllerFacturaDetalle();

            listaPacientes.clear();
            listaPacientes.addAll(controller.obtenerPacientes());
            modeloPacientes.removeAllElements();
            for (ModelPaciente p : listaPacientes) {
                modeloPacientes.addElement(p);
            }

            listaServicios.clear();
            listaServicios.addAll(controller.obtenerServicios());
            modeloServicios.removeAllElements();
            for (ModelServicios s : listaServicios) {
                modeloServicios.addElement(s);
            }

            // === Listener para mostrar subtotal automáticamente al seleccionar servicio ===
            comboServicio.addActionListener(e -> {
                ModelServicios seleccionado = (ModelServicios) comboServicio.getSelectedItem();
                if (seleccionado != null) {
                    txtSubtotal.setText(String.valueOf(seleccionado.getPrecio()));
                }
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }

    //filtrar Pacientes
    private void filtrarPacientes() {
        String filtro = txtBuscarPaciente.getText().toLowerCase();
        modeloPacientes.removeAllElements();
        for (ModelPaciente p : listaPacientes) {
            if ((p.getNombre() + " " + p.getApellido()).toLowerCase().contains(filtro)) {
                modeloPacientes.addElement(p);
            }
        }
    }

    //filtrar servicios, 
    private void filtrarServicios() {
        String filtro = txtBuscarServicio.getText().toLowerCase();
        modeloServicios.removeAllElements();
        for (ModelServicios s : listaServicios) {
            if (s.getNombre().toLowerCase().contains(filtro)) {
                modeloServicios.addElement(s);
            }
        }
    }

    /**
     * Método que agrega el servicio seleccionado a la tabla con su cantidad y
     * subtotal. También actualiza los totales (sin IVA, IVA, con IVA).
     */
    private void agregarServicioATabla() {
        // Obtener el servicio seleccionado
        ModelServicios servicioSeleccionado = (ModelServicios) comboServicio.getSelectedItem();

        // Validar que se haya seleccionado un servicio
        if (servicioSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un servicio.");
            return;
        }

        // Validar que se haya ingresado una cantidad válida
        String cantidadTexto = txtCantidad.getText().trim();
        if (cantidadTexto.isEmpty() || !cantidadTexto.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Debe ingresar una cantidad válida.");
            return;
        }

        int cantidad = Integer.parseInt(cantidadTexto);
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.");
            return;
        }

        // Calcular subtotal (precio * cantidad)
        double precio = servicioSeleccionado.getPrecio();
        double subtotal = precio * cantidad;

        // Mostrar el subtotal en el campo correspondiente
        txtSubtotal.setText(String.format("%.2f", subtotal));

        // Agregar el servicio a la tabla
        DefaultTableModel model = (DefaultTableModel) tablaServicios.getModel();
        model.addRow(new Object[]{
            servicioSeleccionado.getNombre(),
            cantidad,
            String.format("%.2f", subtotal)
        });

        // Actualizar totales (sin IVA, IVA, con IVA)
        this.txtCantidad.setText(""); //Limpiar el campo total
        actualizarTotales();
    }

    /**
     * Método auxiliar que recalcula los totales de la factura.
     */
    private void actualizarTotales() {
        DefaultTableModel model = (DefaultTableModel) tablaServicios.getModel();
        double totalSinIVA = 0.0;

        // Recorrer la tabla y sumar los subtotales
        for (int i = 0; i < model.getRowCount(); i++) {
            Object valor = model.getValueAt(i, 2); // Columna del subtotal
            if (valor != null) {
                try {
                    totalSinIVA += Double.parseDouble(valor.toString());
                } catch (NumberFormatException e) {
                    // Ignorar errores de conversión
                }
            }
        }

        double iva = totalSinIVA * 0.12;
        double totalConIVA = totalSinIVA + iva;

        // Actualizar las etiquetas de resumen
        lblTotalSinIVA.setText(String.format("%.2f", totalSinIVA));
        lblIVA.setText(String.format("%.2f", iva));
        lblTotalConIVA.setText(String.format("%.2f", totalConIVA));
    }

    //Eliminar un registro seleccionado de la tabla
    private void eliminarFilaSeleccionada() {
        int filaSeleccionada = tablaServicios.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un servicio de la tabla para eliminar.");
            return;
        }

        // Confirmar la eliminación
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar el servicio seleccionado?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            DefaultTableModel model = (DefaultTableModel) tablaServicios.getModel();
            model.removeRow(filaSeleccionada);
            actualizarTotales(); // Recalcula los totales después de eliminar
        }
    }

    //Registra una factura
    private void guardarFactura() {
        if (tablaServicios.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Debe agregar al menos un servicio para guardar la factura.");
            return;
        }

        ModelPaciente pacienteSeleccionado = (ModelPaciente) comboPaciente.getSelectedItem();
        if (pacienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un paciente.");
            return;
        }

        // Crear lista de detalles desde la tabla
        DefaultTableModel model = (DefaultTableModel) tablaServicios.getModel();
        List<ModelDetalleFactura> detalles = new ArrayList<>();

        for (int i = 0; i < model.getRowCount(); i++) {
            String nombreServicio = model.getValueAt(i, 0).toString();
            int cantidad = Integer.parseInt(model.getValueAt(i, 1).toString());
            double subtotal = Double.parseDouble(model.getValueAt(i, 2).toString());

            // Buscar el servicio por nombre
            int idServicio = -1;
            for (ModelServicios s : listaServicios) {
                if (s.getNombre().equals(nombreServicio)) {
                    idServicio = s.getIdServicio();
                    break;
                }
            }

            if (idServicio == -1) {
                JOptionPane.showMessageDialog(this, "No se pudo encontrar un servicio con nombre: " + nombreServicio);
                return;
            }

            ModelDetalleFactura detalle = new ModelDetalleFactura();
            detalle.setIdServicio(idServicio);
            detalle.setCantidad(cantidad);
            detalle.setSubtotal(subtotal);

            detalles.add(detalle);
        }

        // Crear factura
        ModelFactura factura = new ModelFactura();
        factura.setFecha(LocalDate.now()); //revisar posible problema de fecha
        factura.setIdPaciente(pacienteSeleccionado.getIdPaciente());
        factura.setIdUsuario(1); // Puedes cambiar esto si tienes usuarios reales
        factura.setTotal(Double.parseDouble(lblTotalConIVA.getText()));
        factura.setDetalles(detalles);

        // Llamar al servicio
        ServiceFacturaDetalle servicio = new ServiceFacturaDetalle();
        boolean exito = servicio.registrarFactura(factura);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Factura registrada correctamente.");
            limpiarFormulario(); // Reutiliza tu método existente
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar la factura.");
        }
    }

    //limpiar todo los registros
    private void limpiarFormulario() {
        // Limpiar campos de texto
        txtBuscarPaciente.setText("");
        txtBuscarServicio.setText("");
        txtCantidad.setText("");
        txtSubtotal.setText("");

        // Resetear combos a la primera opción si hay elementos
        if (comboPaciente.getItemCount() > 0) {
            comboPaciente.setSelectedIndex(0);
        }

        if (comboServicio.getItemCount() > 0) {
            comboServicio.setSelectedIndex(0);
        }

        // Limpiar tabla
        DefaultTableModel model = (DefaultTableModel) tablaServicios.getModel();
        model.setRowCount(0);

        // Reiniciar totales
        lblTotalSinIVA.setText("0.00");
        lblIVA.setText("0.00");
        lblTotalConIVA.setText("0.00");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Factura Form");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 700);

            JDesktopPane desktop = new JDesktopPane();
            ViewFactura facturaForm = new ViewFactura();
            desktop.add(facturaForm);
            facturaForm.setVisible(true);

            frame.add(desktop);
            frame.setVisible(true);
        });
    }
}
