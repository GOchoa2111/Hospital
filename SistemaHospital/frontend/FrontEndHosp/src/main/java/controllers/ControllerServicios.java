package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
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
                limpiarCampos();
            }
        });

        //Boton para actualizar registro
        this.vista.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarServicio();
                limpiarCampos();
            }
        });
        
        //Boton para eliminar registro
        this.vista.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarServicio();
                limpiarCampos();
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
            
            //Format para el precio, pasarlo con formato de moneda
            NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance();
            String precioFormateado = formatoMoneda.format(s.getPrecio());
            
            Object[] fila = {
                s.getIdServicio(),
                s.getNombre(),
                s.getDescripcion(),
                precioFormateado //precio con formato
            };
            modelo.addRow(fila);
        }

        vista.getTblServicios().setModel(modelo);
        // metodo para ocultar columnas
        ocultarColumnaID();
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
            String precioStr = vista.getTxtPrecio().getText().trim().replaceAll("[^\\d.,-]", "").replace(",", "");//limpia el texto dejando solo los numeros
            
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

            // Convertir precio a decimal para base de datos
            
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
            //nuevoServicio.setIdServicio(idServicio); //el id se genera automaticamente al crear el registro
            nuevoServicio.setNombre(nombre);
            nuevoServicio.setDescripcion(descripcion);
            nuevoServicio.setPrecio(precio);

            // Llamar al servicio para agregar
            boolean registrado = servicio.agregarServicio(nuevoServicio);

            if (registrado) {
                JOptionPane.showMessageDialog(vista, "Servicio agregado correctamente.");
                limpiarCampos();
                obtenerServicios();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al agregar el servicio.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    //Metodo para actualizar Servicios
    public void actualizarServicio() {
        
    int fila = vista.getTblServicios().getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(vista, "Seleccione un servicio de la tabla para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        // Leer valores del formulario
        String idServicioStr = vista.getTxtIdServicio().getText().trim();
        String nombre = vista.getTxtNombre().getText().trim();
        String descripcion = vista.getTxtDescripcion().getText().trim();
        String precioStr = vista.getTxtPrecio().getText().trim().replaceAll("[^\\d.,-]", "").replace(",", "");//limpia el texto solo para dejar números

        // Validar campos obligatorios
        if (idServicioStr.isEmpty() || nombre.isEmpty() || descripcion.isEmpty() || precioStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convertir idServicio
        int idServicio;
        try {
            idServicio = Integer.parseInt(idServicioStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "El ID del servicio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
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
        ModelServicios servicioActualizar = new ModelServicios();
        servicioActualizar.setIdServicio(idServicio);
        servicioActualizar.setNombre(nombre);
        servicioActualizar.setDescripcion(descripcion);
        servicioActualizar.setPrecio(precio);

        // Llamar al servicio para actualizar
        boolean actualizado = servicio.actualizarServicio(servicioActualizar);

        if (actualizado) {
            JOptionPane.showMessageDialog(vista, "Servicio actualizado correctamente.");
            limpiarCampos();
            obtenerServicios();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al actualizar el servicio.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(vista, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }  
}
    //Eliminar servicio
    
    public void eliminarServicio() {
        
    int fila = vista.getTblServicios().getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(vista, "Seleccione un servicio de la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Está seguro de eliminar el servicio seleccionado?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
    if (confirmacion != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        
        int idServicio = Integer.parseInt(vista.getTblServicios().getValueAt(fila, 0).toString());

        boolean eliminado = servicio.eliminarServicio(idServicio);

        if (eliminado) {
            JOptionPane.showMessageDialog(vista, "Servicio eliminado correctamente.");
            limpiarCampos();
            obtenerServicios();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al eliminar el servicio.", "Error", JOptionPane.ERROR_MESSAGE);
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
    
    private void ocultarColumnaID() {
        
    // Obtener la columna 0 (ID)
    if (vista.getTblServicios().getColumnModel().getColumnCount() > 0) {
        vista.getTblServicios().getColumnModel().getColumn(0).setMinWidth(0);
        vista.getTblServicios().getColumnModel().getColumn(0).setMaxWidth(0);
        vista.getTblServicios().getColumnModel().getColumn(0).setWidth(0);
        vista.getTblServicios().getColumnModel().getColumn(0).setPreferredWidth(0);
    }
}

}
