package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Models.ModelServicios;
import views.ViewServicios;
import services.ServiceServicios;

public class ControllerServicios {

    private final views.ViewServicios vista;
    private final ServiceServicios servicio;
    private final DefaultTableModel modeloTabla; // Modelo de tabla para los servicios

    public ControllerServicios(ViewServicios vista) {
        this.vista = vista;
        this.servicio = new ServiceServicios();
       

        // Inicializar el modelo de la tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID Servicio");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Descripción");
        modeloTabla.addColumn("Precio");

        vista.getTblServicios().setModel(modeloTabla); // Asignar el modelo a la tabla

        cargarServiciosEnTabla(); // Cargar todos los servicios al inicio

        // Configurar el botón para agregar
        vista.getBtnAgregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarServicio();
            }
        });

        // Configurar el botón para editar
        vista.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarServicio();
            }
        });

        // Configurar el botón para eliminar
        vista.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarServicio();
            }
        });

        // Configurar el botón para limpiar
        vista.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        // Configurar evento de clic en la tabla para seleccionar un servicio
        vista.getTblServicios().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarServicio();
            }
        });
    }

    // Método para llenar la tabla de servicios con todos los datos
    public void cargarServiciosEnTabla() {
        
        List<ModelServicios> servicios = servicio.obtenerServicios();
        actualizarTabla(servicios);
         

    }

    // Método para actualizar la tabla con una lista de servicios
    private void actualizarTabla(List<ModelServicios> servicios) {
        modeloTabla.setRowCount(0); // Limpiar datos anteriores

        for (ModelServicios s : servicios) {
            Object[] fila = {
                s.getIdServicio(),
                s.getNombre(),
                s.getDescripcion(),
                s.getPrecio()
            };
            modeloTabla.addRow(fila);
        }
    }

    // Método para agregar un nuevo servicio
    private void agregarServicio() {
        try {
            // Validar que los campos no estén vacíos
            if (vista.getTxtNombre().getText().isEmpty()
                    || vista.getTxtDescripcion().getText().isEmpty()
                    || vista.getTxtPrecio().getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que el precio sea un número válido
            double precio;
            try {
                precio = Double.parseDouble(vista.getTxtPrecio().getText());
                if (precio <= 0) {
                    JOptionPane.showMessageDialog(vista, "El precio debe ser mayor que cero", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista, "El precio debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear el objeto servicio
            ModelServicios nuevoServicio = new ModelServicios();
            nuevoServicio.setNombre(vista.getTxtNombre().getText());
            nuevoServicio.setDescripcion(vista.getTxtDescripcion().getText());
            nuevoServicio.setPrecio(precio);

            // Llamar al servicio para agregar
            boolean resultado = servicio.agregarServicio(nuevoServicio);

            if (resultado) {
                JOptionPane.showMessageDialog(vista, "Servicio agregado correctamente");
                limpiarCampos();
                cargarServiciosEnTabla(); // Recargar la tabla
            } else {
                JOptionPane.showMessageDialog(vista, "Error al agregar el servicio", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para editar un servicio existente
    private void editarServicio() {
        try {
            // Verificar que se haya seleccionado un servicio
            if (vista.getTxtIdServicio().getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Debe seleccionar un servicio para editar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que los campos no estén vacíos
            if (vista.getTxtNombre().getText().isEmpty()
                    || vista.getTxtDescripcion().getText().isEmpty()
                    || vista.getTxtPrecio().getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que el precio sea un número válido
            double precio;
            try {
                precio = Double.parseDouble(vista.getTxtPrecio().getText());
                if (precio <= 0) {
                    JOptionPane.showMessageDialog(vista, "El precio debe ser mayor que cero", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista, "El precio debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear el objeto servicio
            ModelServicios servicioEditado = new ModelServicios();
            servicioEditado.setIdServicio(Integer.parseInt(vista.getTxtIdServicio().getText()));
            servicioEditado.setNombre(vista.getTxtNombre().getText());
            servicioEditado.setDescripcion(vista.getTxtDescripcion().getText());
            servicioEditado.setPrecio(precio);

            // Llamar al servicio para actualizar
            boolean resultado = servicio.actualizarServicio(servicioEditado);

            if (resultado) {
                JOptionPane.showMessageDialog(vista, "Servicio actualizado correctamente");
                limpiarCampos();
                cargarServiciosEnTabla(); // Recargar la tabla
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar el servicio", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para eliminar un servicio
    private void eliminarServicio() {
        try {
            // Verificar que se haya seleccionado un servicio
            int filaSeleccionada = vista.getTblServicios().getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(vista, "Debe seleccionar un servicio para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener el ID del servicio seleccionado
            int idServicio = (int) vista.getTblServicios().getValueAt(filaSeleccionada, 0);

            // Confirmar la eliminación
            int confirmacion = JOptionPane.showConfirmDialog(vista,
                    "¿Está seguro de eliminar este servicio?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                // Llamar al servicio para eliminar
                boolean resultado = servicio.eliminarServicio(idServicio);

                if (resultado) {
                    JOptionPane.showMessageDialog(vista, "Servicio eliminado correctamente");
                    limpiarCampos();
                    cargarServiciosEnTabla(); // Recargar la tabla
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al eliminar el servicio", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para seleccionar un servicio de la tabla y cargar sus datos en los campos
    private void seleccionarServicio() {
        int filaSeleccionada = vista.getTblServicios().getSelectedRow();
        if (filaSeleccionada != -1) {
            // Obtener los datos de la fila seleccionada
            int idServicio = (int) vista.getTblServicios().getValueAt(filaSeleccionada, 0);
            String nombre = (String) vista.getTblServicios().getValueAt(filaSeleccionada, 1);
            String descripcion = (String) vista.getTblServicios().getValueAt(filaSeleccionada, 2);
            double precio = (double) vista.getTblServicios().getValueAt(filaSeleccionada, 3);

            // Cargar los datos en los campos
            vista.getTxtIdServicio().setText(String.valueOf(idServicio));
            vista.getTxtNombre().setText(nombre);
            vista.getTxtDescripcion().setText(descripcion);
            vista.getTxtPrecio().setText(String.valueOf(precio));
        }
    }

    // Método para limpiar los campos del formulario
    private void limpiarCampos() {
        vista.getTxtIdServicio().setText("");
        vista.getTxtNombre().setText("");
        vista.getTxtDescripcion().setText("");
        vista.getTxtPrecio().setText("");
        vista.getTxtNombre().requestFocus(); // Poner el foco en el campo nombre
    }
}
