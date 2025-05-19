package views;

import javax.swing.*;
import java.awt.*;

public class ViewDoctores extends JInternalFrame {

    private JTextField txtNombre, txtApellido, txtEspecialidad, txtTelefono, txtCorreo;
    private JCheckBox chkEstado;
    private JTable tablaDoctores;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;

    public ViewDoctores() {
        setTitle("Gestión de Doctores");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setSize(800, 600);
        initComponents();
    }

    private void initComponents() {
        
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Doctor"));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panelFormulario.add(txtApellido);

        panelFormulario.add(new JLabel("Especialidad:"));
        txtEspecialidad = new JTextField();
        panelFormulario.add(txtEspecialidad);

        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);

        panelFormulario.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panelFormulario.add(txtCorreo);

        panelFormulario.add(new JLabel("Activo:"));
        chkEstado = new JCheckBox("Sí");
        chkEstado.setSelected(true);
        panelFormulario.add(chkEstado);

        
         // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        Dimension btnSize = new Dimension(110, 25);
        
        btnGuardar = new JButton("Guardar");
        btnGuardar.setPreferredSize(btnSize);
        
        btnActualizar = new JButton("Actualizar");
        btnActualizar.setPreferredSize(btnSize);
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setPreferredSize(btnSize);
        
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setPreferredSize(btnSize);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Tabla
        tablaDoctores = new JTable(); // Se configurará luego con un modelo de tabla
        JScrollPane scrollTabla = new JScrollPane(tablaDoctores);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Listado de Doctores"));

        // Layout principal
        setLayout(new BorderLayout(10, 10));
        add(panelFormulario, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Método main para prueba independiente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Prueba Doctores");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 700);
            JDesktopPane desktop = new JDesktopPane();
            frame.add(desktop);

            ViewDoctores form = new ViewDoctores();
            desktop.add(form);
            form.setVisible(true);

            frame.setVisible(true);
        });
    }
}
