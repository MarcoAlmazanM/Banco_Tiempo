package com.example.banco_tiempo;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    private String name;
    private String lastName;
    private String lastNameM;
    private String email;
    private String username;
    private Integer loginApproval;
    private Integer statusHours;
    private Integer documentosApproval;
    private String foto;
    private String colonia;
    private String calle;
    private String numero;
    private String municipio;
    private String estado;
    private Integer CP;

    public Integer getLoginApproval() {
        return loginApproval;
    }

    public void setLoginApproval(Integer loginApproval) {
        this.loginApproval = loginApproval;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getStatusHours() {
        return statusHours;
    }

    public void setStatusHours(Integer statusHours) {
        this.statusHours = statusHours;
    }

    public Integer getDocumentosApproval() {
        return documentosApproval;
    }

    public void setDocumentosApproval(Integer documentosApproval) {
        this.documentosApproval = documentosApproval;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getLastNameM() {
        return lastNameM;
    }

    public void setLastNameM(String lastNameM) {
        this.lastNameM = lastNameM;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Integer getCP() {
        return CP;
    }

    public void setCP(Integer CP) {
        this.CP = CP;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

