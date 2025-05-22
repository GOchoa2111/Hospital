package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.ModelRepoCitas;
import views.ViewRepoCitas;
import services.ServiceRepoCitas;

public class ControllerRepoCitas {

    private final ViewRepoCitas vista;
    private final ServiceRepoCitas servicio;
    private final DefaultTableModel modeloTabla; // Modelo de tabla para la búsqueda

    public ControllerRepoCitas(ViewRepoCitas vista) {
        this.vista = vista;
        this.servicio = new ServiceRepoCitas();

        // Inicializar el modelo de la tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID Cita");
        modeloTabla.addColumn("Fecha y Hora");
        modeloTabla.addColumn("Servicio");
        modeloTabla.addColumn("Paciente");
        modeloTabla.addColumn("Doctor");
        
        vista.getTblCitas().setModel(modeloTabla); // Asignar el modelo a la tabla

        cargarCitasEnTabla(); // Cargar todas las citas al inicio

        // Configurar el botón para búsqueda
        vista.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = vista.getTxtBuscar().getText().trim();
                buscarCitas(texto);
            }
        });
    }

    // Método para llenar la tabla de citas con todos los datos
    public void cargarCitasEnTabla() {
        ArrayList<ModelRepoCitas> citas = new ArrayList<>(servicio.obtModelRepoCitas());
        actualizarTabla(citas);
    }

    // Método para actualizar la tabla con una lista de citas
    private void actualizarTabla(List<ModelRepoCitas> citas) {
        modeloTabla.setRowCount(0); // Limpiar datos anteriores

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");

        for (ModelRepoCitas c : citas) {
            Object[] fila = {
                c.getIdCita(),
                c.getFechaHora().format(formatter),
                c.getServicio(),
                c.getPaciente(),
                c.getDoctor()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void buscarCitas(String texto) {
        texto = texto.toLowerCase(); // Búsqueda sin distinción de mayúsculas

        List<ModelRepoCitas> citas = servicio.obtModelRepoCitas();
        List<ModelRepoCitas> resultados = new ArrayList<>();

        for (ModelRepoCitas cita : citas) {
            String id = String.valueOf(cita.getIdCita());
            String fecha = cita.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")).toLowerCase();
            String servicio = cita.getServicio().toLowerCase();
            String paciente = cita.getPaciente().toLowerCase();
            String doctor = cita.getDoctor().toLowerCase();

            if (id.contains(texto) || fecha.contains(texto) || servicio.contains(texto)
                    || paciente.contains(texto) || doctor.contains(texto)) {
                resultados.add(cita);
            }
        }

        actualizarTabla(resultados); // Actualizar la tabla con los resultados
    }
}