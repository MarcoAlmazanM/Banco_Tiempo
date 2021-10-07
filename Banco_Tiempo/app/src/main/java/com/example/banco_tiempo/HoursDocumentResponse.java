package com.example.banco_tiempo;

import java.io.Serializable;

public class HoursDocumentResponse implements Serializable {
    private Integer statusHoras;
    private Integer statusDocumentos;

    public Integer getStatusHoras() {
        return statusHoras;
    }

    public void setStatusHoras(Integer statusHoras) {
        this.statusHoras = statusHoras;
    }

    public Integer getStatusDocumentos() {
        return statusDocumentos;
    }

    public void setStatusDocumentos(Integer statusDocumentos) {
        this.statusDocumentos = statusDocumentos;
    }
}
