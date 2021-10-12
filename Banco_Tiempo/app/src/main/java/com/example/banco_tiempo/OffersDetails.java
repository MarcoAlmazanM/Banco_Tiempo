package com.example.banco_tiempo;

public class OffersDetails {
    private int idServicio;
    private String idUsuario;
    private String colonia;
    private String nombre;
    private String descripcion;
    private String certificado;
    private String imagen;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String foto;

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

    public String getImagen() {
        return imagen;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public String getFoto() {
        return foto;
    }

    // Constructor
    public OffersDetails(int idServicio, String idUsuario, String colonia,
                         String nombre, String descripcion, String certificado,
                         String image, String nombreUsuario, String apellidoUsuario,
                         String foto) {

        this.idServicio = idServicio;
        this.idUsuario = idUsuario;
        this.colonia = colonia;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.certificado = certificado;
        this.imagen = getImagen();
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.foto = foto;
    }
}
