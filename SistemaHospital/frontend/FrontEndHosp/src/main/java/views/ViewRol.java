package views;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class ViewRol extends JFrame {

    private JTextField txtId, txtNombre;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modelo;

    public ViewRol() {
        setTitle("Gestión de Roles");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel del formulario
        JPanel panelForm = new JPanel(new GridLayout(2, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelForm.add(new JLabel("ID Rol:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panelForm.add(txtId);
        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);
        add(panelForm, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        Dimension btnSize = new Dimension(100, 30); // Tamaño reducido

        btnGuardar.setPreferredSize(btnSize);
        btnActualizar.setPreferredSize(btnSize);
        btnEliminar.setPreferredSize(btnSize);
        btnLimpiar.setPreferredSize(btnSize);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Panel central que contiene botones y tabla
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.add(panelBotones, BorderLayout.NORTH);

        // Tabla
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);
        panelCentro.add(scrollPane, BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ViewRol form = new ViewRol();
            form.setVisible(true);
        });
    }

    // Getters para el controlador
    public JTextField getTxtId() {
        return txtId;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JTable getTabla() {
        return tabla;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }
}
