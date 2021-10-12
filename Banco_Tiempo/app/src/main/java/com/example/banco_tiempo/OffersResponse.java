package com.example.banco_tiempo;

import java.io.Serializable;

public class OffersResponse implements Serializable {
    private String ofertas;

    private OffersDetails offersDetails;

    //Constructor
    public OffersResponse(String id, OffersDetails offersDetails) {
        this.ofertas = ofertas;
        this.offersDetails = offersDetails;
    }

    //Getters && Setters


    public String getOfertas() {
        return ofertas;
    }

    public void setOfertas(String ofertas) {
        this.ofertas = ofertas;
    }

    public OffersDetails getOffersDetails() {
        return offersDetails;
    }

    public void setOffersDetails(OffersDetails offersDetails) {
        this.offersDetails = offersDetails;
    }
}
