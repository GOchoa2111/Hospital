package controllers;

import views.ViewLogin;
import models.ModelLogin;
import services.ServiceLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import views.ViewMenu;

public class ControllerLogin {

    private ViewLogin view;
    private ModelLogin model;
    private ServiceLogin service;

    public ControllerLogin(ViewLogin view, ModelLogin model, ServiceLogin service) {
        this.view = view;
        this.model = model;
        this.service = service;

        this.view.setVisible(true);

        this.view.getBtnIniciarSesion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });
    }

    private void autenticarUsuario() {
        String usuario = view.getTxtUsuario().getText().trim();
        String pass = String.valueOf(view.getTxtPassword().getPassword()).trim();

        if (usuario.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Debe ingresar usuario y contraseña", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Creamos el modelo con los datos ingresados
        ModelLogin login = new ModelLogin(usuario, pass);

        // Autenticamos el usuario
        ModelLogin usuarioLogueado = service.autenticar(login);

        if (usuarioLogueado != null) {
            JOptionPane.showMessageDialog(view, "Bienvenido!!   " + usuarioLogueado.getNombreUsuario());

            // Obtenemos la baseUrl y token
            String baseUrl = service.getBaseUrl(); // ✅ Ya debe estar definido en ServiceLogin
            String token = usuarioLogueado.getToken();

            // Pasamos el modelo y baseUrl a la vista principal
            ViewMenu menu = new ViewMenu(usuarioLogueado);
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);

            // Cerramos el login
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
