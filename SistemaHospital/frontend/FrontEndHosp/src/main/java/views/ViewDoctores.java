package views;

import controllers.ControllerDoctores;
import models.ModelDoctores;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ViewDoctores extends JInternalFrame {

    private JTextField txtNombre, txtApellido, txtEspecialidad, txtTelefono, txtCorreo;
    private JCheckBox chkEstado;
    private JTable tablaDoctores;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    
    private ControllerDoctores controlador;
    private DefaultTableModel modeloTabla;
    private int idDoctorSeleccionado = -1;
    private int idUsuarioActual = 1; // Esto debería venir de la sesión del usuario

    public ViewDoctores() {
        setTitle("Gestión de Doctores");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setSize(800, 600);
        
        // Inicializar el controlador
        controlador = new ControllerDoctores();
        
        initComponents();
        configurarTabla();
        configurarEventos();
        cargarDoctores();
    }

    private void initComponents() {
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Doctor"));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panelFormulario.add(txtApellido);

        panelFormulario.add(new JLabel("Especialidad:"));
        txtEspecialidad = new JTextField();
        panelFormulario.add(txtEspecialidad);

        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);

        panelFormulario.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panelFormulario.add(txtCorreo);

        panelFormulario.add(new JLabel("Activo:"));
        chkEstado = new JCheckBox("Sí");
        chkEstado.setSelected(true);
        panelFormulario.add(chkEstado);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        Dimension btnSize = new Dimension(110, 25);
        
        btnGuardar = new JButton("Guardar");
        btnGuardar.setPreferredSize(btnSize);
        
        btnActualizar = new JButton("Actualizar");
        btnActualizar.setPreferredSize(btnSize);
        btnActualizar.setEnabled(false); // Inicialmente deshabilitado
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setPreferredSize(btnSize);
        btnEliminar.setEnabled(false); // Inicialmente deshabilitado
        
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setPreferredSize(btnSize);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Tabla
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        tablaDoctores = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaDoctores);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Listado de Doctores"));

        // Layout principal
        setLayout(new BorderLayout(10, 10));
        add(panelFormulario, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void configurarTabla() {
        // Configurar las columnas de la tabla
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("Especialidad");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Correo");
        modeloTabla.addColumn("Estado");
        
        // Configurar ancho de columnas
        tablaDoctores.getColumnModel().getColumn(0).setPreferredWidth(30);
        tablaDoctores.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablaDoctores.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaDoctores.getColumnModel().getColumn(3).setPreferredWidth(120);
        tablaDoctores.getColumnModel().getColumn(4).setPreferredWidth(80);
        tablaDoctores.getColumnModel().getColumn(5).setPreferredWidth(150);
        tablaDoctores.getColumnModel().getColumn(6).setPreferredWidth(50);
    }
    
    private void configurarEventos() {
        // Evento para el botón Guardar
        btnGuardar.addActionListener((ActionEvent e) -> {
            guardarDoctor();
        });
        
        // Evento para el botón Actualizar
        btnActualizar.addActionListener((ActionEvent e) -> {
            actualizarDoctor();
        });
        
        // Evento para el botón Eliminar
        btnEliminar.addActionListener((ActionEvent e) -> {
            eliminarDoctor();
        });
        
        // Evento para el botón Limpiar
        btnLimpiar.addActionListener((ActionEvent e) -> {
            limpiarFormulario();
        });
        
        // Evento para seleccionar un doctor de la tabla
        tablaDoctores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaDoctores.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    seleccionarDoctor(filaSeleccionada);
                }
            }
        });
    }
    
    private void cargarDoctores() {
        try {
            // Limpiar la tabla
            modeloTabla.setRowCount(0);
            
            // Obtener la lista de doctores desde el controlador
            List<ModelDoctores> doctores = controlador.obtenerDoctores();
            
            // Agregar cada doctor a la tabla
            for (ModelDoctores doctor : doctores) {
                Object[] fila = {
                    doctor.getIdDoctor(),
                    doctor.getNombre(),
                    doctor.getApellido(),
                    doctor.getEspecialidad(),
                    doctor.getTelefono(),
                    doctor.getCorreo(),
                    doctor.isEstado() ? "Activo" : "Inactivo"
                };
                modeloTabla.addRow(fila);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                    "Error al cargar los doctores: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void guardarDoctor() {
        try {
            // Validar campos
            if (camposVacios()) {
                JOptionPane.showMessageDialog(this, 
                        "Todos los campos son obligatorios", 
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Obtener datos del formulario
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String especialidad = txtEspecialidad.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String correo = txtCorreo.getText().trim();
            
            // Llamar al controlador para guardar el doctor
            boolean resultado = controlador.agregarDoctor(
                    nombre, apellido, especialidad, telefono, correo, 
                    idUsuarioActual, idUsuarioActual);
            
            if (resultado) {
                JOptionPane.showMessageDialog(this, 
                        "Doctor guardado correctamente", 
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarDoctores();
            } else {
                JOptionPane.showMessageDialog(this, 
                        "No se pudo guardar el doctor", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                    "Error al guardar el doctor: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void actualizarDoctor() {
        try {
            // Validar que haya un doctor seleccionado
            if (idDoctorSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, 
                        "Debe seleccionar un doctor para actualizar", 
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Validar campos
            if (camposVacios()) {
                JOptionPane.showMessageDialog(this, 
                        "Todos los campos son obligatorios", 
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Obtener datos del formulario
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String especialidad = txtEspecialidad.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String correo = txtCorreo.getText().trim();
            
            // Llamar al controlador para actualizar el doctor
            boolean resultado = controlador.actualizarDoctor(
                    idDoctorSeleccionado, nombre, apellido, especialidad, 
                    telefono, correo, idUsuarioActual);
            
            if (resultado) {
                JOptionPane.showMessageDialog(this, 
                        "Doctor actualizado correctamente", 
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarDoctores();
            } else {
                JOptionPane.showMessageDialog(this, 
                        "No se pudo actualizar el doctor", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                    "Error al actualizar el doctor: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void eliminarDoctor() {
        try {
            // Validar que haya un doctor seleccionado
            if (idDoctorSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, 
                        "Debe seleccionar un doctor para eliminar", 
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Confirmar eliminación
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                    "¿Está seguro de eliminar este doctor?", 
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                // Llamar al controlador para eliminar el doctor
                boolean resultado = controlador.eliminarDoctor(idDoctorSeleccionado);
                
                if (resultado) {
                    JOptionPane.showMessageDialog(this, 
                            "Doctor eliminado correctamente", 
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    cargarDoctores();
                } else {
                    JOptionPane.showMessageDialog(this, 
                            "No se pudo eliminar el doctor", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                    "Error al eliminar el doctor: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void seleccionarDoctor(int fila) {
        // Obtener el ID del doctor seleccionado
        idDoctorSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
        
        // Buscar el doctor por su ID
        ModelDoctores doctor = controlador.buscarDoctorPorId(idDoctorSeleccionado);
        
        if (doctor != null) {
            // Llenar el formulario con los datos del doctor
            txtNombre.setText(doctor.getNombre());
            txtApellido.setText(doctor.getApellido());
            txtEspecialidad.setText(doctor.getEspecialidad());
            txtTelefono.setText(doctor.getTelefono());
            txtCorreo.setText(doctor.getCorreo());
            chkEstado.setSelected(doctor.isEstado());
            
            // Habilitar botones de actualizar y eliminar
            btnActualizar.setEnabled(true);
            btnEliminar.setEnabled(true);
            // Deshabilitar botón de guardar
            btnGuardar.setEnabled(false);
        }
    }
    
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEspecialidad.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        chkEstado.setSelected(true);
        
        idDoctorSeleccionado = -1;
        
        // Restablecer estado de los botones
        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        
        // Quitar selección de la tabla
        tablaDoctores.clearSelection();
    }
    
    private boolean camposVacios() {
        
        return txtNombre.getText().trim().isEmpty() ||
               txtApellido.getText().trim().isEmpty() ||
               txtEspecialidad.getText().trim().isEmpty() ||
               txtTelefono.getText().trim().isEmpty() ||
               txtCorreo.getText().trim().isEmpty();
    }

    // Método main para prueba independiente
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Prueba Doctores");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 700);
            JDesktopPane desktop = new JDesktopPane();
            frame.add(desktop);

            ViewDoctores form = new ViewDoctores();
            desktop.add(form);
            form.setVisible(true);

            frame.setVisible(true);
        });
    }
}