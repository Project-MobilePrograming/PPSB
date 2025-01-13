package com.dicoding.pp_stokbaju.api;

import java.util.List;
public class ApiResponse<T> {
    private String status; // "success" atau "error"
    private String message; // Pesan dari API
    private T data; // Data yang diterima (bisa berupa objek atau list)

    // Constructor
    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Getter dan Setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // Method untuk mengecek apakah request sukses
    public boolean isSuccess() {
        return "success".equals(status);
    }
}