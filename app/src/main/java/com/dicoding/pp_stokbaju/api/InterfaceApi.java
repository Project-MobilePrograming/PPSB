package com.dicoding.pp_stokbaju.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
public class InterfaceApi {
    public interface ApiService {
        @Multipart
        @POST("path/to/your/api") // Ganti dengan endpoint API Anda
        Call<BajuResponse> uploadBaju(
                @Part("nama_baju") RequestBody namaBaju,
                @Part("id_jenis_baju") RequestBody idJenisBaju,
                @Part("id_ukuran_baju") RequestBody idUkuranBaju,
                @Part("harga") RequestBody harga,
                @Part("stok") RequestBody stok,
                @Part MultipartBody.Part image // Untuk file gambar
        );
    }

}
