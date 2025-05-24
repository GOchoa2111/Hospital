package controllers;

import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import models.ModelServicios;
import services.ServiceServicios;
import views.ViewServicios;

public class ControllerServicios {

    private ViewServicios vista; // Referencia a la vista
    private ServiceServicios servicio; // Referencia al servicio

    public ControllerServicios(ViewServicios vista) {
        this.vista = vista;
        this.servicio = new ServiceServicios();

        // Agregar listener para detectar selección de fila en la tabla
        
        this.vista.getTblServicios().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    cargarDatosFilaSeleccionada();
                    //habilitar los botones despues de seleccionar una fila y deshabilitar el boton agregar
                    
                    vista.getBtnAgregar().setEnabled(false);
                    vista.getBtnEliminar().setEnabled(true);
                    vista.getBtnLimpiar().setEnabled(true);
                    vista.getBtnActualizar().setEnabled(true);
                    
                }
            }
        });
    }

    // Método para llenar la tabla con los datos de servicios
    public void obtenerServicios() {
        
        List<ModelServicios> servicios = servicio.obtenerServicios();

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

        vista.getTblServicios().setModel(modelo);
        // vista.ocultarColumnas(); // Si tienes este método para ocultar columnas
    }

    // Método para cargar datos en los campos para CRUD
    private void cargarDatosFilaSeleccionada() {
        
        int fila = vista.getTblServicios().getSelectedRow();
        if (fila != -1) {
            Object id = vista.getTblServicios().getValueAt(fila, 0);
            Object nombre = vista.getTblServicios().getValueAt(fila, 1);
            Object descripcion = vista.getTblServicios().getValueAt(fila, 2);
            Object precio = vista.getTblServicios().getValueAt(fila, 3);

            vista.getTxtIdServicio().setText(id.toString());
            vista.getTxtNombre().setText(nombre.toString());
            vista.getTxtDescripcion().setText(descripcion.toString());
            vista.getTxtPrecio().setText(precio.toString());
        }
    }
}