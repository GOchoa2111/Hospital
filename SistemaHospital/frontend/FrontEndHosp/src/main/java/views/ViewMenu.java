package views;

import controllers.ControllerRepoCitas;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import models.ModelLogin;//proceso para mostrar usuario logueado importar en este caso
// importar la clase donde se obtiene el dato a mostrar

public class ViewMenu extends JFrame {

    private JDesktopPane desktopPane;
    private ModelLogin usuario; //se declara el atributo a utilizar de la clase que se importa

    public ViewMenu(ModelLogin usuario) {//pasar el parametro al formulario
        this.usuario = usuario;
        initComponents();
        setTitle("Sistema Hospitalario - Menú Principal");
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

        // Menú RepoCitas
        JMenu menuCitas = new JMenu("Citas");
        JMenuItem menuItemGestionCitas = new JMenuItem("Gestión de Citas");

        menuItemGestionCitas.addActionListener(e -> {
            boolean formularioAbierto = false;

            // Recorrer los frames abiertos para verificar si el formulario ya está abierto
            for (JInternalFrame frame : desktopPane.getAllFrames()) {
                if (frame instanceof ViewRepoCitas) {
                    formularioAbierto = true;
                    try {
                        frame.setSelected(true); // Llevar al frente
                        frame.toFront();
                    } catch (java.beans.PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
            }

            if (!formularioAbierto) {
                // Crear instancia del formulario
                ViewRepoCitas formularioRepoCitas = new ViewRepoCitas();

                // Agregar al desktopPane
                desktopPane.add(formularioRepoCitas);

                // Crear el controlador y pasar la vista
                ControllerRepoCitas controlador = new ControllerRepoCitas(formularioRepoCitas);

                // Mostrar el formulario
                formularioRepoCitas.setVisible(true);

                // Cargar los datos en la tabla
                controlador.cargarCitasEnTabla();
            }
        });

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

        menuItemGestionServicios.addActionListener(e -> {
            boolean formularioAbierto = false;

            // Recorrer los frames abiertos para verificar si el formulario ya está abierto
            for (JInternalFrame frame : desktopPane.getAllFrames()) {
                if (frame instanceof views.ViewServicios) {
                    formularioAbierto = true;
                    try {
                        frame.setSelected(true); // Llevar al frente
                        frame.toFront();
                    } catch (java.beans.PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
            }

            if (!formularioAbierto) {
                try {
                    // Crear instancia del formulario
                    views.ViewServicios formularioServicios = new views.ViewServicios();

                    // Agregar al desktopPane
                    desktopPane.add(formularioServicios);

                    // Crear el controlador y pasar la vista
                    controllers.ControllerServicios controlador = new controllers.ControllerServicios(formularioServicios);

                    // Mostrar el formulario
                    formularioServicios.setVisible(true);

                    // Cargar los datos en la tabla
                    controlador.obtenerServicios();

                    // Seleccionar y llevar al frente el formulario
                    formularioServicios.setSelected(true);
                    formularioServicios.toFront();

                } catch (Exception ex) {
                    System.err.println("Error al abrir el formulario de servicios: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        menuServicios.add(menuItemGestionServicios);

        menuServicios.add(menuItemGestionServicios);

        // Menú Reportes
        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem menuItemReportes = new JMenuItem("Generar Reportes");
        menuItemReportes.addActionListener(e -> JOptionPane.showMessageDialog(this, "Abrir módulo Reportes"));
        menuReportes.add(menuItemReportes);

        //Menú Roles
        JMenu menuRoles = new JMenu("Roles");
        JMenuItem menuItemRoles = new JMenuItem("Gestión de Roles");

        menuItemRoles.addActionListener(e -> {
            boolean formularioAbierto = false; //boolean para vericar (true o false)
            //Recorrer y validar si el formulario esta abierto
            for (JInternalFrame frame : desktopPane.getAllFrames()) {
                if (frame instanceof ViewRoles) {
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
                ViewRoles formularioRoles = new ViewRoles();
                desktopPane.add(formularioRoles);
                formularioRoles.setVisible(true);
            }
        });
        menuRoles.add(menuItemRoles); //añadir el boton al panel para abrir el form

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
        menuBar.add(menuRoles);
        menuBar.add(menuSalir);

        // Establecer barra de menú
        setJMenuBar(menuBar);

        // Etiqueta de bienvenida 
        JLabel lblBienvenida = new JLabel("Bienvenido al Sistema Hospitalario");
        lblBienvenida.setBounds(30, 30, 300, 30);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));
        desktopPane.add(lblBienvenida);

        //Etiqueta de usuario que se loguea
        JLabel usuarioLogin = new JLabel(usuario.getNombreUsuario().toUpperCase());//llamar la instancia creada para utilizar sus variables
        usuarioLogin.setFont(new Font("Arial", Font.BOLD, 30));
        usuarioLogin.setForeground(Color.BLUE);
        usuarioLogin.setBounds(60, 60, 300, 60);
        desktopPane.add(usuarioLogin);

        //Pie de formulario
        JLabel descripcionHospital = new JLabel("<html><center>Hospital General - "
                + "Cuidando tu salud con compromiso y excelencia.<br>Dirección: Calle Principal #123, "
                + "Ciudad<br>Teléfono: (502) 1234-5678</center></html>");
        descripcionHospital.setFont(new Font("Arial", Font.ITALIC, 18));
        descripcionHospital.setForeground(Color.DARK_GRAY);
        descripcionHospital.setBounds(30, 600, 600, 30);
        desktopPane.add(descripcionHospital);

        // Decripción del hospital
        JLabel descripcionLabel = new JLabel("<html><p style='width:400px;'>Hospital La Salud tiene como objetivo el desarrollo "
                + "de un centro de atención médica integral que brinde servicios de salud de alta calidad, accesibles"
                + " y humanizados a la comunidad. Diseñado para responder a las necesidades actuales del sistema sanitario,"
                + " el hospital combinará tecnología de vanguardia, un equipo médico altamente calificado y una infraestructura"
                + " moderna orientada al bienestar del paciente.</p></html>");
        descripcionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descripcionLabel.setForeground(Color.DARK_GRAY);
        descripcionLabel.setBounds(700, 30, 500, 150);
        desktopPane.add(descripcionLabel);

        //misión y visión
        // Misión y Visión con formato HTML
        String textoMisionVision = "<html>"
                + "<div style='width:400px; text-align: justify; margin: 0 auto;'>"
                + "<h2 style='text-align: center;'>Misión</h2>"
                + "<p>Brindar atención médica integral, humana y de calidad, basada en la excelencia profesional, la innovación "
                + "tecnológica y el compromiso con el bienestar de nuestros pacientes, promoviendo una cultura de prevención, "
                + "respeto y mejora continua en todos nuestros servicios de salud.</p>"
                + "<h2 style='text-align: center;'>Visión</h2>"
                + "<p>Ser un hospital de referencia a nivel regional y nacional, reconocido por su liderazgo en atención sanitaria, "
                + "su modelo centrado en el paciente y su contribución al desarrollo de la medicina a través de la investigación,"
                + " la formación y la implementación de soluciones innovadoras y sostenibles.</p>"
                + "</div>"
                + "</html>";

        JLabel labelMisionVision = new JLabel(textoMisionVision);
        labelMisionVision.setFont(new Font("Arial", Font.PLAIN, 14));
        labelMisionVision.setForeground(Color.DARK_GRAY);
        labelMisionVision.setBounds(700, 200, 450, 250); // Ajusta posición y tamaño según tu diseño

        desktopPane.add(labelMisionVision);

    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            ViewMenu menu = new ViewMenu(usuario);

        });
    }*/
}
