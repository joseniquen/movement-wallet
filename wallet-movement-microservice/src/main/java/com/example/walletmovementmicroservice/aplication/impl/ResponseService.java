package com.example.walletmovementmicroservice.aplication.impl;

import com.example.walletmovementmicroservice.utils.Status;

public class ResponseService {
    public String message;
    public Status status;
    public Object data;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
