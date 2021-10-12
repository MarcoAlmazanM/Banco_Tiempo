package com.example.banco_tiempo;

public class OffersResponse {
    private String id;

    private OffersDetails offersDetails;

    //Constructor
    public OffersResponse(String id, OffersDetails offersDetails) {
        this.id = id;
        this.offersDetails = offersDetails;
    }

    //Getters && Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OffersDetails getOffersDetails() {
        return offersDetails;
    }

    public void setOffersDetails(OffersDetails offersDetails) {
        this.offersDetails = offersDetails;
    }
}
