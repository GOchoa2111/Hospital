import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;

public class TestViewServicios {
    public static void main(String[] args) {
        try {
            // Establecer el look and feel FlatDarkLaf
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        // Ejecutar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("Creando JFrame principal...");
                JFrame frame = new JFrame("Prueba ViewServicios");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setLocationRelativeTo(null);

                System.out.println("Creando JDesktopPane...");
                JDesktopPane desktopPane = new JDesktopPane();
                frame.setContentPane(desktopPane);

                System.out.println("Creando ViewServicios...");
                views.ViewServicios vista = new views.ViewServicios();

                System.out.println("Agregando ViewServicios al JDesktopPane...");
                desktopPane.add(vista);

                System.out.println("Creando ControllerServicios...");
                controllers.ControllerServicios controlador = new controllers.ControllerServicios(vista);

                System.out.println("Haciendo visible ViewServicios...");
                vista.setVisible(true);

                System.out.println("Haciendo visible JFrame...");
                frame.setVisible(true);

                System.out.println("Cargando servicios en la tabla...");
                controlador.obtenerServicios();
                System.out.println("Servicios cargados en la tabla.");

            } catch (Exception e) {
                System.err.println("Error durante la inicialización: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}