import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;

public class TestViewRepoCitas {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            // Crear JFrame principal
            JFrame frame = new JFrame("Aplicación de Citas");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            // Crear JDesktopPane
            JDesktopPane desktopPane = new JDesktopPane();
            frame.setContentPane(desktopPane);

            // Crear instancia de ViewRepoCitas (JInternalFrame)
            views.ViewRepoCitas vista = new views.ViewRepoCitas();

            // Agregar el JInternalFrame al JDesktopPane
            desktopPane.add(vista);

            // Crear controlador y pasar la vista
            controllers.ControllerRepoCitas controlador = new controllers.ControllerRepoCitas(vista);

            // Mostrar el JInternalFrame
            vista.setVisible(true);

            // Mostrar el JFrame principal
            frame.setVisible(true);

            // Cargar datos en la tabla
            controlador.cargarCitasEnTabla();
        });
    }
}