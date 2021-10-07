package com.example.banco_tiempo;

import java.io.Serializable;

public class ImageResponse implements Serializable {
    Integer transactionApproval;
    String url;

    public Integer getTransactionApproval() {
        return transactionApproval;
    }

    public void setTransactionApproval(Integer transactionApproval) {
        this.transactionApproval = transactionApproval;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
