package com.dicoding.pp_stokbaju.api;

import com.dicoding.pp_stokbaju.model.Baju;
import com.dicoding.pp_stokbaju.model.JenisBaju;
import com.dicoding.pp_stokbaju.model.UkuranBaju;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    // ============================ BAJU ============================
    // Endpoint untuk membuat data baju baru (menggunakan form-data)
    @Multipart
    @POST("api/baju/create")
    Call<Baju> createBaju(
            @Part("nama_baju") RequestBody namaBaju,
            @Part("id_jenis_baju") RequestBody idJenisBaju,
            @Part("id_ukuran_baju") RequestBody idUkuranBaju,
            @Part("harga") RequestBody harga,
            @Part("stok") RequestBody stok,
            @Part MultipartBody.Part image
    );

    // Endpoint untuk membaca semua data baju
    @GET("api/baju/read")
    Call<List<Baju>> getAllBaju();

    // Endpoint untuk membaca data baju berdasarkan ID
    @GET("api/baju/read/{id}")
    Call<Baju> getBajuById(@Path("id") int id);

    // Endpoint untuk mengupdate data baju (menggunakan form-data)
    @Multipart
    @PUT("api/baju/update/{id}")
    Call<Baju> updateBaju(
            @Path("id") int id,
            @Part("nama_baju") RequestBody namaBaju,
            @Part("id_jenis_baju") RequestBody idJenisBaju,
            @Part("id_ukuran_baju") RequestBody idUkuranBaju,
            @Part("harga") RequestBody harga,
            @Part("stok") RequestBody stok,
            @Part MultipartBody.Part image
    );

    // Endpoint untuk menghapus data baju
    @DELETE("api/baju/delete/{id}")
    Call<Void> deleteBaju(@Path("id") int id);

    // ============================ JENIS BAJU ============================
    @GET("api/jenis_baju/read")
    Call<List<JenisBaju>> getAllJenisBaju();

    // ============================ UKURAN BAJU ============================
    @GET("api/ukuran_baju/read")
    Call<List<UkuranBaju>> getAllUkuranBaju();

}