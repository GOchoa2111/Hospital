package views;

import controllers.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import models.ModelUsuario;
import services.ServiceUsuario;
import utils.SessionManager;
import java.util.List;
import util.ItemCombo;


/*// Clase para manejar items del combo rol
class ItemCombo {

    private int id;
    private String nombre;

    public ItemCombo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return nombre;
    }
}*/
public class ViewUsuarios extends JInternalFrame {

    private JTextField txtIdUsuario;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtNombreUsuario;
    private JPasswordField txtContrasena;
    private JTextField txtCorreo;
    private JComboBox<ItemCombo> comboRol;

    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;

    private JButton btnRegistrar, btnActualizar, btnEliminar, btnLimpiar;

    public ViewUsuarios() {
        super("Gestión de Usuarios", true, true, true, true);
        setSize(900, 550);
        setLayout(new BorderLayout());

        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtIdUsuario = new JTextField();
        txtIdUsuario.setVisible(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCampos.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(15);
        panelCampos.add(txtNombre, gbc);

        gbc.gridx = 2;
        panelCampos.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 3;
        txtApellido = new JTextField(15);
        panelCampos.add(txtApellido, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCampos.add(new JLabel("Nombre Usuario:"), gbc);
        gbc.gridx = 1;
        txtNombreUsuario = new JTextField(15);
        panelCampos.add(txtNombreUsuario, gbc);

        gbc.gridx = 2;
        panelCampos.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 3;
        txtContrasena = new JPasswordField(15);
        panelCampos.add(txtContrasena, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelCampos.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1;
        txtCorreo = new JTextField(15);
        panelCampos.add(txtCorreo, gbc);

        gbc.gridx = 2;
        panelCampos.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 3;
        comboRol = new JComboBox<>();
        comboRol.addItem(new ItemCombo(1, "administrador"));
        comboRol.addItem(new ItemCombo(2, "medico"));
        comboRol.addItem(new ItemCombo(3, "recepcionista"));
        comboRol.addItem(new ItemCombo(4, "gestor"));
        panelCampos.add(comboRol, gbc);

        add(panelCampos, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID Usuario", "Nombre", "Apellido", "Nombre Usuario", "Correo", "Rol"}, 0
        ) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaUsuarios = new JTable(modeloTabla);
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollTabla = new JScrollPane(tablaUsuarios);
        add(scrollTabla, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnRegistrar = new JButton("Registrar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        add(panelBotones, BorderLayout.SOUTH);

        tablaUsuarios.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tablaUsuarios.getSelectedRow() != -1) {
                    int fila = tablaUsuarios.getSelectedRow();
                    txtIdUsuario.setText(modeloTabla.getValueAt(fila, 0).toString());
                    txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                    txtApellido.setText(modeloTabla.getValueAt(fila, 2).toString());
                    txtNombreUsuario.setText(modeloTabla.getValueAt(fila, 3).toString());
                    txtCorreo.setText(modeloTabla.getValueAt(fila, 4).toString());
                    String rolTexto = modeloTabla.getValueAt(fila, 5).toString().toLowerCase();
                    for (int i = 0; i < comboRol.getItemCount(); i++) {
                        if (comboRol.getItemAt(i).toString().equalsIgnoreCase(rolTexto)) {
                            comboRol.setSelectedIndex(i);
                            break;
                        }
                    }
                    btnRegistrar.setEnabled(false);
                    btnActualizar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                }
            }
        });

    }

    public void limpiarCampos() {
        txtIdUsuario.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtNombreUsuario.setText("");
        txtContrasena.setText("");
        txtCorreo.setText("");
        comboRol.setSelectedIndex(0);
        tablaUsuarios.clearSelection();
        btnRegistrar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    /*public int getRolSeleccionado() {
        ItemCombo item = (ItemCombo) comboRol.getSelectedItem();
       return item != null ? item.getId() : -1;
    }*/
    public ItemCombo getRolSeleccionadoItem() {
        return (ItemCombo) comboRol.getSelectedItem();
    }

public int getRolSeleccionado() {
    ItemCombo item = (ItemCombo) comboRol.getSelectedItem();
    return item != null ? item.getId() : -1;
}


    public int getIdUsuario() {
        if (txtIdUsuario.getText().isEmpty()) {
            return 0;
        }
        return Integer.parseInt(txtIdUsuario.getText());
    }

    public String getNombre() {
        return txtNombre.getText().trim();
    }

    public String getApellido() {
        return txtApellido.getText().trim();
    }

    public String getNombreUsuario() {
        return txtNombreUsuario.getText().trim();
    }

    public String getContrasena() {
        return new String(txtContrasena.getPassword());
    }

    public String getCorreo() {
        return txtCorreo.getText().trim();
    }

    public void agregarUsuarioATabla(Object[] fila) {
        modeloTabla.addRow(fila);
    }

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
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

    public JTable getTablaUsuarios() {
        return tablaUsuarios;
    }

    public JTextField getTxtIdUsuario() {
        return txtIdUsuario;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtApellido() {
        return txtApellido;
    }

    public JTextField getTxtNombreUsuario() {
        return txtNombreUsuario;
    }

    public JPasswordField getTxtContrasena() {
        return txtContrasena;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public JComboBox<ItemCombo> getComboRol() {
        return comboRol;
    }

}
