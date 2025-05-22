package views;

import java.awt.Color;
import java.awt.Font;
import javax.security.auth.login.LoginContext;
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

        // Menú Citas
        JMenu menuCitas = new JMenu("Citas");
        JMenuItem menuItemGestionCitas = new JMenuItem("Gestión de Citas");
        menuItemGestionCitas.addActionListener(e -> {
            boolean formularioAbierto = false; //boolean para vericar (true o false)
            //Recorrer y validar si el formulario esta abierto
            for (JInternalFrame frame : desktopPane.getAllFrames()) {
                if (frame instanceof ViewRepoCitas) {
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
                ViewRepoCitas formularioRepoCitas = new ViewRepoCitas();
                desktopPane.add(formularioRepoCitas);
                formularioRepoCitas.setVisible(true);
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

         //Menú Servicios
        JMenu menuServicios = new JMenu("Servicios");
        JMenuItem menuItemGestionServicios = new JMenuItem("Gestión de Servicios");

         menuItemGestionServicios.addActionListener(e -> {
            boolean formularioAbierto = false; //boolean para vericar (true o false)
            //Recorrer y validar si el formulario esta abierto
            for (JInternalFrame frame : desktopPane.getAllFrames()) {
                if (frame instanceof ViewConsulta) {
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
                ViewConsulta formularioServicios = new ViewConsulta();
                desktopPane.add(formularioServicios);
                formularioServicios.setVisible(true);
            }
        });
        menuServicios.add(menuItemGestionServicios); //añadir el boton al panel para abrir el form

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
        lblBienvenida.setFont(new  Font("Arial", Font.BOLD,16));
        desktopPane.add(lblBienvenida);

        //Etiqueta de usuario que se loguea
        JLabel usuarioLogin = new JLabel(usuario.getNombreUsuario().toUpperCase());//llamar la instancia creada para utilizar sus variables
        usuarioLogin.setFont(new Font("Arial", Font.BOLD, 30));
        usuarioLogin.setForeground(Color.BLUE);
        usuarioLogin.setBounds(60, 60, 300, 60);
        desktopPane.add(usuarioLogin);

    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            ViewMenu menu = new ViewMenu(usuario);

        });
    }*/

}
