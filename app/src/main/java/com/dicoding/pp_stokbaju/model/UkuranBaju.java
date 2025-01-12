package com.dicoding.pp_stokbaju.model;

public class UkuranBaju {
    private int id;
    private String ukuran_baju;

    // Constructor
    public UkuranBaju(int id, String ukuran_baju) {
        this.id = id;
        this.ukuran_baju = ukuran_baju;
    }

    // Getter dan Setter
    public int getId() {
        return id; // Mengembalikan ID ukuran baju
    }

    public void setId(int id) {
        this.id = id; // Mengatur ID ukuran baju
    }

    public String getUkuran_baju() {
        return ukuran_baju; // Mengembalikan nama ukuran baju
    }

    public void setUkuran_baju(String ukuran_baju) {
        this.ukuran_baju = ukuran_baju; // Mengatur nama ukuran baju
    }

    @Override
    public String toString() {
        return ukuran_baju; // Agar ukuran baju ditampilkan di Spinner
    }
}