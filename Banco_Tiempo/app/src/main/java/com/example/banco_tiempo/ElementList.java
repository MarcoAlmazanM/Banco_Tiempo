package com.example.banco_tiempo;

import java.io.Serializable;

public class ElementList implements Serializable {
    public String nombre;
    public String trabajo;
    public String foto;
    public String color;

    public ElementList(String nombre, String trabajo, String color, String foto) {
        this.nombre = nombre;
        this.trabajo = trabajo;
        this.color = color;
        this.foto=foto;
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
