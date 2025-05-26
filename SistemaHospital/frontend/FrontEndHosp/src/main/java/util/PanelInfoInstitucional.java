package views.components;

import javax.swing.*;
import java.awt.*;

public class PanelInfoInstitucional extends JPanel {

    public PanelInfoInstitucional(String nombreUsuario) {
        setLayout(new BorderLayout(20, 20));
        setOpaque(false);

        // Cabecera
        JPanel panelCabecera = new JPanel();
        panelCabecera.setLayout(new BoxLayout(panelCabecera, BoxLayout.Y_AXIS));
        panelCabecera.setOpaque(false);

        JLabel lblBienvenida = new JLabel("Bienvenido al Sistema Hospitalario");
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 22));
        lblBienvenida.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblUsuario = new JLabel(nombreUsuario.toUpperCase());
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 28));
        lblUsuario.setForeground(new Color(0, 102, 204));
        lblUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelCabecera.add(lblBienvenida);
        panelCabecera.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCabecera.add(lblUsuario);

        add(panelCabecera, BorderLayout.NORTH);

        // Centro: Objetivo, Misión y Visión
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setOpaque(false);

        JLabel objetivoLabel = new JLabel("<html>"
                + "<h2 style='text-align:center;'>Objetivo</h2>"
                + "<p style='text-align: justify;'>Hospital La Salud tiene como objetivo el desarrollo de un centro de atención médica integral "
                + "que brinde servicios de salud de alta calidad, accesibles y humanizados a la comunidad. Diseñado para responder a las necesidades "
                + "actuales del sistema sanitario, el hospital combinará tecnología de vanguardia, un equipo médico altamente calificado y una "
                + "infraestructura moderna orientada al bienestar del paciente.</p></html>");
        objetivoLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel misionLabel = new JLabel("<html>"
                + "<h2 style='text-align:center;'>Misión</h2>"
                + "<p style='text-align: justify;'>Brindar atención médica integral, humana y de calidad, basada en la excelencia profesional, "
                + "la innovación tecnológica y el compromiso con el bienestar de nuestros pacientes, promoviendo una cultura de prevención, "
                + "respeto y mejora continua en todos nuestros servicios de salud.</p></html>");
        misionLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel visionLabel = new JLabel("<html>"
                + "<h2 style='text-align:center;'>Visión</h2>"
                + "<p style='text-align: justify;'>Ser un hospital de referencia a nivel regional y nacional, reconocido por su liderazgo "
                + "en atención sanitaria, su modelo centrado en el paciente y su contribución al desarrollo de la medicina a través de la "
                + "investigación, la formación y la implementación de soluciones innovadoras y sostenibles.</p></html>");
        visionLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        panelCentro.add(objetivoLabel);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 15)));
        panelCentro.add(misionLabel);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 15)));
        panelCentro.add(visionLabel);

        add(panelCentro, BorderLayout.CENTER);

        // Pie de página
        JLabel piePagina = new JLabel("<html><center>Hospital Salud - Cuidando tu salud con compromiso y excelencia.<br>"
                + "Dirección: Calle Principal #123, Ciudad &nbsp;&nbsp;&nbsp; Teléfono: (502) 1234-5678</center></html>");
        piePagina.setFont(new Font("Arial", Font.ITALIC, 14));
        piePagina.setForeground(Color.DARK_GRAY);
        piePagina.setHorizontalAlignment(SwingConstants.CENTER);

        add(piePagina, BorderLayout.SOUTH);
    }
}
