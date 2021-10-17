package com.example.banco_tiempo;

public class UserRequestOfferResponse {
    private Integer transactionApproval;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getTransactionApproval() {
        return transactionApproval;
    }

    public void setTransactionApproval(Integer transactionApproval) {
        this.transactionApproval = transactionApproval;
    }
}
