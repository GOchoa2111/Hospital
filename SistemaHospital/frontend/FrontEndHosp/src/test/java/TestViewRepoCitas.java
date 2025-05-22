import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestViewRepoCitas {
    public static void main(String[] args) {
        try {
            // Establecer el look and feel FlatDarkLaf
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(TestViewRepoCitas.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Ejecutar en el hilo de eventos de Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            views.ViewRepoCitas vista = new views.ViewRepoCitas();
            controllers.ControllerRepoCitas controlador = new controllers.ControllerRepoCitas(vista);

            vista.setVisible(true);
            controlador.cargarCitasEnTabla();// Aquí haces que se cargue la info al abrir
        });
    }
}
