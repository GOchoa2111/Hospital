package Main;

/**
 *
 * @author Carlos Orozco
 */
import com.formdev.flatlaf.FlatIntelliJLaf;
import controllers.ControllerLogin;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import services.ServiceLogin;
import views.ViewLogin;
import models.ModelLogin;
import util.Loading;

public class Main {

    public static void main(String[] args) {

        // Mostrar pantalla de carga
        Loading cargando = new Loading();
        cargando.setVisible(true);
        cargando.setLocationRelativeTo(null);
        cargando.dispose(); // cerrar el form al finalizar la carga

        try {
            // Cargar tema FlatLaf para los formularios
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        // URL base para el servicio (puedes moverla a un archivo .properties si deseas después)
        String baseUrl = "http://localhost:5132";

        // Crear instancias
        ViewLogin view = new ViewLogin();
        ModelLogin model = new ModelLogin();
        ServiceLogin service = new ServiceLogin(baseUrl); //

        // Crear el controlador y pasarle las instancias
        ControllerLogin controlador = new ControllerLogin(view, model, service);

        // Mostrar la vista
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
}
