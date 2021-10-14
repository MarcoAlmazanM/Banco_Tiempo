package com.example.banco_tiempo;

public class UserAcceptedServicesResponse {
    private Integer idNot;
    private String idReceptor;
    private String idEmisor;
    private Integer idServicio;
    private String image;
    private String nombre;
    private String categoria;
    private String descripcion;
    private Integer something;

    public Integer getIdNot() {
        return idNot;
    }

    public void setIdNot(Integer idNot) {
        this.idNot = idNot;
    }

    public String getIdReceptor() {
        return idReceptor;
    }

    public void setIdReceptor(String idReceptor) {
        this.idReceptor = idReceptor;
    }

    public String getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(String idEmisor) {
        this.idEmisor = idEmisor;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getSomething() {
        return something;
    }

    public void setSomething(Integer something) {
        this.something = something;
    }
}
