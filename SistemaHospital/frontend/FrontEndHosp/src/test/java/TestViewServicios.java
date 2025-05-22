
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import views.ViewServicios;


public class TestViewServicios {
    
        public static void main(String[] args) {//main para pruebas, deshabilitar al finalizar
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Prueba de ViewConsulta");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);

            JDesktopPane desktopPane = new JDesktopPane();
            ViewServicios viewServicios = new ViewServicios();
            viewServicios.setVisible(true);
            desktopPane.add(viewServicios); // Agregamos el internal frame al desktop pane

            frame.setContentPane(desktopPane);
            frame.setLocationRelativeTo(null); // Centrar la ventana
            frame.setVisible(true);
        });
    }
    
}
