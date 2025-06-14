package views;

import controllers.ControllerGestionCitas;
import controllers.ControllerHistorialPaciente;
import controllers.ControllerRepoCitas;
import java.awt.*;
import javax.swing.*;
import models.ModelLogin;
import views.components.PanelInfoInstitucional;
<<<<<<< HEAD
import controllers.ControllerServicios;
import controllers.ControllerUsuario;
=======
>>>>>>> d60e8469d1f4113ebf4daa89c6bf61111779973a

public class ViewMenu extends JFrame {

    private JDesktopPane desktopPane;
    private ModelLogin usuario;

<<<<<<< HEAD
    // Declaración global de menús
    private JMenuBar menuBar;
    private JMenu menuPacientes;
    private JMenu menuCitas;
    private JMenu menuDoctores;
    private JMenu menuServicios;
    private JMenu menuFacturacion;
    private JMenu menuAdministrador;
    private JMenu menuSalir;

    public ViewMenu(ModelLogin usuario, String baseUrl, String token) {
=======
    public ViewMenu(ModelLogin usuario) {
>>>>>>> d60e8469d1f4113ebf4daa89c6bf61111779973a
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

<<<<<<< HEAD
=======
        // Panel con información institucional (usuario, objetivo, misión, visión)
>>>>>>> d60e8469d1f4113ebf4daa89c6bf61111779973a
        PanelInfoInstitucional panelInfo = new PanelInfoInstitucional(usuario.getNombreUsuario());
        panelInfo.setBounds(20, 20, 1220, 620);
        desktopPane.add(panelInfo);

        // Inicialización del menú
        menuBar = new JMenuBar();

        // ======== MENÚ PACIENTES ========
        menuPacientes = new JMenu("Pacientes");
        JMenuItem itemPacientes = new JMenuItem("Gestión de Pacientes");
        itemPacientes.addActionListener(e -> abrirFormulario(ViewPacientes.class, new ViewPacientes(usuario)));
        menuPacientes.add(itemPacientes);

        // ======== MENÚ CITAS ========
        menuCitas = new JMenu("Citas");
        JMenuItem itemCitas = new JMenuItem("Gestión de Citas");
        itemCitas.addActionListener(e -> {
        if (!estaFormularioAbierto(ViewGestionCitas.class)) {
        ViewGestionCitas vistaCitas = new ViewGestionCitas();
        new ControllerGestionCitas(vistaCitas, usuario); 
        agregarFormulario(vistaCitas);
            }
        });
        menuCitas.add(itemCitas);

        // ======== MENÚ DOCTORES ========
        menuDoctores = new JMenu("Doctores");
        JMenuItem itemDoctores = new JMenuItem("Gestión de Doctores");
        itemDoctores.addActionListener(e -> abrirFormulario(ViewDoctores.class, new ViewDoctores(usuario)));
        menuDoctores.add(itemDoctores);

        // ======== MENÚ SERVICIOS ========
        menuServicios = new JMenu("Servicios");
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

<<<<<<< HEAD
        // ======== MENÚ FACTURACIÓN ========
        menuFacturacion = new JMenu("Facturación");
=======
        // === NUEVO MENÚ: Facturación ===
        JMenu menuFacturacion = new JMenu("Facturación");
>>>>>>> d60e8469d1f4113ebf4daa89c6bf61111779973a
        JMenuItem itemGenerarFactura = new JMenuItem("Generar Factura");
        itemGenerarFactura.addActionListener(e -> {
            if (!estaFormularioAbierto(ViewFactura.class)) {
                ViewFactura viewFactura = new ViewFactura(usuario); // <- Aquí se pasa el usuario logueado
                agregarFormulario(viewFactura);
            }
        });

<<<<<<< HEAD
        JMenuItem itemGestionFacturas = new JMenuItem("Gestión de Facturas");
        itemGestionFacturas.addActionListener(e -> {
            if (!estaFormularioAbierto(ViewFacturaGestion.class)) {
                ViewFacturaGestion viewGestion = new ViewFacturaGestion(baseUrl, token, usuario.getIdUsuario());
                agregarFormulario(viewGestion);
            }
        });

        menuFacturacion.add(itemGenerarFactura);
        menuFacturacion.add(itemGestionFacturas);

        // ======== MENÚ ADMINISTRADOR ========
        menuAdministrador = new JMenu("Administrador");
        JMenuItem itemUsuarios = new JMenuItem("Gestión de Usuarios");
        itemUsuarios.addActionListener(e -> {
            if (!estaFormularioAbierto(ViewUsuarios.class)) {
                ViewUsuarios viewUsuarios = new ViewUsuarios();
                new ControllerUsuario(viewUsuarios);
                agregarFormulario(viewUsuarios);
            }
        });
=======
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
>>>>>>> d60e8469d1f4113ebf4daa89c6bf61111779973a

        JMenuItem itemRoles = new JMenuItem("Gestión de Roles");
        itemRoles.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abrir módulo Roles (próximamente)");
        });

        menuAdministrador.add(itemUsuarios);
        menuAdministrador.add(itemRoles);

        // ======== MENÚ SALIR ========
        menuSalir = new JMenu("Salir");
        JMenuItem itemSalir = new JMenuItem("Cerrar sesión");
        itemSalir.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(this, "¿Seguro que desea salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        menuSalir.add(itemSalir);

<<<<<<< HEAD
        // ======== AGREGAR MENÚS AL MENUBAR ========
=======
        // Agregar todos los menús a la barra
>>>>>>> d60e8469d1f4113ebf4daa89c6bf61111779973a
        menuBar.add(menuPacientes);
        menuBar.add(menuCitas);
        menuBar.add(menuDoctores);
        menuBar.add(menuServicios);
<<<<<<< HEAD
        menuBar.add(menuFacturacion);
        menuBar.add(menuAdministrador);
        menuBar.add(menuSalir);

        // Aplicar permisos antes de establecer el menú
        configurarPermisos(usuario.getRole());
        setJMenuBar(menuBar);
    }

    private void configurarPermisos(String rol) {
        rol = rol.toLowerCase();

        switch (rol) {
            case "administrador":
                // Acceso completo
                break;

            case "gestor":
                // Todo menos administrador
                menuAdministrador.setEnabled(false);
                break;

            case "medico":
                // Solo historial médico (a futuro)
                menuPacientes.setEnabled(false);
                menuCitas.setEnabled(false);
                menuDoctores.setEnabled(false);
                menuServicios.setEnabled(false);
                menuFacturacion.setEnabled(false);
                menuAdministrador.setEnabled(false);
                break;

            case "recepcionista":
                // Solo facturación
                menuPacientes.setEnabled(true);
                menuCitas.setEnabled(true);
                menuDoctores.setEnabled(false);
                menuServicios.setEnabled(false);
                menuAdministrador.setEnabled(false);
                break;

            default:
                JOptionPane.showMessageDialog(this, "Rol no reconocido: " + rol);
                System.exit(1);
        }
    }

=======
        menuBar.add(menuFacturacion); // <- Agregado nuevo menú aquí
        menuBar.add(menuReportes);
        menuBar.add(menuRoles);
        menuBar.add(menuSalir);
        setJMenuBar(menuBar);
    }

    // Verifica si un formulario ya está abierto
>>>>>>> d60e8469d1f4113ebf4daa89c6bf61111779973a
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
