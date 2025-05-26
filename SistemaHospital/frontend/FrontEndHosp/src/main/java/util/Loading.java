import javax.swing.*;
import java.awt.*;

public class Loading extends JWindow {

    private JProgressBar progressBar;

    public Loading() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);

        // Logo del hospital
        ImageIcon logo = new ImageIcon(getClass().getResource("/Images/logo.png"));
        JLabel logoLabel = new JLabel(logo, JLabel.CENTER);
        content.add(logoLabel, BorderLayout.CENTER);

        // Título
        JLabel titulo = new JLabel("Hospital La Salud", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        content.add(titulo, BorderLayout.NORTH);

        // Barra de progreso
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true); // Mostrar el porcentaje
        content.add(progressBar, BorderLayout.SOUTH);

        // Configurar ventana
        setContentPane(content);
        setSize(500, 300);
        setLocationRelativeTo(null); // Centrado
    }

    public void mostrar() {
        setVisible(true);

        for (int i = 0; i <= 100; i++) {
            progressBar.setValue(i);
            try {
                Thread.sleep(40); // 40ms * 100 = 4 segundos total
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        dispose(); // Cerrar splash al terminar
    }

    public static void main(String[] args) {
        Loading splash = new Loading();
        splash.mostrar();

        // Después de la carga, mostrar el menú principal
        JFrame menuPrincipal = new JFrame("Menú Principal");
        menuPrincipal.setSize(800, 600);
        menuPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuPrincipal.setLocationRelativeTo(null);
        menuPrincipal.setVisible(true);
    }
}
