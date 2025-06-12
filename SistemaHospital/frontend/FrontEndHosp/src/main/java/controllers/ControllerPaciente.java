// ControllerPaciente.java
package controllers;

import models.ModelPaciente;
import services.ServicePaciente;
import views.ViewPacientes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.ArrayList;

public class ControllerPaciente {

    private ViewPacientes vista;
    private ServicePaciente servicio;
    private String token;
    private int idUsuario;

    public ControllerPaciente(ViewPacientes vista, String token, int idUsuario) {
        this.vista = vista;
        this.token = token;
        this.idUsuario = idUsuario;
        this.servicio = new ServicePaciente();
    }

    public void cargarPacientesEnTabla() {
        ArrayList<ModelPaciente> pacientes = servicio.obtenerPacientes(token);
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Fecha Nacimiento");
        modelo.addColumn("Género");
        modelo.addColumn("Tipo Sangre");
        modelo.addColumn("Dirección");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Correo");
        modelo.addColumn("CreadoPor");
        modelo.addColumn("fechaCreacion");
        modelo.addColumn("estado");

        modelo.setRowCount(0);

        for (ModelPaciente p : pacientes) {
            Object[] fila = {
                p.getIdPaciente(),
                p.getNombre(),
                p.getApellido(),
                p.getFechaNacimiento(),
                p.getGenero(),
                p.getTipoSangre(),
                p.getDireccion(),
                p.getTelefono(),
                p.getCorreo(),
                p.getCreadoPor(),
                p.getFechaCreacion(),
                p.getEstadoComoTexto()
            };
            modelo.addRow(fila);
        }
        vista.getTabla().setModel(modelo);
        vista.ocultarColumnas();
    }

    public void registrarPaciente() {
        try {
            if (!vista.validarCampos()) {
                return;
            }

            String nombre = vista.getTxtNombre().getText();
            String apellido = vista.getTxtApellido().getText();
            String fechaNacimientoStr = vista.getTxtFechaNacimiento().getText();
            String genero = vista.getComboGenero().getSelectedItem().toString();
            String tipoSangre = vista.getComboTipoSangre().getSelectedItem().toString();
            String direccion = vista.getTxtDireccion().getText();
            String telefono = vista.getTxtTelefono().getText();
            String correo = vista.getTxtCorreo().getText();
            String estadoStr = vista.getComboEstado().getSelectedItem().toString();
            boolean estado = estadoStr.equalsIgnoreCase("Activo") || estadoStr.equalsIgnoreCase("true");

            LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr);

            ModelPaciente paciente = new ModelPaciente(
                    0, nombre, apellido, fechaNacimiento, genero,
                    tipoSangre, direccion, telefono, correo,
                    idUsuario, LocalDate.now(), estado
            );

            boolean registrado = servicio.registrarPaciente(paciente, token);
            if (registrado) {
                JOptionPane.showMessageDialog(null, "Paciente registrado correctamente.");
                limpiarFormulario();
                cargarPacientesEnTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar paciente.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "La formato de la fecha ingresada es incorrecta: " + ex.getMessage());
        }
    }

    public void actualizarPaciente() {
        int fila = vista.getTabla().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un paciente de la tabla para actualizar.");
            return;
        }

        try {
            int id = Integer.parseInt(vista.getTabla().getValueAt(fila, 0).toString());

            String nombre = vista.getTxtNombre().getText();
            String apellido = vista.getTxtApellido().getText();
            String fechaNacimiento = vista.getTxtFechaNacimiento().getText();
            String genero = vista.getComboGenero().getSelectedItem().toString();
            String tipoSangre = vista.getComboTipoSangre().getSelectedItem().toString();
            String direccion = vista.getTxtDireccion().getText();
            String telefono = vista.getTxtTelefono().getText();
            String correo = vista.getTxtCorreo().getText();
            String estadoStr = vista.getComboEstado().getSelectedItem().toString();
            boolean estado = estadoStr.equalsIgnoreCase("Activo") || estadoStr.equalsIgnoreCase("true");

            ModelPaciente paciente = new ModelPaciente();
            paciente.setIdPaciente(id);
            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setFechaNacimiento(LocalDate.parse(fechaNacimiento));
            paciente.setGenero(genero);
            paciente.setTipoSangre(tipoSangre);
            paciente.setDireccion(direccion);
            paciente.setTelefono(telefono);
            paciente.setCorreo(correo);
            paciente.setEstado(estado);
            paciente.setCreadoPor(idUsuario);

            boolean actualizado = servicio.actualizarPaciente(paciente, token);
            if (actualizado) {
                JOptionPane.showMessageDialog(null, "Paciente actualizado correctamente.");
                cargarPacientesEnTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar paciente.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    public void eliminarPaciente() {
        int fila = vista.getTabla().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un paciente de la tabla para eliminar.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este paciente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            int id = Integer.parseInt(vista.getTabla().getValueAt(fila, 0).toString());

            boolean eliminado = servicio.eliminarPaciente(id, token);

            if (eliminado) {
                JOptionPane.showMessageDialog(null, "Paciente eliminado correctamente.");
                cargarPacientesEnTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el paciente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    public void limpiarFormulario() {
        vista.getTxtId().setText("");
        vista.getTxtNombre().setText("");
        vista.getTxtApellido().setText("");
        vista.getTxtFechaNacimiento().setText("");
        vista.getComboGenero().setSelectedIndex(0);
        vista.getComboTipoSangre().setSelectedIndex(0);
        vista.getTxtDireccion().setText("");
        vista.getTxtTelefono().setText("");
        vista.getTxtCorreo().setText("");
        vista.getComboEstado().setSelectedIndex(0);

        vista.getBtnRegistrar().setEnabled(true);
        vista.getBtnActualizar().setEnabled(false);
        vista.getBtnEliminar().setEnabled(false);
        vista.getBtnLimpiar().setEnabled(false);

        vista.getTxtNombre().requestFocus();
    }
}
