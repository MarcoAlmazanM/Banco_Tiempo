package com.example.banco_tiempo;

public class RecoverPasswordResponse {
    private Integer emailApproval;
    private String error;

    public Integer getEmailApproval() {
        return emailApproval;
    }

    public void setEmailApproval(Integer emailApproval) {
        this.emailApproval = emailApproval;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
