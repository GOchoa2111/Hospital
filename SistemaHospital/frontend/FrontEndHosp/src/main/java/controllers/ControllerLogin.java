/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

/**
 *
 * @author Carlos Orozco
 */
import views.ViewLogin;
import models.ModelLogin;
import services.ServiceLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import views.ViewMenu;

public class ControllerLogin {

    private ViewLogin view; //referencia a la ventana/formulario login
    private ModelLogin model; //referencia al modelo
    private ServiceLogin service; //instancia para el servicio

    // Constructor que recibe la vista para conectar la lógica con la interfaz
    public ControllerLogin(ViewLogin view, ModelLogin model, ServiceLogin service) {

        this.view = view;
        this.service = service;
        this.model = model;

        this.view.setVisible(true);

        // Agregamos un listener para manejar el evento del botón "Iniciar sesión"
        this.view.getBtnIniciarSesion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();  // Método que realiza la autenticación
            }
        });
    }

    // Método privado para validar y autenticar al usuario cuando presiona el botón
    private void autenticarUsuario() {

        // Obtener los valores ingresados por el usuario en los campos de texto
        String usuario = view.getTxtUsuario().getText().trim();
        String pass = String.valueOf(view.getTxtPassword().getPassword()).trim();

        // Validación simple para asegurarse que no estén vacíos
        if (usuario.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Debe ingresar usuario y contraseña", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Creamos el modelo con los datos ingresados para enviarlo al servicio
        ModelLogin login = new ModelLogin(usuario, pass);

        // Llamamos al método que consume el API para verificar el login
        ModelLogin usuarioLogueado = service.autenticar(login);

        // Verificamos si el login fue exitoso
        if (usuarioLogueado != null) {
            JOptionPane.showMessageDialog(view, "Bienvenido!!   " + usuarioLogueado.getNombreUsuario());

            // Abrir ventana principal del sistema pasando el usuario logueado con token e id
            ViewMenu menu = new ViewMenu(usuarioLogueado);
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);

            // Cerrar formulario login
            view.dispose();

        } else {
            JOptionPane.showMessageDialog(view, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
