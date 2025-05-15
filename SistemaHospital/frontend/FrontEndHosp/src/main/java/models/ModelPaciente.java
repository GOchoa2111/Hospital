package models;

import java.util.Date;


public class ModelPaciente { //encapsulamiento de datos
    
    
    private int id_paciente;
    private Date fecha_ingreso;
    private String nombre;
    private String apellido;
    private Date fecha_nacimiento;
    private String genero;
    private String tipo_sangre;
    private String direccion;
    private int telefono;
    private String correo;
    
       // Getters (obtener datos) y setters (modificar valores)
    public int getid_paciente() { return id_paciente; }
    public void setid_paciente(int id_paciente) { this.id_paciente = id_paciente; }
    
    public Date getfecha_ingreso(){return fecha_ingreso; }
    public void setfecha_ingreso(Date fecha_ingreso) { this.fecha_ingreso = fecha_ingreso;}
    
    public String getnombre() { return nombre;}
    public void setnombre( String nombre){ this.nombre = nombre;}
    
    public String getapellido() { return apellido;}
    public void setapellido( String apellido) { this.apellido = apellido;}
    
    public Date getfecha_nacimiento() { return fecha_nacimiento;}
    public void setfecha_nacimiento( Date fecha_nacimiento) { this.fecha_nacimiento = fecha_nacimiento;}
    
    public String getgenero() { return genero;}
    public void setgenero( String genero) { this.genero = genero;}
    
    public String gettipo_sangre() { return tipo_sangre;}
    public void settipo_sangre( String tipo_sangre) { this.tipo_sangre = tipo_sangre;}
    
    public String getdireccion() { return direccion;}
    public void setdireccion( String direccion) { this.direccion = direccion;}
    
    public int gettelefon() { return telefono;}
    public void settelefono( int telefono) { this.telefono = telefono;}
    
    public String getcorreo() { return correo;}
    public void setcorreo( String correo) { this.correo = correo;}

    
}

