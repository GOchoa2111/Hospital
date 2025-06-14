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

// Importaciones para iText 7 y manejo de archivos
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.borders.Border;
import java.io.File;
import java.awt.Desktop; // Necesario para abrir el PDF automáticamente

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
    private List<ModelHistorialReporte> historialActual; // Almacena el historial para poder exportarlo

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
        
        // Enlazamos el botón de exportar
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
            
            // CORRECCIÓN: Crear un nuevo DefaultComboBoxModel y asignarlo
            DefaultComboBoxModel<ModelPaciente> model = new DefaultComboBoxModel<>();
            
            for (ModelPaciente paciente : pacientes) {
                model.addElement(paciente);
            }
            // Asignar el modelo recién creado al JComboBox de la vista
            view.getCmbPaciente().setModel(model); 

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
            this.historialActual = serviceReportes.obtenerHistorialPorPaciente(idPaciente, usuarioLogueado.getToken()); // Almacenar el historial

            // Preparamos el modelo para nuestra tabla de resultados
            String[] columnas = {"Fecha", "Doctor", "Servicio/Motivo", "Diagnóstico", "Tratamiento"};
            DefaultTableModel tableModel = new DefaultTableModel(columnas, 0){
                 @Override
                 public boolean isCellEditable(int row, int column) {
                    return false; // Hacemos la tabla no editable
                 }
            };
            
            if(historialActual.isEmpty()){ // Corregido: usar historialActual
                JOptionPane.showMessageDialog(view, "El paciente seleccionado no tiene un historial clínico registrado.", "Información", JOptionPane.INFORMATION_MESSAGE);
                view.getBtnExportarPDF().setEnabled(false); // Deshabilitar si no hay datos
            } else {
                // Formateador para mostrar la fecha de manera amigable
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                // Llenamos el modelo con los datos recibidos de la API
                for (ModelHistorialReporte registro : historialActual) {
                    tableModel.addRow(new Object[]{
                        dateFormat.format(registro.getFechaCita()),
                        registro.getNombreDoctor(),
                        registro.getNombreServicio(),
                        registro.getDiagnostico() != null ? registro.getDiagnostico() : "N/A", // Manejo de nulos
                        registro.getTratamiento() != null ? registro.getTratamiento() : "N/A" // Manejo de nulos
                    });
                }
                view.getBtnExportarPDF().setEnabled(true); // Habilitar si hay datos
            }

            // Asignamos el modelo (lleno o vacío) a nuestra tabla en la vista
            view.getTblHistorial().setModel(tableModel);
            
            // La línea a continuación es redundante si ya se maneja en el if/else, se puede eliminar o mantener para claridad.
            // view.getBtnExportarPDF().setEnabled(!historialActual.isEmpty()); // Corregido: usar historialActual

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al generar el reporte: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Lógica para el botón de exportar. Genera un PDF del historial clínico del paciente.
     */
    private void exportarAPDF() {
        if (historialActual == null || historialActual.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Genere un historial primero para poder exportar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ModelPaciente pacienteSeleccionado = (ModelPaciente) view.getCmbPaciente().getSelectedItem();
        if (pacienteSeleccionado == null) {
            JOptionPane.showMessageDialog(view, "Seleccione un paciente para generar el PDF.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Se define el nombre del archivo PDF
            String fileName = "Historial_Clinico_" + pacienteSeleccionado.getNombre() + "_" + pacienteSeleccionado.getApellido() + ".pdf";
            PdfWriter writer = new PdfWriter(fileName);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Colores personalizados
            DeviceRgb primaryColor = new DeviceRgb(52, 152, 219); // Azul primario
            DeviceRgb accentColor = new DeviceRgb(46, 204, 113); // Verde para acentos
            DeviceRgb headerBg = new DeviceRgb(230, 230, 250); // Fondo claro para encabezados de tabla

            // Márgenes del documento
            document.setMargins(30, 20, 30, 20);

            // Encabezado del documento con nombre del hospital y título del reporte
            document.add(new Paragraph("HOSPITAL CONFIANZA MÉDICA")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(18)
                    .setBold()
                    .setFontColor(primaryColor));
            document.add(new Paragraph("Historial Clínico del Paciente")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(14)
                    .setBold());
            // CORRECCIÓN: Pasar un float como grosor de la línea
            document.add(new LineSeparator(new SolidLine(1f)).setStrokeColor(primaryColor)); // Línea separadora
            
            // Información del paciente
            document.add(new Paragraph("\nDATOS DEL PACIENTE")
                    .setBold()
                    .setFontSize(12)
                    .setFontColor(primaryColor));
            document.add(new Paragraph("Nombre: " + pacienteSeleccionado.getNombre() + " " + pacienteSeleccionado.getApellido()));
            document.add(new Paragraph("Fecha de Nacimiento: " + pacienteSeleccionado.getFechaNacimiento()));
            document.add(new Paragraph("Género: " + pacienteSeleccionado.getGenero()));
            document.add(new Paragraph("Tipo de Sangre: " + pacienteSeleccionado.getTipoSangre()));
            document.add(new Paragraph("Contacto: " + pacienteSeleccionado.getTelefono() + " | " + pacienteSeleccionado.getCorreo()));
            document.add(new Paragraph("Dirección: " + pacienteSeleccionado.getDireccion()));
            // CORRECCIÓN: Pasar un float como grosor de la línea
            document.add(new LineSeparator(new SolidLine(0.5f)).setStrokeColor(new DeviceRgb(200,200,200))); // Línea más suave

            // Título de la sección de historial
            document.add(new Paragraph("\nREGISTRO DE CONSULTAS Y SERVICIOS")
                    .setBold()
                    .setFontSize(12)
                    .setFontColor(primaryColor));

            // Creación de la tabla del historial con anchos relativos
            Table table = new Table(UnitValue.createPercentArray(new float[]{2, 2, 2, 3, 3}))
                    .useAllAvailableWidth()
                    .setMarginTop(10);
            
            // Encabezados de la tabla con estilos
            table.addHeaderCell(new Cell().add(new Paragraph("Fecha Cita").setBold()).setBackgroundColor(headerBg).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new Cell().add(new Paragraph("Doctor").setBold()).setBackgroundColor(headerBg).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new Cell().add(new Paragraph("Servicio").setBold()).setBackgroundColor(headerBg).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new Cell().add(new Paragraph("Diagnóstico").setBold()).setBackgroundColor(headerBg).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new Cell().add(new Paragraph("Tratamiento").setBold()).setBackgroundColor(headerBg).setTextAlignment(TextAlignment.CENTER));
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm"); // Formato de fecha para la tabla

            // Datos del historial, iterando sobre historialActual
            for (ModelHistorialReporte registro : historialActual) {
                table.addCell(new Cell().add(new Paragraph(dateFormat.format(registro.getFechaCita()))));
                table.addCell(new Cell().add(new Paragraph(registro.getNombreDoctor())));
                table.addCell(new Cell().add(new Paragraph(registro.getNombreServicio())));
                table.addCell(new Cell().add(new Paragraph(registro.getDiagnostico() != null ? registro.getDiagnostico() : "N/A"))); // Manejo de nulos
                table.addCell(new Cell().add(new Paragraph(registro.getTratamiento() != null ? registro.getTratamiento() : "N/A"))); // Manejo de nulos
            }

            document.add(table); // Añadir la tabla al documento

            // Pie de página con información de generación
            document.add(new Paragraph("\n\nDocumento generado el " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(8)
                    .setFontColor(ColorConstants.GRAY));
            
            document.add(new Paragraph("Hospital Confianza Médica - Siempre a tu servicio")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10)
                    .setFontColor(accentColor));

            document.close(); // Cerrar el documento

            // Mensaje de éxito y apertura automática del PDF
            JOptionPane.showMessageDialog(view, "PDF del historial generado exitosamente en: " + new File(fileName).getAbsolutePath(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(fileName));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al generar el PDF del historial clínico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}