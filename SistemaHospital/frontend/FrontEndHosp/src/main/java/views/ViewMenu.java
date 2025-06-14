package views;

import controllers.ControllerRepoCitas;
import java.awt.*;
import javax.swing.*;
import models.ModelLogin;
import views.components.PanelInfoInstitucional;
import controllers.ControllerServicios;
import controllers.ControllerUsuario;

public class ViewMenu extends JFrame {

    private JDesktopPane desktopPane;
    private ModelLogin usuario;
    private String baseUrl;
    private String token;

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
            if (!estaFormularioAbierto(ViewRepoCitas.class)) {
                ViewRepoCitas view = new ViewRepoCitas();
                ControllerRepoCitas controlador = new ControllerRepoCitas(view);
                controlador.cargarCitasEnTabla();
                agregarFormulario(view);
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
            if (!estaFormularioAbierto(ViewServicios.class)) {
                try {
                    ViewServicios view = new ViewServicios();
                    ControllerServicios controlador = new ControllerServicios(view);
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
                ViewFactura viewFactura = new ViewFactura(usuario);
                agregarFormulario(viewFactura);
            }
        });

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

        // ======== AGREGAR MENÚS AL MENUBAR ========
        menuBar.add(menuPacientes);
        menuBar.add(menuCitas);
        menuBar.add(menuDoctores);
        menuBar.add(menuServicios);
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
                menuPacientes.setEnabled(false);
                menuCitas.setEnabled(false);
                menuDoctores.setEnabled(false);
                menuServicios.setEnabled(false);
                menuAdministrador.setEnabled(false);
                break;

            default:
                JOptionPane.showMessageDialog(this, "Rol no reconocido: " + rol);
                System.exit(1);
        }
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
            formulario.setMaximum(true);
        } catch (java.beans.PropertyVetoException ex) {
            ex.printStackTrace();
        }
    }
}
