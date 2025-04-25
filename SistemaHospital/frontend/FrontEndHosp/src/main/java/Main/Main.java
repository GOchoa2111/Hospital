
package Main;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javax.management.remote.JMXConnectorFactory;
import views.Paciente;

/**
 *
 * @author Carlos Orozco
 */
public class Main {
    
    public static void main(String[] args) {
        
        try{
        //solicitar petición 
        
        URL url = new URL("http://localhost:5132/api/Pacientes");
        
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("GET");
        conn.connect();
        
        //validar petición
        int responseCode = conn.getResponseCode();
        
            if (responseCode != 200) {
            
                throw new RuntimeException("Ocurrio un error" + responseCode);
                
            } else {
                    
        //abrir un escaner que lea el flujo de datos
                    StringBuilder infoStringBuilder = new StringBuilder();
                    
                    Scanner scanner = new Scanner(url.openStream());
                    
                    while (scanner.hasNext()){
                    
                        infoStringBuilder.append(scanner.nextLine());
                    
                    }
                    
                    //mostrar la información contenida
                    
                    System.out.println(infoStringBuilder);
                    
                    scanner.close();
  
                    }
    
                }catch(Exception e){
        
            e.printStackTrace();
        }
        

       /* System.out.println("Prueba de texto");
        
        Paciente paciente = new Paciente();
        
        paciente.setVisible(true);*/
    }
    
}
