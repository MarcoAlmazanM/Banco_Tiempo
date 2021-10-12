package com.example.banco_tiempo;

public class OfferVO {

    private String trabajo;
    private String info;
    private int imagen;

    public OfferVO(String trabajo, String info, int imagen){
        this.trabajo = trabajo;
        this.info = info;
        this.imagen = imagen;
    }

    public String getTrabajo() {return trabajo;}

    public void setTrabajo(String trabajo) {this.trabajo = trabajo;}

    public String getInfo() {return info;}

    public void setInfo(String info) {this.info = info;}

    public int getImagen() {return imagen;}

    public void setImagen(int imagen) {this.imagen = imagen;}

}
