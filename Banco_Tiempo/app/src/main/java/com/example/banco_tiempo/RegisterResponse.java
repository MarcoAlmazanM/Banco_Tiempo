package com.example.banco_tiempo;

import java.io.Serializable;

public class RegisterResponse implements Serializable {
    private Integer registerApproval;
    private String error;


    public Integer getRegisterApproval() {
        return registerApproval;
    }

    public void setRegisterApproval(Integer registerApproval) {
        this.registerApproval = registerApproval;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
