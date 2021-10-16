package com.example.banco_tiempo;

public class UserNotifications {
    private Integer idNot;
    private String idEmisor;
    private String idReceptor;
    private Integer idServicio;
    private String tipo;
    private String nombre;
    private String descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

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
        this.error = error;
    }
}
