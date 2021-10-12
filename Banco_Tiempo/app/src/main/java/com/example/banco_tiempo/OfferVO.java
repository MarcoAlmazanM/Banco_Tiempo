package com.example.banco_tiempo;

public class OfferVO {

    private String trabajo;
    private String info;
    private String cate;
    private String imagen;

    public OfferVO(String trabajo, String info, String imagen, String cate){
        this.trabajo = trabajo;
        this.info = info;
        this.imagen = imagen;
        this.cate = cate;
    }

    public String getTrabajo() {return trabajo;}

    public void setTrabajo(String trabajo) {this.trabajo = trabajo;}

    public String getInfo() {return info;}

    public void setInfo(String info) {this.info = info;}

    public String getImagen() {return imagen;}

    public void setImagen(String imagen) {this.imagen = imagen;}

    public String getCate() {return cate;}

    public void setCate(String cate) {this.cate = cate;}

}
