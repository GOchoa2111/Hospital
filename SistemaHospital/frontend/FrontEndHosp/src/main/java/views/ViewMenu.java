package views;

import controllers.ControllerGestionCitas;
import controllers.ControllerHistorialPaciente;
import controllers.ControllerRepoCitas;
import java.awt.*;
import javax.swing.*;
import models.ModelLogin;
import views.components.PanelInfoInstitucional;
import controllers.ControllerServicios;
import controllers.ControllerUsuario; // Se asume que esta clase existe para Gestión de Usuarios

public class ViewMenu extends JFrame {

    private JDesktopPane desktopPane;
    private ModelLogin usuario;

    // Declaración global de menús (para configurar permisos)
    private JMenuBar menuBar;
    private JMenu menuPacientes;
    private JMenu menuCitas;
    private JMenu menuDoctores;
    private JMenu menuServicios;
    private JMenu menuFacturacion;
    private JMenu menuAdministrador; // Nuevo menú para usuarios y roles
    private JMenu menuReportes;     // Menú de reportes
    private JMenu menuRoles;        // Menú de roles (aunque Gestión de Roles ahora va en Admin)
    private JMenu menuSalir;

    // Constructor actualizado para recibir baseUrl y token si son necesarios para ViewFacturaGestion
    private String baseUrl; // Necesario para ViewFacturaGestion
    private String token; // Necesario para ViewFacturaGestion

    public ViewMenu(ModelLogin usuario, String baseUrl, String token) {
        this.usuario = usuario;
        this.baseUrl = baseUrl;
        this.token = token;
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

        // ======== MENÚ FACTURACIÓN ========
        menuFacturacion = new JMenu("Facturación");
        JMenuItem itemGenerarFactura = new JMenuItem("Generar Factura");
        itemGenerarFactura.addActionListener(e -> {
            if (!estaFormularioAbierto(ViewFactura.class)) {
                ViewFactura viewFactura = new ViewFactura(usuario); // <- Aquí se pasa el usuario logueado
                agregarFormulario(viewFactura);
            }
        });
        menuFacturacion.add(itemGenerarFactura);

        // Ítem de Gestión de Facturas (añadido en una de las ramas)
        JMenuItem itemGestionFacturas = new JMenuItem("Gestión de Facturas");
        itemGestionFacturas.addActionListener(e -> {
            if (!estaFormularioAbierto(ViewFacturaGestion.class)) {
                // Aquí se usan baseUrl y token que deben venir del constructor de ViewMenu
                ViewFacturaGestion viewGestion = new ViewFacturaGestion(baseUrl, token, usuario.getIdUsuario());
                agregarFormulario(viewGestion);
            }
        });
        menuFacturacion.add(itemGestionFacturas);

        // ======== MENÚ REPORTES ======== (Originalmente fuera de conflictos, pero reordenado)
        menuReportes = new JMenu("Reportes");
        JMenuItem itemReportesHistorial = new JMenuItem("Historial Clínico de Paciente");

        itemReportesHistorial.addActionListener(e -> {
            if (!estaFormularioAbierto(ViewHistorialPaciente.class)) {
                ViewHistorialPaciente vistaReporte = new ViewHistorialPaciente();
                // Creamos su controlador, pasándole la vista y el usuario logueado
                new ControllerHistorialPaciente(vistaReporte, usuario); 
                agregarFormulario(vistaReporte); // Lo añadimos al escritorio principal
            }
        });
        menuReportes.add(itemReportesHistorial);
        // Quita la línea duplicada: menuReportes.add(itemReportes);

        // ======== MENÚ ADMINISTRADOR ======== (Gestión de Usuarios y Roles)
        menuAdministrador = new JMenu("Administrador");
        JMenuItem itemUsuarios = new JMenuItem("Gestión de Usuarios");
        itemUsuarios.addActionListener(e -> {
            if (!estaFormularioAbierto(ViewUsuarios.class)) {
                ViewUsuarios viewUsuarios = new ViewUsuarios();
                // Asumiendo que ControllerUsuario tiene un constructor adecuado
                new ControllerUsuario(viewUsuarios); 
                agregarFormulario(viewUsuarios);
            }
        });
        menuAdministrador.add(itemUsuarios);

        // Ítem de Gestión de Roles (se mueve bajo Administrador)
        menuRoles = new JMenu("Roles"); // Declara globalmente si se necesita habilitar/deshabilitar
        JMenuItem itemRoles = new JMenuItem("Gestión de Roles");
        itemRoles.addActionListener(e -> {
            // Reemplaza el JOptionPane con la apertura del formulario de roles si existe
            abrirFormulario(ViewRoles.class, new ViewRoles()); 
        });
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

        // ======== AGREGAR MENÚS AL MENUBAR ========
        menuBar.add(menuPacientes);
        menuBar.add(menuCitas);
        menuBar.add(menuDoctores);
        menuBar.add(menuServicios);
        menuBar.add(menuFacturacion);
        //menuBar.add(menuReportes); // Asegura que Reportes se añade al menú principal
        //menuBar.add(menuAdministrador); // Añade el menú Administrador
        menuBar.add(menuSalir);

        // Aplicar permisos antes de establecer el menú
        configurarPermisos(usuario.getRole());
        setJMenuBar(menuBar);
    }

    // Nuevo método para configurar permisos basado en el rol del usuario
    private void configurarPermisos(String rol) {
        rol = rol.toLowerCase();

        // Por defecto, todos los menús están habilitados
        menuPacientes.setEnabled(true);
        menuCitas.setEnabled(true);
        menuDoctores.setEnabled(true);
        menuServicios.setEnabled(true);
        menuFacturacion.setEnabled(true);
        menuAdministrador.setEnabled(true);
        menuReportes.setEnabled(true); // Asegúrate de que este menú esté habilitado por defecto

        switch (rol) {
            case "administrador":
                // Acceso completo (todos habilitados por defecto)
                break;

            case "gestor":
                // Todo menos administrador
                menuAdministrador.setEnabled(false);
                break;

            case "medico":
                // Solo historial médico (a futuro, si el item lo abre directamente)
                menuPacientes.setEnabled(false);
                menuCitas.setEnabled(false);
                menuDoctores.setEnabled(false);
                menuServicios.setEnabled(false);
                menuFacturacion.setEnabled(false);
                menuAdministrador.setEnabled(false);
                // Si el médico solo ve reportes, entonces menuReportes debe ser true
                // y los demás false, como están aquí.
                // Asegúrate que el itemReportesHistorial no tiene dependencia de otros módulos si están deshabilitados.
                break;

            case "recepcionista":
                // Solo pacientes, citas, facturación y reportes.
                menuDoctores.setEnabled(false);
                menuServicios.setEnabled(false);
                menuAdministrador.setEnabled(false);
                break;

            default:
                JOptionPane.showMessageDialog(this, "Rol no reconocido: " + rol + ". El sistema se cerrará.");
                System.exit(1);
        }
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