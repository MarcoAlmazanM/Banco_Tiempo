package com.example.banco_tiempo;

import java.io.Serializable;

public class NewOfferResponse implements Serializable {
    private String transactionApproval;

    public String getTransactionApproval() {
        return transactionApproval;
    }

    public void setTransactionApproval(String transactionApproval) {
        this.transactionApproval = transactionApproval;
    }
}
