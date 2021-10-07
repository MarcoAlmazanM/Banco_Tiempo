package com.example.banco_tiempo;

import java.io.Serializable;

public class NewOfferResponse implements Serializable {
    private Integer transactionApproval;

    public Integer getTransactionApproval() {
        return transactionApproval;
    }

    public void setTransactionApproval(Integer transactionApproval) {
        this.transactionApproval = transactionApproval;
    }
}
