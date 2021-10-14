package com.example.banco_tiempo;

public class UserNotifications {
    private Integer idNot;
    private String idEmisor;
    private String idReceptor;
    private String idServicio;
    private String tipo;
    private String nombre;
    private String descripcion;

    public Integer getIdNot() {
        return idNot;
    }

    public String getIdEmisor() {
        return idEmisor;
    }

    public String getIdReceptor() {
        return idReceptor;
    }

    public String getIdServicio() {
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

    public UserNotifications(Integer idNot, String idEmisor, String idReceptor, String idServicio, String nombre, String tipo) {
        this.idNot = idNot;
        this.idEmisor = idEmisor;
        this.idReceptor = idReceptor;
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.tipo = tipo;
    }
}
