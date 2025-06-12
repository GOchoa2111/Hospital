/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */




import javax.swing.*;

public class TestViewFacturaGestion {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Prueba de ViewFacturaGestion");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.setLocationRelativeTo(null);

            JDesktopPane desktopPane = new JDesktopPane();
            frame.setContentPane(desktopPane);

            String baseUrl = "http://localhost:5132/api";
            String token = "tu_token_aqui";
            int usuarioId = 1;

            ViewFacturaGestion viewFacturaGestion = new ViewFacturaGestion(baseUrl, token, usuarioId);
            viewFacturaGestion.setVisible(true);
            desktopPane.add(viewFacturaGestion);

            frame.setVisible(true);
        });
    }
}


