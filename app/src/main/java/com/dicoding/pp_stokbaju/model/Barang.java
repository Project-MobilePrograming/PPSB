package com.dicoding.pp_stokbaju.model;


public class Barang {
    private int id;
    private String NamaBarang;
    private String JumlahBarang;
    private int stok;
    private String gambar; // Gambar bisa berupa URL atau Base64 jika Anda mengirimnya sebagai string

    // Constructor
    public Barang(int id, String NmaBarang,int stok, String gambar) {
        this.id = id;
        this.NamaBarang = NamaBarang;
        this.JumlahBarang = JumlahBarang;
        this.stok = stok;
        this.gambar = gambar;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaBarang(){
        return NamaBarang;
    }

    public String getJumlahBarang(){
        return JumlahBarang;
    }

    public void setNamaBarang(String NamaBarang){
        this.NamaBarang = NamaBarang;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

}

