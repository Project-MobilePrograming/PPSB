package com.dicoding.pp_stokbaju.model;

public class JenisBaju {
    private int id;
    private String nama_jenis_baju;

    // Constructor
    public JenisBaju(int id, String nama_jenis_baju) {
        this.id = id;
        this.nama_jenis_baju = nama_jenis_baju;
    }

    // Getter dan Setter
    public int getId() {
        return id; // Mengembalikan ID jenis baju
    }

    public void setId(int id) {
        this.id = id; // Mengatur ID jenis baju
    }

    public String getNama_jenis_baju() {
        return nama_jenis_baju; // Mengembalikan nama jenis baju
    }

    public void setNama_jenis_baju(String nama_jenis_baju) {
        this.nama_jenis_baju = nama_jenis_baju; // Mengatur nama jenis baju
    }

    @Override
    public String toString() {
        return nama_jenis_baju; // Agar nama jenis baju ditampilkan di Spinner
    }
}