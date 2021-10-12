package com.example.banco_tiempo;

import java.io.Serializable;

public class ElementList implements Serializable {
    /*public String nombre;
    public String trabajo;
    public String foto;
    public String color;

     */
    private int idServicio;
    private String idUsuario;
    private String colonia;
    private String nombre;
    public String trabajo;
    private String descripcion;
    private String certificado;
    private String image;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String foto;
    public String color;

    /*public ElementList(String nombre, String trabajo, String color, String foto) {
        this.nombre = nombre;
        this.trabajo = trabajo;
        this.color = color;
        this.foto=foto;
    }

     */
    public ElementList(int idServicio, String idUsuario, String colonia,
                         String nombre, String descripcion, String certificado,
                         String image, String nombreUsuario, String apellidoUsuario, String foto) {
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
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(String trabajo) {
        this.trabajo = trabajo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


}
