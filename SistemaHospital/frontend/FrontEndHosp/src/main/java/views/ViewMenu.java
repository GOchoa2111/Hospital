package views;

import controllers.ControllerGestionCitas;
import controllers.ControllerHistorialPaciente;
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
        PanelInfoInstitucional panelInfo = new PanelInfoInstitucional(usuario.getNombreUsuario());
        panelInfo.setBounds(20, 20, 1220, 620);
        desktopPane.add(panelInfo);

        // === MENÚ ===
        JMenuBar menuBar = new JMenuBar();

        // Pacientes
        JMenu menuPacientes = new JMenu("Pacientes");
        JMenuItem itemPacientes = new JMenuItem("Gestión de Pacientes");
        itemPacientes.addActionListener(e -> abrirFormulario(ViewPacientes.class, new ViewPacientes(usuario)));
        menuPacientes.add(itemPacientes);

        // Citas
        JMenu menuCitas = new JMenu("Citas");
        JMenuItem itemCitas = new JMenuItem("Gestión de Citas");
        itemCitas.addActionListener(e -> {
        if (!estaFormularioAbierto(ViewGestionCitas.class)) {
        ViewGestionCitas vistaCitas = new ViewGestionCitas();
        new ControllerGestionCitas(vistaCitas, usuario); 
        agregarFormulario(vistaCitas);
            }
        });
        menuCitas.add(itemCitas);

        // Doctores
        JMenu menuDoctores = new JMenu("Doctores");
        JMenuItem itemDoctores = new JMenuItem("Gestión de Doctores");
        itemDoctores.addActionListener(e -> abrirFormulario(ViewDoctores.class, new ViewDoctores(usuario)));
        menuDoctores.add(itemDoctores);

        // Servicios
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

        // === NUEVO MENÚ: Facturación ===
        JMenu menuFacturacion = new JMenu("Facturación");
        JMenuItem itemGenerarFactura = new JMenuItem("Generar Factura");
        itemGenerarFactura.addActionListener(e -> {
            if (!estaFormularioAbierto(ViewFactura.class)) {
                ViewFactura viewFactura = new ViewFactura(usuario); // <- Aquí se pasa el usuario logueado
                agregarFormulario(viewFactura);
            }
        });
        menuFacturacion.add(itemGenerarFactura);

        // Reportes
    JMenu menuReportes = new JMenu("Reportes");
    JMenuItem itemReportes = new JMenuItem("Historial Clínico de Paciente");

    itemReportes.addActionListener(e -> {
    if (!estaFormularioAbierto(ViewHistorialPaciente.class)) {
        ViewHistorialPaciente vistaReporte = new ViewHistorialPaciente();
        // Creamos su controlador, pasándole la vista y el usuario logueado
        new ControllerHistorialPaciente(vistaReporte, usuario); 
        agregarFormulario(vistaReporte); // Lo añadimos al escritorio principal
    }
});

menuReportes.add(itemReportes);

menuReportes.add(itemReportes);

        // Roles
        JMenu menuRoles = new JMenu("Roles");
        JMenuItem itemRoles = new JMenuItem("Gestión de Roles");
        itemRoles.addActionListener(e -> abrirFormulario(ViewRoles.class, new ViewRoles()));
        menuRoles.add(itemRoles);

        // Salir
        JMenu menuSalir = new JMenu("Salir");
        JMenuItem itemSalir = new JMenuItem("Cerrar sesión");
        itemSalir.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(this, "¿Seguro que desea salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        menuSalir.add(itemSalir);

        // Agregar todos los menús a la barra
        menuBar.add(menuPacientes);
        menuBar.add(menuCitas);
        menuBar.add(menuDoctores);
        menuBar.add(menuServicios);
        menuBar.add(menuFacturacion); // <- Agregado nuevo menú aquí
        menuBar.add(menuReportes);
        menuBar.add(menuRoles);
        menuBar.add(menuSalir);
        setJMenuBar(menuBar);
    }

    // Verifica si un formulario ya está abierto
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

    // Método genérico para abrir formularios únicos
    private void abrirFormulario(Class<?> clase, JInternalFrame formulario) {
        if (!estaFormularioAbierto(clase)) {
            agregarFormulario(formulario);
        }
    }

    // Agrega el formulario al escritorio
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
