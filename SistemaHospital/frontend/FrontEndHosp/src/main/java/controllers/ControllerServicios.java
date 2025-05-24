package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
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
        
        //Agregar el listener para los botones
        // Boton para agregar registro
        this.vista.getBtnAgregar().addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                agregarServicio();  
            }  
        });
        
        //Boton para limpiar campos
            this.vista.getBtnLimpiar().addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                limpiarCampos();  
            }  
        });
        
        

        // Agregar listener para detectar selección de fila en la tabla
        
        this.vista.getTblServicios().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    cargarDatosFilaSeleccionada();
                    //Acción de botones despues de seleccionar una fila y deshabilitar el boton agregar
                    
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
    
    //Metodo para agregar servicio
    
    public void agregarServicio() {
    try {
        // Leer valores del formulario
        String idServicioStr = vista.getTxtIdServicio().getText().trim();
        String nombre = vista.getTxtNombre().getText().trim();
        String descripcion = vista.getTxtDescripcion().getText().trim();
        String precioStr = vista.getTxtPrecio().getText().trim();

        // Validar campos obligatorios
        if (nombre.isEmpty() || descripcion.isEmpty() || precioStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convertir idServicio (opcional, si es autogenerado puede omitirse)
        int idServicio = 0;
        if (!idServicioStr.isEmpty()) {
            try {
                idServicio = Integer.parseInt(idServicioStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista, "El ID del servicio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Convertir precio
        double precio;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio <= 0) {
                JOptionPane.showMessageDialog(vista, "El precio debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear objeto servicio
        ModelServicios nuevoServicio = new ModelServicios();
        nuevoServicio.setIdServicio(idServicio); // Si el ID es autogenerado, puedes omitir esta línea
        nuevoServicio.setNombre(nombre);
        nuevoServicio.setDescripcion(descripcion);
        nuevoServicio.setPrecio(precio);

        // Llamar al servicio para agregar
        
        boolean registrado = servicio.agregarPaciente(nuevoServicio);

        if (registrado) {
            JOptionPane.showMessageDialog(vista, "Servicio registrado correctamente.");
            limpiarCampos();
            obtenerServicios();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al registrar el servicio.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(vista, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}
  
    private void limpiarCampos() {  
        vista.getTxtIdServicio().setText("");  
        vista.getTxtNombre().setText("");  
        vista.getTxtDescripcion().setText("");  
        vista.getTxtPrecio().setText("");  
  
        // Ajustar botones  
        vista.getBtnAgregar().setEnabled(true);  
        vista.getBtnActualizar().setEnabled(false);  
        vista.getBtnEliminar().setEnabled(false);  
        vista.getBtnLimpiar().setEnabled(false);  
  
        vista.getTxtNombre().requestFocus();  
    }  
}
