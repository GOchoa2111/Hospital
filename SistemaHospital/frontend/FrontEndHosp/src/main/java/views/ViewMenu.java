package views;

import controllers.ControllerRepoCitas;
import java.awt.*;
import javax.swing.*;
import models.ModelLogin;
import views.components.PanelInfoInstitucional;

public class ViewMenu extends JFrame {

    private JDesktopPane desktopPane;
    private ModelLogin usuario;

    public ViewMenu(ModelLogin usuario) {
        this.usuario = usuario;
        initComponents();
        setTitle("Sistema Hospitalario - Menú Principal");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        // Panel con información institucional (usuario, objetivo, misión, visión)
        PanelInfoInstitucional panelInfo = new PanelInfoInstitucional(usuario.getNombreUsuario());//se pasa el parametro de usuario
        panelInfo.setBounds(20, 20, 1220, 620);
        desktopPane.add(panelInfo);

        // Menú
        JMenuBar menuBar = new JMenuBar();

        JMenu menuPacientes = new JMenu("Pacientes");
        JMenuItem itemPacientes = new JMenuItem("Gestión de Pacientes");
        itemPacientes.addActionListener(e -> abrirFormulario(ViewPacientes.class, new ViewPacientes()));
        menuPacientes.add(itemPacientes);

        JMenu menuCitas = new JMenu("Citas");
        JMenuItem itemCitas = new JMenuItem("Gestión de Citas");
        itemCitas.addActionListener(e -> {
            if (!estaFormularioAbierto(ViewRepoCitas.class)) {
                ViewRepoCitas view = new ViewRepoCitas();
                ControllerRepoCitas controlador = new ControllerRepoCitas(view);
                controlador.cargarCitasEnTabla();
                agregarFormulario(view);
            }
        });
        menuCitas.add(itemCitas);

        JMenu menuDoctores = new JMenu("Doctores");
        JMenuItem itemDoctores = new JMenuItem("Gestión de Doctores");
        itemDoctores.addActionListener(e -> abrirFormulario(ViewDoctores.class, new ViewDoctores()));
        menuDoctores.add(itemDoctores);

        JMenu menuServicios = new JMenu("Servicios");
        JMenuItem itemServicios = new JMenuItem("Gestión de Servicios");
        itemServicios.addActionListener(e -> {
            if (!estaFormularioAbierto(views.ViewServicios.class)) {
                try {
                    views.ViewServicios view = new views.ViewServicios();
                    controllers.ControllerServicios controlador = new controllers.ControllerServicios(view);
                    controlador.obtenerServicios();
                    agregarFormulario(view);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        menuServicios.add(itemServicios);

        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem itemReportes = new JMenuItem("Generar Reportes");
        itemReportes.addActionListener(e -> JOptionPane.showMessageDialog(this, "Abrir módulo Reportes"));
        menuReportes.add(itemReportes);

        JMenu menuRoles = new JMenu("Roles");
        JMenuItem itemRoles = new JMenuItem("Gestión de Roles");
        itemRoles.addActionListener(e -> abrirFormulario(ViewRoles.class, new ViewRoles()));
        menuRoles.add(itemRoles);

        JMenu menuSalir = new JMenu("Salir");
        JMenuItem itemSalir = new JMenuItem("Cerrar sesión");
        itemSalir.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(this, "¿Seguro que desea salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        menuSalir.add(itemSalir);

        menuBar.add(menuPacientes);
        menuBar.add(menuCitas);
        menuBar.add(menuDoctores);
        menuBar.add(menuServicios);
        menuBar.add(menuReportes);
        menuBar.add(menuRoles);
        menuBar.add(menuSalir);
        setJMenuBar(menuBar);
    }

    private boolean estaFormularioAbierto(Class<?> claseFormulario) {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (claseFormulario.isInstance(frame)) {
                try {
                    frame.setSelected(true);
                    frame.toFront();
                } catch (java.beans.PropertyVetoException ex) {
                    ex.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }

    private void abrirFormulario(Class<?> clase, JInternalFrame formulario) {
        if (!estaFormularioAbierto(clase)) {
            agregarFormulario(formulario);
        }
    }

    private void agregarFormulario(JInternalFrame formulario) {
        desktopPane.add(formulario);
        formulario.setVisible(true);
        try {
            formulario.setSelected(true);
            formulario.toFront();
        } catch (java.beans.PropertyVetoException ex) {
            ex.printStackTrace();
        }
    }
}
