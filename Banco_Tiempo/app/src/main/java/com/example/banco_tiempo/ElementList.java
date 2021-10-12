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
    public ElementList(Integer idServicio, String idUsuario, String colonia,
                         String nombre, String descripcion, String certificado,
                         String image, String nombreUsuario, String apellidoUsuario,
                         String foto, String color) {
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

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
