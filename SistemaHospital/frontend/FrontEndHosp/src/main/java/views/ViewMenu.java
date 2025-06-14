package views;

import controllers.ControllerRepoCitas;
import java.awt.*;
import javax.swing.*;
import models.ModelLogin;
import views.components.PanelInfoInstitucional;
import controllers.ControllerServicios;
import views.*;

public class ViewMenu extends JFrame {

    private JDesktopPane desktopPane;
    private ModelLogin usuario;
    private String baseUrl;
    private String token;

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

        // Panel con información institucional
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
            if (!estaFormularioAbierto(ViewRepoCitas.class)) {
                ViewRepoCitas view = new ViewRepoCitas();
                ControllerRepoCitas controlador = new ControllerRepoCitas(view);
                controlador.cargarCitasEnTabla();
                agregarFormulario(view);
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

        // === Facturación ===
        JMenu menuFacturacion = new JMenu("Facturación");

        // Opción 1: Generar Factura
        JMenuItem itemGenerarFactura = new JMenuItem("Generar Factura");
        itemGenerarFactura.addActionListener(e -> {
            if (!estaFormularioAbierto(ViewFactura.class)) {
                ViewFactura viewFactura = new ViewFactura(usuario);
                agregarFormulario(viewFactura);
            }
        });
        menuFacturacion.add(itemGenerarFactura);

        // Opción 2: Gestión de Facturas
        JMenuItem itemGestionFacturas = new JMenuItem("Gestión de Facturas");
        itemGestionFacturas.addActionListener(e -> {
            if (!estaFormularioAbierto(ViewFacturaGestion.class)) {
                ViewFacturaGestion viewGestion = new ViewFacturaGestion(baseUrl, token, usuario.getIdUsuario());
                agregarFormulario(viewGestion);
            }
        });
        menuFacturacion.add(itemGestionFacturas);

        // Reportes
        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem itemReportes = new JMenuItem("Generar Reportes");
        itemReportes.addActionListener(e -> JOptionPane.showMessageDialog(this, "Abrir módulo Reportes"));
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

        // Agregar todos los menús
        menuBar.add(menuPacientes);
        menuBar.add(menuCitas);
        menuBar.add(menuDoctores);
        menuBar.add(menuServicios);
        menuBar.add(menuFacturacion);
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
            formulario.setMaximum(true);
        } catch (java.beans.PropertyVetoException ex) {
            ex.printStackTrace();
        }
    }
}
