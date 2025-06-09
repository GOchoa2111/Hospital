/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.time.LocalDate;
import models.ModelPaciente;
import services.ServicePaciente;
import views.ViewPacientes;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ControllerPaciente {

    private ViewPacientes vista; // Referencia a la vista
    private ServicePaciente servicio; // Referencia al servicio

    // Constructor de la clase ControllerPaciente
    // Este constructor recibe como parámetro la vista (formulario de pacientes)
    public ControllerPaciente(ViewPacientes vista) {
        this.vista = vista;
        this.servicio = new ServicePaciente();
    }

    // Método para llenar la tabla de pacientes
    public void cargarPacientesEnTabla() {

        ArrayList<ModelPaciente> pacientes = servicio.obtenerPacientes();
        //DefaultTableModel modeloTabla = (DefaultTableModel) vista.getTabla().getModel();//codigo para cargar desde el model de ViewPaciente(no fucniono)
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

        modelo.setRowCount(0); // Limpiar la tabla antes de cargar

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
                p.getEstadoComoTexto()//este campo es tipo bit en la base
            };
            modelo.addRow(fila);
        }
        vista.getTabla().setModel(modelo);//asignar el modelo a la tabla en la vista
        vista.ocultarColumnas(); //cargar metodo para ocultar columnas según su ubicación
        
        
    }

    //Agregar Paciente
    public void registrarPaciente() {

        try {

            // Leer valores del formulario
            String nombre = vista.getTxtNombre().getText();
            String apellido = vista.getTxtApellido().getText();
            String fechaNacimientoStr = vista.getTxtFechaNacimiento().getText(); // Formato: yyyy-MM-dd
            String genero = vista.getComboGenero().getSelectedItem().toString();
            String tipoSangre = vista.getComboTipoSangre().getSelectedItem().toString();
            String direccion = vista.getTxtDireccion().getText();
            String telefono = vista.getTxtTelefono().getText();
            String correo = vista.getTxtCorreo().getText();
            String estadoStr = vista.getComboEstado().getSelectedItem().toString();//se obtiene el dato en string
            boolean estado = estadoStr.equalsIgnoreCase("Activo") || estadoStr.equalsIgnoreCase("true");//se convierte a boolean

            // Conversión de fecha
            LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr);

            // Simular valores automáticos (puedes adaptar según tu lógica)
            int creadoPor = 1; // Por ejemplo, ID del usuario que crea
            LocalDate fechaCreacion = LocalDate.now();

            // Crear objeto paciente
            ModelPaciente paciente = new ModelPaciente(
                    0, nombre, apellido, fechaNacimiento, genero,
                    tipoSangre, direccion, telefono, correo,
                    creadoPor, fechaCreacion, estado
            );

            // Llamar al servicio
            boolean registrado = servicio.registrarPaciente(paciente);
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

    //Actualizar Paciente
    public void actualizarPaciente() {
        int fila = vista.getTabla().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un paciente de la tabla para actualizar.");
            return;
        }

        try {
            // Obtener ID del paciente desde la tabla
            int id = Integer.parseInt(vista.getTabla().getValueAt(fila, 0).toString());

            // Leer valores del formulario
            String nombre = vista.getTxtNombre().getText();
            String apellido = vista.getTxtApellido().getText();
            String fechaNacimiento = vista.getTxtFechaNacimiento().getText(); // O usa DatePicker si aplica
            String genero = vista.getComboGenero().getSelectedItem().toString();
            String tipoSangre = vista.getComboTipoSangre().getSelectedItem().toString();
            String direccion = vista.getTxtDireccion().getText();
            String telefono = vista.getTxtTelefono().getText();
            String correo = vista.getTxtCorreo().getText();
            String estadoStr = vista.getComboEstado().getSelectedItem().toString();
            boolean estado = estadoStr.equalsIgnoreCase("Activo") || estadoStr.equalsIgnoreCase("true");

            // Crear objeto paciente setear los datos
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
            paciente.setCreadoPor(id);
            paciente.setEstado(estado);

            // Enviar al servicio
            boolean actualizado = servicio.actualizarPaciente(paciente);
            if (actualizado) {
                JOptionPane.showMessageDialog(null, "Paciente actualizado correctamente.");
                cargarPacientesEnTabla(); // Recarga la tabla
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar paciente.");

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    //Eliminar paciente
    
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
        // Obtener el ID del paciente de la tabla
        int id = Integer.parseInt(vista.getTabla().getValueAt(fila, 0).toString());
        System.out.println("Id para eliminar " + id);
        // Llamar al servicio para eliminar
        
        boolean eliminado = servicio.eliminarPaciente(id);

        if (eliminado) {
            JOptionPane.showMessageDialog(null, "Paciente eliminado correctamente.");
            cargarPacientesEnTabla(); // refresca la tabla
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el paciente. ", "Error", JOptionPane.ERROR_MESSAGE );
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
    }
}

    
    
    //limpiar campos despues de agregar
    public void limpiarFormulario() {

        vista.getTxtNombre().setText("");
        vista.getTxtApellido().setText("");
        vista.getTxtFechaNacimiento().setText("");
        vista.getComboGenero().setSelectedIndex(0); // O el índice correspondiente a "Seleccione"
        vista.getComboTipoSangre().setSelectedIndex(0);
        vista.getTxtDireccion().setText("");
        vista.getTxtTelefono().setText("");
        vista.getTxtCorreo().setText("");
        vista.getComboEstado().setSelectedIndex(0); // valor predeterminado Activo
        
        // Ajustar botones  
        vista.getBtnRegistrar().setEnabled(true);  
        vista.getBtnActualizar().setEnabled(false);  
        vista.getBtnEliminar().setEnabled(false);  
        vista.getBtnLimpiar().setEnabled(false);  
  
        vista.getTxtNombre().requestFocus();  
    }

}
