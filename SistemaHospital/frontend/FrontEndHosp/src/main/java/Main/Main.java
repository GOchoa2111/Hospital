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
        
           //Llamar form Loadig
        
        Loading cargando = new Loading();
        cargando.setVisible(true);
        cargando.setLocationRelativeTo(null);
        cargando.dispose();//cerrar el form al finalizar la carga
        
        try {  //cargar tema para los formularios
            UIManager.setLookAndFeel(new FlatIntelliJLaf());  
        } catch (UnsupportedLookAndFeelException ex) {  
            ex.printStackTrace();  
        }

        // Crear instancia para cargar validación del login
        ViewLogin view = new ViewLogin();
        ModelLogin model = new ModelLogin();
        ServiceLogin service = new ServiceLogin("http://localhost:5132/api");

        // Crear el controlador y pasarle las instancias
        ControllerLogin controlador = new ControllerLogin(view,model, service);

        // Mostrar la vista
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
}