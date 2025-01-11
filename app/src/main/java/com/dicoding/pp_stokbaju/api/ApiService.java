package com.dicoding.pp_stokbaju.api;

import com.dicoding.pp_stokbaju.model.Baju;
import com.dicoding.pp_stokbaju.model.JenisBaju;
import com.dicoding.pp_stokbaju.model.UkuranBaju;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
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
    // Endpoint untuk membuat jenis baju baru
    @POST("api/jenis_baju/create")
    Call<JenisBaju> createJenisBaju(@Body JenisBaju jenisBaju);

    // Endpoint untuk membaca semua jenis baju
    @GET("api/jenis_baju/read")
    Call<List<JenisBaju>> getAllJenisBaju();

    // Endpoint untuk membaca jenis baju berdasarkan ID
    @GET("api/jenis_baju/read/{id}")
    Call<JenisBaju> getJenisBajuById(@Path("id") int id);

    // Endpoint untuk mengupdate jenis baju
    @PUT("api/jenis_baju/update/{id}")
    Call<JenisBaju> updateJenisBaju(@Path("id") int id, @Body JenisBaju jenisBaju);

    // Endpoint untuk menghapus jenis baju
    @DELETE("api/jenis_baju/delete/{id}")
    Call<Void> deleteJenisBaju(@Path("id") int id);

    // ============================ UKURAN BAJU ============================
    // Endpoint untuk membuat ukuran baju baru
    @POST("api/ukuran_baju/create")
    Call<UkuranBaju> createUkuranBaju(@Body UkuranBaju ukuranBaju);

    // Endpoint untuk membaca semua ukuran baju
    @GET("api/ukuran_baju/read")
    Call<List<UkuranBaju>> getAllUkuranBaju();

    // Endpoint untuk membaca ukuran baju berdasarkan ID
    @GET("api/ukuran_baju/read/{id}")
    Call<UkuranBaju> getUkuranBajuById(@Path("id") int id);

    // Endpoint untuk mengupdate ukuran baju
    @PUT("api/ukuran_baju/update/{id}")
    Call<UkuranBaju> updateUkuranBaju(@Path("id") int id, @Body UkuranBaju ukuranBaju);

    // Endpoint untuk menghapus ukuran baju
    @DELETE("api/ukuran_baju/delete/{id}")
    Call<Void> deleteUkuranBaju(@Path("id") int id);
}