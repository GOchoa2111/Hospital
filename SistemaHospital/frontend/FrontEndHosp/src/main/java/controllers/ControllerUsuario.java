package controllers;

import models.ModelUsuario;
import services.ServiceUsuario;
import views.ViewUsuarios;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.List;
import util.ItemCombo;

public class ControllerUsuario {

    private ViewUsuarios vista;
    private ServiceUsuario servicio;

    public ControllerUsuario(ViewUsuarios vista) {
        this.vista = vista;
        this.servicio = new ServiceUsuario();
        initControladores();
        cargarUsuariosEnTabla();
    }

    private void initControladores() {
        vista.getBtnRegistrar().addActionListener(e -> guardarUsuario());
        vista.getBtnActualizar().addActionListener(e -> actualizarUsuario());
        vista.getBtnEliminar().addActionListener(e -> eliminarUsuario());
        vista.getBtnLimpiar().addActionListener(e -> limpiarCampos());

        vista.getTablaUsuarios().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarUsuarioDesdeTabla();
            }
        });
    }

    private void cargarUsuariosEnTabla() {
        try {
            List<ModelUsuario> lista = servicio.listarUsuarios();
            DefaultTableModel modelo = (DefaultTableModel) vista.getTablaUsuarios().getModel();
            modelo.setRowCount(0);

            for (ModelUsuario u : lista) {
                modelo.addRow(new Object[]{
                    u.getIdUsuario(),
                    u.getNombre(),
                    u.getApellido(),
                    u.getNombreUsuario(),
                    u.getCorreo(),
                    u.getRolId()
                });
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar usuarios: " + e.getMessage());
        }
    }

    private void guardarUsuario() {
        try {
            ModelUsuario usuario = obtenerDatosFormulario();
            boolean creado = servicio.registrarUsuario(usuario);

            if (creado) {
                JOptionPane.showMessageDialog(vista, "Usuario registrado con éxito.");
                cargarUsuariosEnTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar usuario.");
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(vista, "Error al registrar: " + e.getMessage());
        }
    }

    private void actualizarUsuario() {
        try {
            ModelUsuario usuario = obtenerDatosFormulario();
            boolean actualizado = servicio.actualizarUsuario(usuario);

            if (actualizado) {
                JOptionPane.showMessageDialog(vista, "Usuario actualizado.");
                cargarUsuariosEnTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar.");
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(vista, "Error al actualizar: " + e.getMessage());
        }
    }

    private void eliminarUsuario() {
        try {
            int id = Integer.parseInt(vista.getTxtIdUsuario().getText());
            int confirm = JOptionPane.showConfirmDialog(vista, "¿Deseas eliminar el usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean eliminado = servicio.eliminarUsuario(id);

                if (eliminado) {
                    JOptionPane.showMessageDialog(vista, "Usuario eliminado.");
                    cargarUsuariosEnTabla();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al eliminar.");
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(vista, "Error al eliminar: " + e.getMessage());
        }
    }

    private void cargarUsuarioDesdeTabla() {
        int fila = vista.getTablaUsuarios().getSelectedRow();
        if (fila >= 0) {
            vista.getTxtIdUsuario().setText(vista.getTablaUsuarios().getValueAt(fila, 0).toString());
            vista.getTxtNombre().setText(vista.getTablaUsuarios().getValueAt(fila, 1).toString());
            vista.getTxtApellido().setText(vista.getTablaUsuarios().getValueAt(fila, 2).toString());
            vista.getTxtNombreUsuario().setText(vista.getTablaUsuarios().getValueAt(fila, 3).toString());
            vista.getTxtCorreo().setText(vista.getTablaUsuarios().getValueAt(fila, 4).toString());

            String rolTexto = vista.getTablaUsuarios().getValueAt(fila, 5).toString().toLowerCase();

            JComboBox<ItemCombo> combo = vista.getComboRol();
            for (int i = 0; i < combo.getItemCount(); i++) {
                if (combo.getItemAt(i).toString().equalsIgnoreCase(rolTexto)) {
                    combo.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void limpiarCampos() {
        vista.getTxtIdUsuario().setText("");
        vista.getTxtNombre().setText("");
        vista.getTxtApellido().setText("");
        vista.getTxtNombreUsuario().setText("");
        vista.getTxtCorreo().setText("");
        vista.getTxtContrasena().setText("");
        // Si tienes otros campos, limpia aquí también
    }

    private ModelUsuario obtenerDatosFormulario() {
        ModelUsuario u = new ModelUsuario();
        String id = vista.getTxtIdUsuario().getText();

        if (!id.isEmpty()) {
            u.setIdUsuario(Integer.parseInt(id));
        }

        u.setNombre(vista.getTxtNombre().getText());
        u.setApellido(vista.getTxtApellido().getText());
        u.setNombreUsuario(vista.getTxtNombreUsuario().getText());
        u.setCorreo(vista.getTxtCorreo().getText());
        u.setContrasena(vista.getTxtContrasena().getText());

        // Obtener el Item seleccionado del combo y su ID para el rol
        ItemCombo item = (ItemCombo) vista.getComboRol().getSelectedItem();
        if (item != null) {
            u.setRolId(item.getId());
        } else {
            u.setRolId(-1); // O valor por defecto
        }

        return u;
    }
}
