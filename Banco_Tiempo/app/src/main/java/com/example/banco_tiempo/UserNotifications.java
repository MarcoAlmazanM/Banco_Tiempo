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
    private String nombreApellidoM;
    private String correo;


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

    public String getNombreApellidoM() { return nombreApellidoM; }


    public UserNotifications(Integer idNot, String idEmisor, String idReceptor, Integer idServicio,
                             String tipo, String nombre, String descripcion, String nombreUsuario,
                             String nombreApellidoP, String nombreApellidoM, String correo) {
        this.idNot = idNot;
        this.idEmisor = idEmisor;
        this.idReceptor = idReceptor;
        this.idServicio = idServicio;
        this.tipo = tipo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nombreUsuario = nombreUsuario;
        this.nombreApellidoP = nombreApellidoP;
        this.nombreApellidoM = nombreApellidoM;
        this.correo = correo;

    }
}
