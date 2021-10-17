package com.example.banco_tiempo;

public class NotificationList {
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

    public NotificationList(Integer idNot, String idEmisor, String idReceptor, Integer idServicio, String tipo, String nombre, String descripcion, String nombreUsuario, String nombreApellidoP, String nombreApellidoM, String correo) {
        this.idNot = idNot;
        this.idEmisor = idEmisor;
        this.idReceptor = idReceptor;
        this.idServicio = idServicio;
        this.tipo = tipo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nombreUsuario = nombreUsuario;
        this.nombreApellidoP = nombreApellidoP;
        this.NombreApellidoM= nombreApellidoM;
        this.correo = correo;
    }

    public Integer getIdNot() {
        return idNot;
    }

    public void setIdNot(Integer idNot) {
        this.idNot = idNot;
    }

    public String getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(String idEmisor) {
        this.idEmisor = idEmisor;
    }

    public String getIdReceptor() {
        return idReceptor;
    }

    public void setIdReceptor(String idReceptor) {
        this.idReceptor = idReceptor;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCorreo() { return correo; }

    public void setCorreo(String correo) { this.correo = correo; }

    public String getNombreUsuario() { return nombreUsuario; }

    public String getNombreApellidoP() { return nombreApellidoP; }

    public String getNombreApellidoM() { return NombreApellidoM; }
}
