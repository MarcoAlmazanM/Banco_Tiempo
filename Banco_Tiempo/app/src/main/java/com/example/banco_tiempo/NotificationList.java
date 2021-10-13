package com.example.banco_tiempo;

public class NotificationList {
    private String trabajo;
    private String info;
    private String categoria;
    private String imagen;

    public NotificationList(String trabajo, String info, String imagen, String categoria){
        this.trabajo = trabajo;
        this.info = info;
        this.imagen = imagen;
        this.categoria = categoria;
    }

    public String getTrabajo() {return trabajo;}

    public void setTrabajo(String trabajo) {this.trabajo = trabajo;}

    public String getInfo() {return info;}

    public void setInfo(String info) {this.info = info;}

    public String getImagen() {return imagen;}

    public void setImagen(String imagen) {this.imagen = imagen;}

    public String getCate() {return categoria;}

    public void setCate(String cate) {this.categoria = cate;}

}
