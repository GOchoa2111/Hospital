package views;

import javax.swing.*;

public class ViewMenu extends JFrame {

    private JDesktopPane desktopPane;

    public ViewMenu() {
        initComponents();
        setTitle("Sistema Hospitalario - Menú Principal" );
        setSize(1280, 720);
        setLocationRelativeTo(null); // Centrar ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        // Crear DesktopPane como contenedor principal
        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        // Barra de menú
        JMenuBar menuBar = new JMenuBar();

        // Menú Pacientes
        JMenu menuPacientes = new JMenu("Pacientes");
        JMenuItem menuItemGestionPacientes = new JMenuItem("Gestión de Pacientes");

        menuItemGestionPacientes.addActionListener(e -> {
            boolean formularioAbierto = false; //boolean para vericar (true o false)
            //Recorrer y validar si el formulario esta abierto
            for (JInternalFrame frame : desktopPane.getAllFrames()) {
                if (frame instanceof ViewPacientes) {
                    formularioAbierto = true;
                    try {
                        frame.setSelected(true); // Llevar al frente
                    } catch (java.beans.PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
            }

            if (!formularioAbierto) {
                ViewPacientes formularioPacientes = new ViewPacientes();
                desktopPane.add(formularioPacientes);
                formularioPacientes.setVisible(true);
            }
        });

        menuPacientes.add(menuItemGestionPacientes);

        // Menú Citas
        JMenu menuCitas = new JMenu("Citas");
        JMenuItem menuItemGestionCitas = new JMenuItem("Gestión de Citas");
        menuItemGestionCitas.addActionListener(e -> JOptionPane.showMessageDialog(this, "Abrir módulo Citas"));
        menuCitas.add(menuItemGestionCitas);

        // Menú Doctores
        JMenu menuDoctores = new JMenu("Doctores");
        JMenuItem menuItemGestionDoctores = new JMenuItem("Gestión de Doctores");
        
        menuItemGestionDoctores.addActionListener(e -> {
            boolean formularioAbierto = false; //boolean para vericar (true o false)
            //Recorrer y validar si el formulario esta abierto
            for (JInternalFrame frame : desktopPane.getAllFrames()) {
                if (frame instanceof ViewDoctores) {
                    formularioAbierto = true;
                    try {
                        frame.setSelected(true); // Llevar al frente
                    } catch (java.beans.PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
            }

            if (!formularioAbierto) {
                ViewDoctores formularioDoctores = new ViewDoctores();
                desktopPane.add(formularioDoctores);
                formularioDoctores.setVisible(true);
            }
        });
        menuDoctores.add(menuItemGestionDoctores); //añadir el boton al panel para abrir el form
        
        

        // Menú Servicios
        JMenu menuServicios = new JMenu("Servicios");
        JMenuItem menuItemGestionServicios = new JMenuItem("Gestión de Servicios");
        
        
        menuItemGestionServicios.addActionListener(e -> JOptionPane.showMessageDialog(this, "Abrir módulo Servicios"));
        menuServicios.add(menuItemGestionServicios);

        // Menú Reportes
        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem menuItemReportes = new JMenuItem("Generar Reportes");
        menuItemReportes.addActionListener(e -> JOptionPane.showMessageDialog(this, "Abrir módulo Reportes"));
        menuReportes.add(menuItemReportes);

        // Menú Salir
        JMenu menuSalir = new JMenu("Salir");
        JMenuItem menuItemSalir = new JMenuItem("Cerrar sesión");
        menuItemSalir.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(this, "¿Seguro que desea salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        menuSalir.add(menuItemSalir);

        // Agregar todos los menús
        menuBar.add(menuPacientes);
        menuBar.add(menuCitas);
        menuBar.add(menuDoctores);
        menuBar.add(menuServicios);
        menuBar.add(menuReportes);
        menuBar.add(menuSalir);

        // Establecer barra de menú
        setJMenuBar(menuBar);

        // Etiqueta de bienvenida (opcional)
        JLabel lblBienvenida = new JLabel("Bienvenido al Sistema Hospitalario");
        lblBienvenida.setBounds(30, 30, 300, 30);
        desktopPane.add(lblBienvenida);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ViewMenu().setVisible(true);
        });
    }
}
