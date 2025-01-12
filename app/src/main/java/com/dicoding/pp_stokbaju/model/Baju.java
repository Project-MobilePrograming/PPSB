package com.dicoding.pp_stokbaju.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Baju implements Parcelable {

    private int id;
    private String nama_baju;
    private String nama_jenis_baju;
    private int id_jenis_baju;
    private int id_ukuran_baju;
    private String ukuran_baju;
    private double harga;
    private int stok;
    private String gambar_url;

    // Constructor untuk membuat objek Baju tanpa ID (digunakan saat create)
    public Baju(String nama_baju, int id_jenis_baju, String nama_jenis_baju, int id_ukuran_baju, String ukuran_baju, double harga, int stok, String gambar_url) {
        this.nama_baju = nama_baju;
        this.id_jenis_baju = id_jenis_baju;
        this.nama_jenis_baju = nama_jenis_baju;
        this.id_ukuran_baju = id_ukuran_baju;
        this.ukuran_baju = ukuran_baju;
        this.harga = harga;
        this.stok = stok;
        this.gambar_url = gambar_url;
    }

    // Constructor untuk membuat objek Baju dengan ID (digunakan saat update)
    public Baju(int id, String nama_baju, int id_jenis_baju, String nama_jenis_baju, int id_ukuran_baju, String ukuran_baju, double harga, int stok, String gambar_url) {
        this.id = id;
        this.nama_baju = nama_baju;
        this.id_jenis_baju = id_jenis_baju;
        this.nama_jenis_baju = nama_jenis_baju;
        this.id_ukuran_baju = id_ukuran_baju;
        this.ukuran_baju = ukuran_baju;
        this.harga = harga;
        this.stok = stok;
        this.gambar_url = gambar_url;
    }

    // Parcelable implementation
    protected Baju(Parcel in) {
        id = in.readInt();
        nama_baju = in.readString();
        id_jenis_baju = in.readInt();
        nama_jenis_baju = in.readString();
        id_ukuran_baju = in.readInt();
        ukuran_baju = in.readString();
        harga = in.readDouble();
        stok = in.readInt();
        gambar_url = in.readString();
    }

    public static final Creator<Baju> CREATOR = new Creator<Baju>() {
        @Override
        public Baju createFromParcel(Parcel in) {
            return new Baju(in);
        }

        @Override
        public Baju[] newArray(int size) {
            return new Baju[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama_baju);
        dest.writeInt(id_jenis_baju);
        dest.writeString(nama_jenis_baju);
        dest.writeInt(id_ukuran_baju);
        dest.writeString(ukuran_baju);
        dest.writeDouble(harga);
        dest.writeInt(stok);
        dest.writeString(gambar_url);
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

    public String getNama_jenis_baju() {
        return nama_jenis_baju;
    }

    public void setNama_jenis_baju(String nama_jenis_baju) {
        this.nama_jenis_baju = nama_jenis_baju;
    }

    public int getId_ukuran_baju() {
        return id_ukuran_baju;
    }

    public void setId_ukuran_baju(int id_ukuran_baju) {
        this.id_ukuran_baju = id_ukuran_baju;
    }

    public String getUkuran_baju() {
        return ukuran_baju;
    }

    public void setUkuran_baju(String ukuran_baju) {
        this.ukuran_baju = ukuran_baju;
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