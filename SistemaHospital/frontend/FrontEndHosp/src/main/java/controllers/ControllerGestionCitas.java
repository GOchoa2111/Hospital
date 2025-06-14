package controllers;

import models.*;
import services.*;
import views.ViewGestionCitas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class ControllerGestionCitas {

    private final ViewGestionCitas view;
    private final ModelLogin usuarioLogueado;
    private final ServicePaciente servicePaciente;
    private final ServiceDoctores serviceDoctor;
    private final ServiceServicios serviceServicio;
    private final ServiceCitas serviceCita;
    private List<ModelCita> listaDeCitas;
    private List<ModelPaciente> listaDePacientes;
    private List<ModelDoctores> listaDeDoctores;
    private List<ModelServicios> listaDeServicios;

    public ControllerGestionCitas(ViewGestionCitas view, ModelLogin usuario) {
        this.view = view;
        this.usuarioLogueado = usuario;
        
        this.servicePaciente = new ServicePaciente();
        this.serviceDoctor = new ServiceDoctores();
        this.serviceServicio = new ServiceServicios();
        this.serviceCita = new ServiceCitas();
        
        this.view.getBtnGuardar().addActionListener(e -> guardarCita());
        this.view.getBtnActualizar().addActionListener(e -> actualizarCita());
        this.view.getBtnEliminar().addActionListener(e -> eliminarCita());
        this.view.getBtnNotificar().addActionListener(e -> notificarCita());
        this.view.getBtnLimpiar().addActionListener(e -> limpiarFormulario());

        this.view.getTblCitas().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                manejarSeleccionDeTabla();
            }
        });
        
        poblarComboBoxes();
        cargarCitasEnTabla();
    }

    private void poblarComboBoxes() {
        try {
            listaDePacientes = servicePaciente.obtenerPacientes(usuarioLogueado.getToken());
            listaDeDoctores = serviceDoctor.obtenerDoctores(usuarioLogueado.getToken());
            listaDeServicios = serviceServicio.obtenerServicios();

            view.getCmbPaciente().setModel(new DefaultComboBoxModel<>(listaDePacientes.toArray(new ModelPaciente[0])));
            view.getCmbDoctor().setModel(new DefaultComboBoxModel<>(listaDeDoctores.toArray(new ModelDoctores[0])));
            view.getCmbServicio().setModel(new DefaultComboBoxModel<>(listaDeServicios.toArray(new ModelServicios[0])));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al cargar listas desplegables: " + e.getMessage(), "Error de Carga", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarCitasEnTabla() {
        try {
            this.listaDeCitas = serviceCita.obtenerCitas(usuarioLogueado.getToken());
            
            String[] columnas = {"ID", "Paciente", "Doctor", "Fecha y Hora", "Estado"};
            DefaultTableModel tableModel = new DefaultTableModel(columnas, 0) {
                 @Override
                 public boolean isCellEditable(int row, int column) { return false; }
            };

            for (ModelCita cita : this.listaDeCitas) {
                String nombrePaciente = listaDePacientes.stream().filter(p -> p.getIdPaciente() == cita.getIdPaciente()).findFirst().map(Object::toString).orElse("N/A");
                String nombreDoctor = listaDeDoctores.stream().filter(d -> d.getIdDoctor() == cita.getIdDoctor()).findFirst().map(Object::toString).orElse("N/A");
                
                tableModel.addRow(new Object[]{
                    cita.getIdCita(),
                    nombrePaciente,
                    nombreDoctor,
                    new SimpleDateFormat("dd/MM/yyyy HH:mm").format(cita.getFechaHora()),
                    cita.getEstado()
                });
            }
            view.getTblCitas().setModel(tableModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al cargar citas: " + e.getMessage(), "Error de Carga", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarCita() {
        if (!validarFormulario()) return;
        try {
            ModelCita nuevaCita = construirCitaDesdeFormulario();
            boolean exito = serviceCita.crearCita(nuevaCita, usuarioLogueado.getToken());

            if (exito) {
                JOptionPane.showMessageDialog(view, "Cita guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarCitasEnTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(view, "Error al guardar la cita.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error inesperado al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarCita() {
    int filaSeleccionada = view.getTblCitas().getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(view, "Por favor, seleccione una cita de la tabla para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    if (!validarFormulario()) return; 
    
    try {
        int idCita = (int) view.getTblCitas().getValueAt(filaSeleccionada, 0);
        
        ModelCita citaActualizada = construirCitaDesdeFormulario();
        citaActualizada.setIdCita(idCita); 

        boolean exito = serviceCita.actualizarCita(citaActualizada, usuarioLogueado.getToken());
        
        if (exito) {
            JOptionPane.showMessageDialog(view, "Cita actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarCitasEnTabla(); 
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(view, "No se pudo actualizar la cita.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(view, "Error inesperado al actualizar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void eliminarCita() {
        int filaSeleccionada = view.getTblCitas().getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(view, "Por favor, seleccione una cita para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view, "¿Está seguro de que desea eliminar esta cita?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int idCita = (int) view.getTblCitas().getValueAt(filaSeleccionada, 0);
                boolean exito = serviceCita.eliminarCita(idCita, usuarioLogueado.getToken());
                if (exito) {
                    JOptionPane.showMessageDialog(view, "Cita eliminada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarCitasEnTabla();
                    limpiarFormulario();
                } else {
                    JOptionPane.showMessageDialog(view, "Error al eliminar la cita.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Error inesperado al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void notificarCita() {
        int filaSeleccionada = view.getTblCitas().getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(view, "Por favor, seleccione una cita para notificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int idCita = (int) view.getTblCitas().getValueAt(filaSeleccionada, 0);
            boolean exito = serviceCita.notificarCitaPorCorreo(idCita, usuarioLogueado.getToken());
            if (exito) {
                JOptionPane.showMessageDialog(view, "Recordatorio enviado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, "Error al enviar la notificación.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error inesperado al notificar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void manejarSeleccionDeTabla() {
    int filaSeleccionada = view.getTblCitas().getSelectedRow();
    if (filaSeleccionada == -1) {
        limpiarFormulario();
        return;
    }

    int idCita = (int) view.getTblCitas().getValueAt(filaSeleccionada, 0);
    ModelCita citaSeleccionada = listaDeCitas.stream()
        .filter(c -> c.getIdCita() == idCita)
        .findFirst().orElse(null);

    if (citaSeleccionada != null) {
        view.getJdcFechaCita().setDate(citaSeleccionada.getFechaHora());
        view.getSpnHoraCita().setValue(citaSeleccionada.getFechaHora());
        view.getCmbEstado().setSelectedItem(citaSeleccionada.getEstado());

        seleccionarItemPorId(view.getCmbPaciente(), citaSeleccionada.getIdPaciente());
        seleccionarItemPorId(view.getCmbDoctor(), citaSeleccionada.getIdDoctor());
        seleccionarItemPorId(view.getCmbServicio(), citaSeleccionada.getIdServicio());
        
        view.getBtnGuardar().setEnabled(false);
        view.getBtnActualizar().setEnabled(true);
        view.getBtnEliminar().setEnabled(true);
        view.getBtnNotificar().setEnabled(true);
    }
}
    
    private void limpiarFormulario() {
        view.getTblCitas().clearSelection();
        if (view.getCmbPaciente().getItemCount() > 0) view.getCmbPaciente().setSelectedIndex(0);
        if (view.getCmbDoctor().getItemCount() > 0) view.getCmbDoctor().setSelectedIndex(0);
        if (view.getCmbServicio().getItemCount() > 0) view.getCmbServicio().setSelectedIndex(0);
        view.getCmbEstado().setSelectedIndex(0);
        view.getJdcFechaCita().setDate(new Date());
        view.getSpnHoraCita().setValue(new Date());

        view.getBtnGuardar().setEnabled(true);
        view.getBtnActualizar().setEnabled(false);
        view.getBtnEliminar().setEnabled(false);
        view.getBtnNotificar().setEnabled(false);
    }
    
    private ModelCita construirCitaDesdeFormulario() {
        ModelPaciente paciente = (ModelPaciente) view.getCmbPaciente().getSelectedItem();
        ModelDoctores doctor = (ModelDoctores) view.getCmbDoctor().getSelectedItem();
        ModelServicios servicio = (ModelServicios) view.getCmbServicio().getSelectedItem();
        Date fecha = view.getJdcFechaCita().getDate();
        Date hora = (Date) view.getSpnHoraCita().getValue();
        String estado = (String) view.getCmbEstado().getSelectedItem();

        Calendar calFecha = Calendar.getInstance();
        calFecha.setTime(fecha);
        Calendar calHora = Calendar.getInstance();
        calHora.setTime(hora);
        calFecha.set(Calendar.HOUR_OF_DAY, calHora.get(Calendar.HOUR_OF_DAY));
        calFecha.set(Calendar.MINUTE, calHora.get(Calendar.MINUTE));
        Date fechaHoraFinal = calFecha.getTime();

        ModelCita cita = new ModelCita();
        cita.setIdPaciente(paciente.getIdPaciente());
        cita.setIdDoctor(doctor.getIdDoctor());
        cita.setIdServicio(servicio.getIdServicio());
        cita.setFechaHora(fechaHoraFinal);
        cita.setEstado(estado);
        return cita;
    }

    private boolean validarFormulario() {
        if (view.getCmbPaciente().getSelectedItem() == null ||
            view.getCmbDoctor().getSelectedItem() == null ||
            view.getCmbServicio().getSelectedItem() == null ||
            view.getJdcFechaCita().getDate() == null) {
            JOptionPane.showMessageDialog(view, "Todos los campos (Paciente, Doctor, Servicio, Fecha) son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void seleccionarItemPorId(JComboBox comboBox, int id) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            Object item = comboBox.getItemAt(i);
            if (item instanceof ModelPaciente && ((ModelPaciente) item).getIdPaciente() == id) {
                comboBox.setSelectedIndex(i); return;
            }
            if (item instanceof ModelDoctores && ((ModelDoctores) item).getIdDoctor() == id) {
                comboBox.setSelectedIndex(i); return;
            }
            if (item instanceof ModelServicios && ((ModelServicios) item).getIdServicio() == id) {
                comboBox.setSelectedIndex(i); return;
            }
        }
    }
}