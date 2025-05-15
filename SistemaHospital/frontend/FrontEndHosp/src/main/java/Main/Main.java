package Main;

/**
 *
 * @author Carlos Orozco
 */
import controllers.ControllerLogin;
import services.ServiceLogin;
import views.ViewLogin;
import models.ModelLogin;

public class Main {

    public static void main(String[] args) {

        // Crear instancia para cargar los servicios
        ViewLogin view = new ViewLogin();
        ModelLogin model = new ModelLogin();
        ServiceLogin service = new ServiceLogin();

        // Crear el controlador y pasarle las instancias
        ControllerLogin controlador = new ControllerLogin(view,model, service);

        // Mostrar la vista
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
}
