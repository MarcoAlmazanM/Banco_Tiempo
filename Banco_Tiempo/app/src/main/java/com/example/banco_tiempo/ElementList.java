package com.example.banco_tiempo;

import java.io.Serializable;

public class ElementList implements Serializable {

    private int idServicio;
    private String idUsuario;
    private String colonia;
    private String nombre;
    private String descripcion;
    private String certificado;
    private String image;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String foto;
    public String color;

    public ElementList(Integer idServicio, String idUsuario, String colonia,
                         String nombre, String descripcion, String certificado,
                         String image, String nombreUsuario, String apellidoUsuario,
                         String foto,String color) {

        this.idServicio = idServicio;
        this.idUsuario = idUsuario;
        this.colonia = colonia;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.certificado = certificado;
        this.image = image;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.foto = foto;
        this.color = color;
    }

    //Setters
    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setColor(String color) {
        this.color = color;
    }

    //Getters
    public int getIdServicio() { return idServicio; }

    public String getIdUsuario() { return idUsuario; }

    public String getColonia() { return colonia; }

    public String getNombre() { return nombre; }

    public String getDescripcion() { return descripcion; }

    public String getCertificado() { return certificado; }

    public String getImage() { return image; }

    public String getNombreUsuario() { return nombreUsuario; }

    public String getApellidoUsuario() { return apellidoUsuario; }

    public String getFoto() { return foto; }

    public String getColor() { return color; }
}
