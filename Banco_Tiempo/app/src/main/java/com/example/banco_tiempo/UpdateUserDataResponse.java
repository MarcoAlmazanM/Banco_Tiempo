package com.example.banco_tiempo;

public class UpdateUserDataResponse {
    private Integer transactionApproval;
    private String error;

    public Integer getTransactionApproval() {
        return transactionApproval;
    }

    public void setTransactionApproval(Integer transactionApproval) {
        this.transactionApproval = transactionApproval;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
