package com.example.banco_tiempo;

public class UserOffersDetails {
    private int idServicio;
    private String idUsuario;
    private String colonia;
    private String nombre;
    private String descripcion;
    private String certificado;
    private String imagen;
    private String categoria;

    public int getIdServicio() {
        return idServicio;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getColonia() {
        return colonia;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCertificado() {
        return certificado;
    }

    public String getImagen() {
        return imagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public UserOffersDetails(int idServicio, String idUsuario, String colonia, String nombre, String descripcion, String certificado, String imagen, String categoria) {
        this.idServicio = idServicio;
        this.idUsuario = idUsuario;
        this.colonia = colonia;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.certificado = certificado;
        this.imagen = imagen;
        this.categoria = categoria;
    }
}
