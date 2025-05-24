package Main;

/**
 *
 * @author Carlos Orozco
 */
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import controllers.ControllerLogin;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import services.ServiceLogin;
import views.ViewLogin;
import models.ModelLogin;

public class Main {

    public static void main(String[] args) {
        
        try {  
            UIManager.setLookAndFeel(new FlatIntelliJLaf());  
        } catch (UnsupportedLookAndFeelException ex) {  
            ex.printStackTrace();  
        }

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
