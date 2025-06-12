package views;

import controllers.ControllerFacturaDetalle;
import models.ModelPaciente;
import models.ModelServicios;
import models.ModelDetalleFactura;
import models.ModelFactura;
import models.ModelLogin;
import services.ServiceFacturaDetalle;

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

public class ViewFactura extends JInternalFrame {

    // Componentes de la interfaz
    public JTextField txtBuscarPaciente;
    public JTextField txtBuscarServicio;
    public JComboBox<ModelPaciente> comboPaciente;
    public JTextField txtUsuario; // Mostrar usuario logueado
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

    // Modelos para combos
    private DefaultComboBoxModel<ModelPaciente> modeloPacientes = new DefaultComboBoxModel<>();
    private DefaultComboBoxModel<ModelServicios> modeloServicios = new DefaultComboBoxModel<>();

    // Listas para almacenar datos cargados
    private final List<ModelPaciente> listaPacientes = new ArrayList<>();
    private final List<ModelServicios> listaServicios = new ArrayList<>();

    // Usuario logueado (se recibe en el constructor)
    private final ModelLogin usuario;

    /**
     * Constructor principal que recibe el usuario logueado. Inicializa la
     * interfaz y carga los datos.
     */
    public ViewFactura(ModelLogin usuario) {
        this.usuario = usuario;
        initUI();
    }

    /**
     * Método que inicializa toda la interfaz gráfica.
     */
    private void initUI() {
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

        // Panel con información del usuario y paciente
        JPanel panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información"));
        GridBagConstraints gbcInfo = new GridBagConstraints();
        gbcInfo.insets = new Insets(5, 5, 5, 5);
        gbcInfo.fill = GridBagConstraints.HORIZONTAL;

        // Campo para mostrar el usuario logueado (no editable)
        txtUsuario = new JTextField(usuario.getNombreUsuario());
        txtUsuario.setEditable(false);

        // Campo para buscar paciente y combo para seleccionar paciente
        txtBuscarPaciente = new JTextField(15);
        comboPaciente = new JComboBox<>(modeloPacientes);

        // Agregar etiquetas y componentes al panelInfo con GridBagLayout
        gbcInfo.gridx = 0;
        gbcInfo.gridy = 0;
        panelInfo.add(new JLabel("Usuario:"), gbcInfo);
        gbcInfo.gridx = 1;
        panelInfo.add(txtUsuario, gbcInfo);

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

        // Listener para filtrar pacientes mientras se escribe
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

        // Campo para buscar servicio y combo para seleccionar servicio
        txtBuscarServicio = new JTextField(15);
        comboServicio = new JComboBox<>(modeloServicios);

        // Campos para cantidad y subtotal
        txtCantidad = new JTextField(5);
        txtSubtotal = new JTextField(10);
        txtSubtotal.setEditable(false); // Subtotal se calcula automáticamente

        // Botón para agregar servicio a la tabla
        btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarServicioATabla();
            }
        });

        // Agregar componentes al panelServicio
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

        // Añadir paneles al panel superior
        panelTop.add(panelInfo);
        panelTop.add(Box.createHorizontalGlue());
        panelTop.add(panelServicio);
        add(panelTop, BorderLayout.NORTH);

        // === TABLA DE SERVICIOS AGREGADOS ===
        tablaServicios = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Servicio", "Cantidad", "Subtotal"}
        ));
        add(new JScrollPane(tablaServicios), BorderLayout.CENTER);

        // === PANEL INFERIOR CON TOTALES Y BOTONES ===
        JPanel panelBottom = new JPanel(new BorderLayout());

        // Panel con resumen de totales
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

        // Panel con botones
        JPanel panelBotones = new JPanel();
        btnGenerar = new JButton("Generar Factura");
        btnGenerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarFactura();
            }
        });

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });

        btnEliminar = new JButton("Eliminar");
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

        // Cargar datos iniciales de pacientes y servicios
        cargarDatosIniciales(usuario.getToken());

        // Listener para filtrar servicios mientras se escribe
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

        // Listener para actualizar subtotal automáticamente al seleccionar un servicio
        comboServicio.addActionListener(e -> {
            ModelServicios seleccionado = (ModelServicios) comboServicio.getSelectedItem();
            if (seleccionado != null) {
                txtSubtotal.setText(String.format("%.2f", seleccionado.getPrecio()));
            }
        });
    }

    /**
     * Carga los datos iniciales de pacientes y servicios desde el controlador.
     */
    private void cargarDatosIniciales(String token) {
        try {
            ControllerFacturaDetalle controller = new ControllerFacturaDetalle();

            // Cargar pacientes
            listaPacientes.clear();
            listaPacientes.addAll(controller.obtenerPacientes());
            modeloPacientes.removeAllElements();
            for (ModelPaciente p : listaPacientes) {
                modeloPacientes.addElement(p);
            }

            // Cargar servicios
            listaServicios.clear();
            listaServicios.addAll(controller.obtenerServicios());
            modeloServicios.removeAllElements();
            for (ModelServicios s : listaServicios) {
                modeloServicios.addElement(s);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }

    /**
     * Filtra la lista de pacientes según el texto ingresado en
     * txtBuscarPaciente.
     */
    private void filtrarPacientes() {
        String filtro = txtBuscarPaciente.getText().toLowerCase();
        modeloPacientes.removeAllElements();
        for (ModelPaciente p : listaPacientes) {
            if ((p.getNombre() + " " + p.getApellido()).toLowerCase().contains(filtro)) {
                modeloPacientes.addElement(p);
            }
        }
    }

    /**
     * Filtra la lista de servicios según el texto ingresado en
     * txtBuscarServicio.
     */
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
     * Agrega el servicio seleccionado a la tabla con cantidad y subtotal.
     * También actualiza los totales.
     */
    private void agregarServicioATabla() {
        ModelServicios servicioSeleccionado = (ModelServicios) comboServicio.getSelectedItem();

        if (servicioSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un servicio.");
            return;
        }

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

        double precio = servicioSeleccionado.getPrecio();
        double subtotal = precio * cantidad;

        txtSubtotal.setText(String.format("%.2f", subtotal));

        DefaultTableModel model = (DefaultTableModel) tablaServicios.getModel();
        model.addRow(new Object[]{
            servicioSeleccionado.getNombre(),
            cantidad,
            String.format("%.2f", subtotal)
        });

        txtCantidad.setText(""); // Limpiar campo cantidad
        actualizarTotales();
    }

    /**
     * Actualiza los totales (sin IVA, IVA y con IVA) en las etiquetas
     * correspondientes.
     */
    private void actualizarTotales() {
        DefaultTableModel model = (DefaultTableModel) tablaServicios.getModel();
        double totalSinIVA = 0.0;

        for (int i = 0; i < model.getRowCount(); i++) {
            Object valor = model.getValueAt(i, 2);
            if (valor != null) {
                try {
                    totalSinIVA += Double.parseDouble(valor.toString());
                } catch (NumberFormatException e) {
                    // Ignorar error
                }
            }
        }

        double iva = totalSinIVA * 0.12;
        double totalConIVA = totalSinIVA + iva;

        lblTotalSinIVA.setText(String.format("%.2f", totalSinIVA));
        lblIVA.setText(String.format("%.2f", iva));
        lblTotalConIVA.setText(String.format("%.2f", totalConIVA));
    }

    /**
     * Elimina la fila seleccionada de la tabla de servicios.
     */
    private void eliminarFilaSeleccionada() {
        int filaSeleccionada = tablaServicios.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un servicio de la tabla para eliminar.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar el servicio seleccionado?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            DefaultTableModel model = (DefaultTableModel) tablaServicios.getModel();
            model.removeRow(filaSeleccionada);
            actualizarTotales();
        }
    }

    /**
     * Guarda la factura con los datos ingresados y el usuario logueado.
     */
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

        DefaultTableModel model = (DefaultTableModel) tablaServicios.getModel();
        List<ModelDetalleFactura> detalles = new ArrayList<>();

        for (int i = 0; i < model.getRowCount(); i++) {
            String nombreServicio = model.getValueAt(i, 0).toString();
            int cantidad = Integer.parseInt(model.getValueAt(i, 1).toString());
            double subtotal = Double.parseDouble(model.getValueAt(i, 2).toString());

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

        ModelFactura factura = new ModelFactura();
        factura.setFecha(LocalDate.now());
        factura.setIdPaciente(pacienteSeleccionado.getIdPaciente());

        // Aquí asignamos el usuario logueado correctamente  
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Error: Usuario no está definido.");
            return;
        }

        // Imprimir el ID del usuario para verificar  
        System.out.println("ID del usuario logueado: " + usuario.getIdUsuario());

        factura.setIdUsuario(usuario.getIdUsuario());
        factura.setTotal(Double.parseDouble(lblTotalConIVA.getText()));
        factura.setDetalles(detalles);

        ServiceFacturaDetalle servicio = new ServiceFacturaDetalle();
        String token = usuario.getToken();
        boolean exito = servicio.registrarFactura(factura, token);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Factura registrada correctamente.");
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar la factura.");
        }
    }

    /**
     * Limpia todos los campos y la tabla para un nuevo registro.
     */
    private void limpiarFormulario() {
        txtBuscarPaciente.setText("");
        txtBuscarServicio.setText("");
        txtCantidad.setText("");
        txtSubtotal.setText("");

        if (comboPaciente.getItemCount() > 0) {
            comboPaciente.setSelectedIndex(0);
        }

        if (comboServicio.getItemCount() > 0) {
            comboServicio.setSelectedIndex(0);
        }

        DefaultTableModel model = (DefaultTableModel) tablaServicios.getModel();
        model.setRowCount(0);

        lblTotalSinIVA.setText("0.00");
        lblIVA.setText("0.00");
        lblTotalConIVA.setText("0.00");
    }
}
