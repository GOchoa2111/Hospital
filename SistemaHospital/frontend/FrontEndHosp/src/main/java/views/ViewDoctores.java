// ViewDoctores.java
package views;

import controllers.ControllerDoctores;
import models.ModelDoctores;
import models.ModelLogin;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ViewDoctores extends JInternalFrame {

    private JTextField txtNombre, txtApellido, txtEspecialidad, txtTelefono, txtCorreo;
    private JComboBox<String> comboEstado; // Usamos JComboBox para estado
    private JTable tablaDoctores;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;

    private ControllerDoctores controlador;
    private DefaultTableModel modeloTabla;
    private int idDoctorSeleccionado = -1;

    private ModelLogin usuarioLogueado;
    private String token;

    private boolean cargandoDatos = false; // Flag para evitar eventos no deseados

    public ViewDoctores(ModelLogin usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
        this.token = usuarioLogueado.getToken();

        setTitle("Gestión de Doctores");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setSize(800, 600);

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

        panelFormulario.add(new JLabel("Estado:"));
        comboEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});
        panelFormulario.add(comboEstado);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        Dimension btnSize = new Dimension(110, 25);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setPreferredSize(btnSize);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setPreferredSize(btnSize);
        btnActualizar.setEnabled(false);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setPreferredSize(btnSize);
        btnEliminar.setEnabled(false);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setPreferredSize(btnSize);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable
            }
        };
        tablaDoctores = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaDoctores);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Listado de Doctores"));

        setLayout(new BorderLayout(10, 10));
        add(panelFormulario, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void configurarTabla() {
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("Especialidad");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Correo");
        modeloTabla.addColumn("Estado");

        tablaDoctores.getColumnModel().getColumn(0).setPreferredWidth(30);
        tablaDoctores.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablaDoctores.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaDoctores.getColumnModel().getColumn(3).setPreferredWidth(120);
        tablaDoctores.getColumnModel().getColumn(4).setPreferredWidth(80);
        tablaDoctores.getColumnModel().getColumn(5).setPreferredWidth(150);
        tablaDoctores.getColumnModel().getColumn(6).setPreferredWidth(50);

        tablaDoctores.setDefaultRenderer(Object.class, new DoctorTableRenderer());
    }

    private void configurarEventos() {
        btnGuardar.addActionListener(e -> guardarDoctor());
        btnActualizar.addActionListener(e -> actualizarDoctor());
        btnEliminar.addActionListener(e -> eliminarDoctor());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

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
            modeloTabla.setRowCount(0);
            List<ModelDoctores> doctores = controlador.obtenerDoctores(token);

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
            if (camposVacios()) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String especialidad = txtEspecialidad.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String correo = txtCorreo.getText().trim();

            boolean resultado = controlador.agregarDoctor(
                    nombre, apellido, especialidad, telefono, correo,
                    usuarioLogueado.getIdUsuario(),
                    token);

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
            if (idDoctorSeleccionado == -1) {
                JOptionPane.showMessageDialog(this,
                        "Debe seleccionar un doctor para actualizar",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (camposVacios()) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String especialidad = txtEspecialidad.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String correo = txtCorreo.getText().trim();
            boolean estado = comboEstado.getSelectedItem().equals("Activo");

            boolean resultado = controlador.actualizarDoctor(
                    idDoctorSeleccionado, nombre, apellido, especialidad,
                    telefono, correo,
                    estado,
                    usuarioLogueado.getIdUsuario(),
                    token);

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
            if (idDoctorSeleccionado == -1) {
                JOptionPane.showMessageDialog(this,
                        "Debe seleccionar un doctor para eliminar",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar este doctor?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean resultado = controlador.eliminarDoctor(idDoctorSeleccionado, token);

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
        cargandoDatos = true;

        idDoctorSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
        ModelDoctores doctor = controlador.buscarDoctorPorId(idDoctorSeleccionado, token);

        if (doctor != null) {
            txtNombre.setText(doctor.getNombre());
            txtApellido.setText(doctor.getApellido());
            txtEspecialidad.setText(doctor.getEspecialidad());
            txtTelefono.setText(doctor.getTelefono());
            txtCorreo.setText(doctor.getCorreo());
            comboEstado.setSelectedItem(doctor.isEstado() ? "Activo" : "Inactivo");

            btnActualizar.setEnabled(true);
            btnEliminar.setEnabled(true);
            btnGuardar.setEnabled(false);
        }

        cargandoDatos = false;
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEspecialidad.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        comboEstado.setSelectedItem("Activo");

        idDoctorSeleccionado = -1;

        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);

        tablaDoctores.clearSelection();
    }

    private boolean camposVacios() {
        return txtNombre.getText().trim().isEmpty()
                || txtApellido.getText().trim().isEmpty()
                || txtEspecialidad.getText().trim().isEmpty()
                || txtTelefono.getText().trim().isEmpty()
                || txtCorreo.getText().trim().isEmpty();
    }

    private class DoctorTableRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String estado = (String) table.getModel().getValueAt(row, 6);
            if ("Inactivo".equals(estado)) {
                c.setBackground(new Color(255, 200, 200));
            } else {
                c.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            }
            return c;
        }
    }
}