package com.dicoding.pp_stokbaju.model;

public class JenisBaju {
    // ID jenis baju (primary key di database)
    private int id;

    // Nama jenis baju (contoh: Kaos, Kemeja, Jaket)
    private String nama_jenis_baju;

    // Constructor untuk membuat objek JenisBaju tanpa ID (digunakan saat create)
    public JenisBaju(String nama_jenis_baju) {
        this.nama_jenis_baju = nama_jenis_baju;
    }

    // Constructor untuk membuat objek JenisBaju dengan ID (digunakan saat update)
    public JenisBaju(int id, String nama_jenis_baju) {
        this.id = id;
        this.nama_jenis_baju = nama_jenis_baju;
    }

    // Getter dan Setter untuk semua field
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_jenis_baju() {
        return nama_jenis_baju;
    }

    public void setNama_jenis_baju(String nama_jenis_baju) {
        this.nama_jenis_baju = nama_jenis_baju;
    }
}