package com.example.banco_tiempo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OffersResponse implements Serializable {

    private OffersDetails[] ofertas;

    public OffersDetails[] getOfertas() {
        return ofertas;
    }

    public void setOfertas(OffersDetails[] ofertas) {
        this.ofertas = ofertas;
    }
}
