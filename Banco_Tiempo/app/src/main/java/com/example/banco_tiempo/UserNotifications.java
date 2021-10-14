package com.example.banco_tiempo;

public class UserNotifications {
    private Integer idNot;
    private String idEmisor;
    private String idReceptor;
    private String idServicio;
    private String nombreServicio;
    private String tipo;

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

    public String getNombreServicio() {
        return nombreServicio;
    }

    public String getTipo() {
        return tipo;
    }

    public UserNotifications(Integer idNot, String idEmisor, String idReceptor, String idServicio, String nombreServicio, String tipo) {
        this.idNot = idNot;
        this.idEmisor = idEmisor;
        this.idReceptor = idReceptor;
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.tipo = tipo;
    }
}
