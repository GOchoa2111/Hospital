// ViewPacientes.java
package views;

import controllers.ControllerPaciente;
import models.ModelLogin;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewPacientes extends JInternalFrame {

    private JTextField txtId, txtNombre, txtApellido, txtTelefono, txtFechaNacimiento,
            txtDireccion, txtCorreo;
    private JComboBox<String> comboGenero, comboTipoSangre, comboEstado;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private ControllerPaciente controlador;

    private ModelLogin usuarioLogueado;
    private String token;

    public ViewPacientes(ModelLogin usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
        this.token = usuarioLogueado.getToken();

        setTitle("Gestión de Pacientes");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setSize(800, 600);
        setLayout(new BorderLayout(10, 10));

        this.controlador = new ControllerPaciente(this, token, usuarioLogueado.getIdUsuario());

        // Panel de formulario
        JPanel panelForm = new JPanel(new GridLayout(6, 4, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelForm.add(new JLabel("ID Paciente:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panelForm.add(txtId);

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panelForm.add(txtApellido);

        panelForm.add(new JLabel("Fecha de Nacimiento:"));
        txtFechaNacimiento = new JTextField("YYYY-MM-DD");
        panelForm.add(txtFechaNacimiento);

        panelForm.add(new JLabel("Género:"));
        comboGenero = new JComboBox<>(new String[]{"Seleccionar", "Masculino", "Femenino", "Otro"});
        panelForm.add(comboGenero);

        panelForm.add(new JLabel("Tipo de Sangre:"));
        comboTipoSangre = new JComboBox<>(new String[]{"Seleccionar", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"});
        panelForm.add(comboTipoSangre);

        panelForm.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panelForm.add(txtDireccion);

        panelForm.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelForm.add(txtTelefono);

        panelForm.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panelForm.add(txtCorreo);

        panelForm.add(new JLabel("Estado:"));
        comboEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});
        panelForm.add(comboEstado);

        add(panelForm, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        Dimension btnSize = new Dimension(110, 25);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setPreferredSize(btnSize);
        panelBotones.add(btnRegistrar);
        btnRegistrar.addActionListener(e -> controlador.registrarPaciente());

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setPreferredSize(btnSize);
        panelBotones.add(btnActualizar);
        btnActualizar.addActionListener(e -> controlador.actualizarPaciente());

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setPreferredSize(btnSize);
        panelBotones.add(btnEliminar);
        btnEliminar.addActionListener(e -> controlador.eliminarPaciente());

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setPreferredSize(btnSize);
        panelBotones.add(btnLimpiar);
        btnLimpiar.addActionListener(e -> controlador.limpiarFormulario());

        add(panelBotones, BorderLayout.SOUTH);

        // Tabla de pacientes
        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);
        tabla.setPreferredScrollableViewportSize(new Dimension(700, 100));
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // Inicializar botones
        btnRegistrar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnLimpiar.setEnabled(false);
        btnEliminar.setEnabled(false);

        // Selección de fila en tabla
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tabla.getSelectedRow();
                if (fila != -1) {
                    try {
                        txtId.setText(tabla.getValueAt(fila, 0).toString());
                        txtNombre.setText(tabla.getValueAt(fila, 1).toString());
                        txtApellido.setText(tabla.getValueAt(fila, 2).toString());
                        txtFechaNacimiento.setText(tabla.getValueAt(fila, 3).toString());
                        comboGenero.setSelectedItem(tabla.getValueAt(fila, 4).toString().trim());
                        comboTipoSangre.setSelectedItem(tabla.getValueAt(fila, 5).toString().trim());
                        txtDireccion.setText(tabla.getValueAt(fila, 6).toString());
                        txtTelefono.setText(tabla.getValueAt(fila, 7).toString());
                        txtCorreo.setText(tabla.getValueAt(fila, 8).toString());

                        Object estadoObj = tabla.getValueAt(fila, 11);
                        boolean estado = false;
                        if (estadoObj instanceof Boolean) {
                            estado = (Boolean) estadoObj;
                        } else if (estadoObj instanceof String) {
                            String estadoStr = ((String) estadoObj).trim().toLowerCase();
                            estado = estadoStr.equals("true") || estadoStr.equals("1") || estadoStr.equals("activo");
                        } else if (estadoObj instanceof Integer) {
                            estado = ((Integer) estadoObj) == 1;
                        }

                        comboEstado.setSelectedItem(estado ? "Activo" : "Inactivo");

                        btnRegistrar.setEnabled(false);
                        btnEliminar.setEnabled(true);
                        btnActualizar.setEnabled(true);
                        btnLimpiar.setEnabled(true);

                    } catch (Exception ex) {
                        System.err.println("Error al seleccionar fila: " + ex.getMessage());
                    }
                }
            }
        });

        // Renderer para pintar filas inactivas
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                String estado = table.getValueAt(row, 11).toString();

                if (estado.equalsIgnoreCase("Inactivo") || estado.equals("0")) {
                    c.setBackground(Color.PINK);
                } else {
                    c.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
                }

                return c;
            }
        });

        controlador.cargarPacientesEnTabla();
    }

    // Getters para campos y botones (igual que antes)...
    public JTextField getTxtId() {
        return txtId;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtApellido() {
        return txtApellido;
    }

    public JTextField getTxtFechaNacimiento() {
        return txtFechaNacimiento;
    }

    public JComboBox<String> getComboGenero() {
        return comboGenero;
    }

    public JComboBox<String> getComboTipoSangre() {
        return comboTipoSangre;
    }

    public JTextField getTxtDireccion() {
        return txtDireccion;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public JComboBox<String> getComboEstado() {
        return comboEstado;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JTable getTabla() {
        return tabla;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void ocultarColumnas() {
        int[] columnasAOcultar = {0, 9, 10};
        for (int col : columnasAOcultar) {
            tabla.getColumnModel().getColumn(col).setMinWidth(0);
            tabla.getColumnModel().getColumn(col).setMaxWidth(0);
            tabla.getColumnModel().getColumn(col).setWidth(0);
        }
    }

    //Validar campos que esten todos completos
    public boolean validarCampos() {

        if (txtNombre.getText().trim().isEmpty()) {
            showError("El campo Nombre es obligatorio.");
            return false;
        }
        if (txtApellido.getText().trim().isEmpty()) {
            showError("El campo Apellido es obligatorio.");
            return false;
        }
        if (txtFechaNacimiento.getText().trim().isEmpty()) {
            showError("El campo Fecha de Nacimiento es obligatorio.");
            return false;
        }
        if (comboGenero.getSelectedIndex() == 0) {
            showError("Debe seleccionar un Género.");
            return false;
        }
        if (comboTipoSangre.getSelectedIndex() == 0) {
            showError("Debe seleccionar un Tipo de Sangre.");
            return false;
        }
        if (txtDireccion.getText().trim().isEmpty()) {
            showError("El campo Dirección es obligatorio.");
            return false;
        }
        if (txtTelefono.getText().trim().isEmpty()) {
            showError("El campo Teléfono es obligatorio.");
            return false;
        }
        if (txtCorreo.getText().trim().isEmpty()) {
            showError("El campo Correo es obligatorio.");
            return false;
        }
        return true;
    }

    private void showError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Validación", JOptionPane.WARNING_MESSAGE);
    }
}
