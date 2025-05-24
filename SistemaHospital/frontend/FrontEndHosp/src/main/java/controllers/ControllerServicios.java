package controllers;

import java.util.List;
import models.ModelServicios;

import javax.swing.table.DefaultTableModel;
import views.ViewServicios;

public class ControllerServicios {

    private ViewServicios vista; // Referencia a la vista
    private services.ServiceServicios servicio; // Referencia al servicio

    // Constructor de la clase ControllerPaciente
    // Este constructor recibe como parámetro la vista (formulario de pacientes)
    public ControllerServicios(views.ViewServicios vista) {
        this.vista = vista;
        this.servicio = new services.ServiceServicios();
    }

    // Método para llenar la tabla de pacientes
    public void obtenerServicios() {
        

        List<ModelServicios> servicios = servicio.obtenerServicios();
        //validación de la entrada de datos
        if (servicios == null || servicios.isEmpty()) {  
        System.out.println("No se recibieron servicios para cargar.");  
        return;  
    }
        DefaultTableModel modelo = new DefaultTableModel();
    

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Precio");

        modelo.setRowCount(0); // Limpiar la tabla antes de cargar

        for (ModelServicios s : servicios) {
            Object[] fila = {
                s.getIdServicio(),
                s.getNombre(),
                s.getDescripcion(),
                s.getPrecio()

            };
            modelo.addRow(fila);
        }
        vista.getTblServicios().setModel(modelo);//asignar el modelo a la tabla en la vista
        // vista.ocultarColumnas(); //cargar metodo para ocultar columnas según su ubicación

    }

}
