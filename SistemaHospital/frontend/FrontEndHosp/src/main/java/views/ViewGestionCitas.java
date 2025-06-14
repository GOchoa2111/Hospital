/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */

 package views;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import models.ModelDoctores;
import models.ModelPaciente;
import models.ModelServicios;

public class ViewGestionCitas extends javax.swing.JInternalFrame {

    // --- Constructor ---
    public ViewGestionCitas() {
        initComponents();
        
        setTitle("GESTION DE CITAS MEDICAS");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setSize(950, 650);
        
        // Deshabilitamos los botones que no deben usarse al inicio
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnNotificar.setEnabled(false);
    }
    
    // Estos son los "puentes" que le faltaban a nuestro controlador.
    public JComboBox<ModelPaciente> getCmbPaciente() { return cmbPaciente; }
    public JComboBox<ModelDoctores> getCmbDoctor() { return cmbDoctor; }
    public JComboBox<ModelServicios> getCmbServicio() { return cmbServicio; }
    public JComboBox<String> getCmbEstado() { return cmbEstado; }
    public JDateChooser getJdcFechaCita() { return jdcFechaCita; }
    public JSpinner getSpnHoraCita() { return spnHoraCita; }
    public JTable getTblCitas() { return tblCitas; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JButton getBtnNotificar() { return btnNotificar; }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelFormulario = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbPaciente = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cmbDoctor = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cmbServicio = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        panelFechaHora = new javax.swing.JPanel();
        jdcFechaCita = new com.toedter.calendar.JDateChooser();
        spnHoraCita = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        cmbEstado = new javax.swing.JComboBox<>();
        scrollTabla = new javax.swing.JScrollPane();
        tblCitas = new javax.swing.JTable();
        panelBotones = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnNotificar = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.BorderLayout(10, 10));

        panelFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalles de la Cita"));
        panelFormulario.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Paciente:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelFormulario.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelFormulario.add(cmbPaciente, gridBagConstraints);

        jLabel2.setText("Doctor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelFormulario.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelFormulario.add(cmbDoctor, gridBagConstraints);

        jLabel3.setText("Servicio:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelFormulario.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelFormulario.add(cmbServicio, gridBagConstraints);

        jLabel4.setText("Fecha y Hora:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelFormulario.add(jLabel4, gridBagConstraints);

        panelFechaHora.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        jdcFechaCita.setPreferredSize(new java.awt.Dimension(150, 25));
        panelFechaHora.add(jdcFechaCita);

        spnHoraCita.setModel(new javax.swing.SpinnerDateModel());
        spnHoraCita.setEditor(new javax.swing.JSpinner.DateEditor(spnHoraCita, "HH:mm"));
        spnHoraCita.setPreferredSize(new java.awt.Dimension(100, 25));
        panelFechaHora.add(spnHoraCita);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelFormulario.add(panelFechaHora, gridBagConstraints);

        jLabel5.setText("Estado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelFormulario.add(jLabel5, gridBagConstraints);

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Programada", "Confirmada", "Cancelada", "Atendido" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelFormulario.add(cmbEstado, gridBagConstraints);

        getContentPane().add(panelFormulario, java.awt.BorderLayout.NORTH);

        scrollTabla.setBorder(javax.swing.BorderFactory.createTitledBorder("Citas Programadas"));
        scrollTabla.setViewportView(tblCitas);

        getContentPane().add(scrollTabla, java.awt.BorderLayout.CENTER);

        panelBotones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnGuardar.setText("Guardar Cita");
        panelBotones.add(btnGuardar);

        btnActualizar.setText("Actualizar");
        panelBotones.add(btnActualizar);

        btnEliminar.setText("Eliminar");
        panelBotones.add(btnEliminar);

        btnLimpiar.setText("Limpiar");
        panelBotones.add(btnLimpiar);

        btnNotificar.setText("Enviar Recordatorio");
        panelBotones.add(btnNotificar);

        getContentPane().add(panelBotones, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnNotificar;
    private javax.swing.JComboBox<ModelDoctores> cmbDoctor;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox<ModelPaciente> cmbPaciente;
    private javax.swing.JComboBox<ModelServicios> cmbServicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelFechaHora;
    private javax.swing.JPanel panelFormulario;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JSpinner spnHoraCita;
    private javax.swing.JTable tblCitas;
    private com.toedter.calendar.JDateChooser jdcFechaCita;
    // End of variables declaration//GEN-END:variables
}