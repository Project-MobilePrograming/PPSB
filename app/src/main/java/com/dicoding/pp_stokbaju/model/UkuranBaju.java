package com.dicoding.pp_stokbaju.model;

public class UkuranBaju {
    // ID ukuran baju (primary key di database)
    private int id;

    // Nama ukuran baju (contoh: S, M, L, XL)
    private String ukuran_baju;

    // Constructor untuk membuat objek UkuranBaju tanpa ID (digunakan saat create)
    public UkuranBaju(String ukuran_baju) {
        this.ukuran_baju = ukuran_baju;
    }

    // Constructor untuk membuat objek UkuranBaju dengan ID (digunakan saat update)
    public UkuranBaju(int id, String ukuran_baju) {
        this.id = id;
        this.ukuran_baju = ukuran_baju;
    }

    // Getter dan Setter untuk semua field
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUkuran_baju() {
        return ukuran_baju;
    }

    public void setUkuran_baju(String ukuran_baju) {
        this.ukuran_baju = ukuran_baju;
    }
}