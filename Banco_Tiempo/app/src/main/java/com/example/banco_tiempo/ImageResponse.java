package com.example.banco_tiempo;

import java.io.Serializable;

public class ImageResponse implements Serializable {
    Integer transactionApproval;

    public Integer getTransactionApproval() {
        return transactionApproval;
    }

    public void setTransactionApproval(Integer transactionApproval) {
        this.transactionApproval = transactionApproval;
    }
}
