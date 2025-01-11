package com.dicoding.pp_stokbaju.model;

public class Baju {
    // ID baju (primary key di database)
    private int id;

    // Nama baju
    private String nama_baju;

    // ID jenis baju (foreign key ke tabel jenis_baju)
    private int id_jenis_baju;

    // ID ukuran baju (foreign key ke tabel ukuran_baju)
    private int id_ukuran_baju;

    // Harga baju
    private double harga;

    // Stok baju
    private int stok;

    // URL gambar baju
    private String gambar_url;

    // Constructor untuk membuat objek Baju tanpa ID (digunakan saat create)
    public Baju(String nama_baju, int id_jenis_baju, int id_ukuran_baju, double harga, int stok, String gambar_url) {
        this.nama_baju = nama_baju;
        this.id_jenis_baju = id_jenis_baju;
        this.id_ukuran_baju = id_ukuran_baju;
        this.harga = harga;
        this.stok = stok;
        this.gambar_url = gambar_url;
    }

    // Constructor untuk membuat objek Baju dengan ID (digunakan saat update)
    public Baju(int id, String nama_baju, int id_jenis_baju, int id_ukuran_baju, double harga, int stok, String gambar_url) {
        this.id = id;
        this.nama_baju = nama_baju;
        this.id_jenis_baju = id_jenis_baju;
        this.id_ukuran_baju = id_ukuran_baju;
        this.harga = harga;
        this.stok = stok;
        this.gambar_url = gambar_url;
    }

    // Getter dan Setter untuk semua field
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_baju() {
        return nama_baju;
    }

    public void setNama_baju(String nama_baju) {
        this.nama_baju = nama_baju;
    }

    public int getId_jenis_baju() {
        return id_jenis_baju;
    }

    public void setId_jenis_baju(int id_jenis_baju) {
        this.id_jenis_baju = id_jenis_baju;
    }

    public int getId_ukuran_baju() {
        return id_ukuran_baju;
    }

    public void setId_ukuran_baju(int id_ukuran_baju) {
        this.id_ukuran_baju = id_ukuran_baju;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getGambar_url() {
        return gambar_url;
    }

    public void setGambar_url(String gambar_url) {
        this.gambar_url = gambar_url;
    }
}