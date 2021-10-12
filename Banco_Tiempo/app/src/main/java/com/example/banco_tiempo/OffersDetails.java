package com.example.banco_tiempo;

public class OffersDetails {
    private int idServicio;
    private String idUsuario;
    private String colonia;
    private String nombre;
    private String descripcion;
    private String certificado;
    private String image;
    private String nombreUsuario;
    private String categoria;

    //Getters
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

    public String getImage() {
        return image;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getCategoria() {
        return categoria;
    }
    // Constructor
    public OffersDetails(int idServicio, String idUsuario, String colonia,
                         String nombre, String descripcion, String certificado,
                         String image, String nombreUsuario, String categoria) {
        this.idServicio = idServicio;
        this.idUsuario = idUsuario;
        this.colonia = colonia;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.certificado = certificado;
        this.image = image;
        this.nombreUsuario = nombreUsuario;
        this.categoria = categoria;
    }
}
