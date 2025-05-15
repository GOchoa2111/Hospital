/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Carlos Orozco
 */
public class ModelLogin {

    private String nombreUsuario;
    private String contrasena;

    public ModelLogin(String nombreUsuario, String contrasena) {

        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    public ModelLogin() {
        // Constructor vacío para inicialización
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
