package com.dicoding.pp_stokbaju.ui;

public class Barang {
    private String namaBarang;
    private int jumlahBarang;

    public Barang(String namaBarang, int jumlahBarang) {
        this.namaBarang = namaBarang;
        this.jumlahBarang = jumlahBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }
}
