/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

/**
 *
 * @author Carlos Orozco
 */


import javax.swing.*;
import java.awt.event.*;

public class ViewMenu extends JFrame {

    public ViewMenu() {
        initComponents();
        setTitle("Sistema Hospitalario - Menú Principal");
        setSize(1280, 720);
        setLocationRelativeTo(null); // Centrar ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        // Barra de menú
        JMenuBar menuBar = new JMenuBar();

        // Menú Pacientes
        JMenu menuPacientes = new JMenu("Pacientes");
        JMenuItem menuItemGestionPacientes = new JMenuItem("Gestión de Pacientes");
        menuItemGestionPacientes.addActionListener(e -> JOptionPane.showMessageDialog(this, "Abrir módulo Pacientes"));
        menuPacientes.add(menuItemGestionPacientes);

        // Menú Citas
        JMenu menuCitas = new JMenu("Citas");
        JMenuItem menuItemGestionCitas = new JMenuItem("Gestión de Citas");
        menuItemGestionCitas.addActionListener(e -> JOptionPane.showMessageDialog(this, "Abrir módulo Citas"));
        menuCitas.add(menuItemGestionCitas);

        // Menú Doctores
        JMenu menuDoctores = new JMenu("Doctores");
        JMenuItem menuItemGestionDoctores = new JMenuItem("Gestión de Doctores");
        menuItemGestionDoctores.addActionListener(e -> JOptionPane.showMessageDialog(this, "Abrir módulo Doctores"));
        menuDoctores.add(menuItemGestionDoctores);

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

        // Agregar menús a la barra
        menuBar.add(menuPacientes);
        menuBar.add(menuCitas);
        menuBar.add(menuDoctores);
        menuBar.add(menuServicios);
        menuBar.add(menuReportes);
        menuBar.add(menuSalir);

        setJMenuBar(menuBar);

        // Puedes agregar un panel central si quieres (opcional)
        JPanel panel = new JPanel();
        JLabel lblBienvenida = new JLabel("Bienvenido al Sistema Hospitalario");
        panel.add(lblBienvenida);
        add(panel);
    }

    // Método main para probar rápido el menú
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ViewMenu().setVisible(true);
        });
    }
}

