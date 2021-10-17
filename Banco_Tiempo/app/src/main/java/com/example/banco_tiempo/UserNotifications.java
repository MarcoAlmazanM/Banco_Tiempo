package com.example.banco_tiempo;

public class UserNotifications {
    private Integer idNot;
    private String idEmisor;
    private String idReceptor;
    private Integer idServicio;
    private String tipo;
    private String nombre;
    private String descripcion;
    private String nombreUsuario;
    private String nombreApellidoP;
    private String NombreApellidoM;
    private String correo;
    private String error;

    public Integer getIdNot() {
        return idNot;
    }

    public String getIdEmisor() {
        return idEmisor;
    }

    public String getIdReceptor() {
        return idReceptor;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() { return descripcion; }

    public String getCorreo() { return correo; }

    public String getNombreUsuario() { return nombreUsuario; }

    public String getNombreApellidoP() { return nombreApellidoP; }

    public String getNombreApellidoM() { return NombreApellidoM; }

    public UserNotifications(Integer idNot, String idEmisor, String idReceptor, Integer idServicio, String nombre, String tipo, String nombreUsuario, String nombreApellidoP, String nombreApellidoM, String correo) {
    public String getError() {
        return error;
    }

    public UserNotifications(Integer idNot, String idEmisor, String idReceptor, Integer idServicio, String nombre, String tipo, String error) {
        this.idNot = idNot;
        this.idEmisor = idEmisor;
        this.idReceptor = idReceptor;
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.tipo = tipo;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.nombreApellidoP = nombreApellidoP;
        this.NombreApellidoM= nombreApellidoM;
        this.correo = correo;
        this.error = error;
    }
}
