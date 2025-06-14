package controllers;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.ModelHistorialReporte;
import models.ModelLogin;
import models.ModelPaciente;
import services.ServicePaciente;
import services.ServiceReportes;
import views.ViewHistorialPaciente;

/**
 * Esta clase es el "cerebro" de la ventana de reportes. Conecta la vista
 * con los servicios de datos.
 */
public class ControllerHistorialPaciente {

    // --- Variables de Instancia ---
    private final ViewHistorialPaciente view;
    private final ModelLogin usuarioLogueado;
    private final ServicePaciente servicePaciente;
    private final ServiceReportes serviceReportes;

    /**
     * Constructor. Se ejecuta cuando se crea la ventana.
     * @param view La interfaz gráfica que este controlador manejará.
     * @param usuario El usuario que ha iniciado sesión, necesario para el token de autenticación.
     */
    public ControllerHistorialPaciente(ViewHistorialPaciente view, ModelLogin usuario) {
        this.view = view;
        this.usuarioLogueado = usuario;
        
        // Inicializamos los servicios que vamos a necesitar
        this.servicePaciente = new ServicePaciente();
        this.serviceReportes = new ServiceReportes();

        // Enlazamos el evento del botón "Generar" a nuestro método
        this.view.getBtnGenerar().addActionListener(e -> generarReporte());
        
        // Enlazamos el botón de exportar (por ahora mostrará un mensaje)
        this.view.getBtnExportarPDF().addActionListener(e -> exportarAPDF());

        // Cargamos los pacientes en la lista desplegable en cuanto se abre la ventana
        poblarComboPacientes();
    }

    /**
     * Carga la lista de pacientes desde la API y la muestra en el JComboBox.
     */
    private void poblarComboPacientes() {
        try {
            List<ModelPaciente> pacientes = servicePaciente.obtenerPacientes(usuarioLogueado.getToken());
            DefaultComboBoxModel<ModelPaciente> model = (DefaultComboBoxModel<ModelPaciente>) view.getCmbPaciente().getModel();
            model.removeAllElements();
            
            for (ModelPaciente paciente : pacientes) {
                model.addElement(paciente);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al cargar la lista de pacientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Se ejecuta al presionar "Generar Historial". Pide el historial a la API y lo muestra en la tabla.
     */
    private void generarReporte() {
        ModelPaciente pacienteSeleccionado = (ModelPaciente) view.getCmbPaciente().getSelectedItem();

        if (pacienteSeleccionado == null) {
            JOptionPane.showMessageDialog(view, "Por favor, seleccione un paciente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idPaciente = pacienteSeleccionado.getIdPaciente();
            List<ModelHistorialReporte> historial = serviceReportes.obtenerHistorialPorPaciente(idPaciente, usuarioLogueado.getToken());

            // Preparamos el modelo para nuestra tabla de resultados
            String[] columnas = {"Fecha", "Doctor", "Servicio/Motivo", "Diagnóstico", "Tratamiento"};
            DefaultTableModel tableModel = new DefaultTableModel(columnas, 0){
                 @Override
                 public boolean isCellEditable(int row, int column) {
                    return false; // Hacemos la tabla no editable
                 }
            };
            
            if(historial.isEmpty()){
                JOptionPane.showMessageDialog(view, "El paciente seleccionado no tiene un historial clínico registrado.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                 // Formateador para mostrar la fecha de manera amigable
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                // Llenamos el modelo con los datos recibidos de la API
                for (ModelHistorialReporte registro : historial) {
                    tableModel.addRow(new Object[]{
                        dateFormat.format(registro.getFechaCita()),
                        registro.getNombreDoctor(),
                        registro.getNombreServicio(),
                        registro.getDiagnostico(),
                        registro.getTratamiento()
                    });
                }
            }

            // Asignamos el modelo (lleno o vacío) a nuestra tabla en la vista
            view.getTblHistorial().setModel(tableModel);
            
            // Habilitamos el botón de exportar solo si hay datos que mostrar
            view.getBtnExportarPDF().setEnabled(!historial.isEmpty());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al generar el reporte: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Lógica para el botón de exportar (funcionalidad futura).
     */
    private void exportarAPDF() {
        JOptionPane.showMessageDialog(view, "La funcionalidad de exportar a PDF se implementará en un futuro.", "Próximamente", JOptionPane.INFORMATION_MESSAGE);
    }
}