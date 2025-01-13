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
import retrofit2.http.Query;

public interface ApiService {
    // Baju endpoints
    @Multipart
    @POST("api/baju/create")
    Call<ApiResponse<Void>> createBaju(
            @Part("nama_baju") RequestBody namaBaju,
            @Part("id_jenis_baju") RequestBody idJenisBaju,
            @Part("id_ukuran_baju") RequestBody idUkuranBaju,
            @Part("harga") RequestBody harga,
            @Part("stok") RequestBody stok,
            @Part MultipartBody.Part image
    );

    @GET("api/baju/read")
    Call<ApiResponse<List<Baju>>> getAllBaju();

    @GET("api/baju/read/{id}")
    Call<ApiResponse<Baju>> getBajuById(@Path("id") int id);

    @Multipart
    @POST("api/baju/update") // Gunakan POST jika PUT tidak didukung backend
    Call<ApiResponse<Baju>> updateBaju(
            @Part("id") RequestBody id,
            @Part("nama_baju") RequestBody namaBaju,
            @Part("id_jenis_baju") RequestBody idJenisBaju,
            @Part("id_ukuran_baju") RequestBody idUkuranBaju,
            @Part("harga") RequestBody harga,
            @Part("stok") RequestBody stok,
            @Part MultipartBody.Part image // Ini opsional, bisa null
    );


    @DELETE("api/baju/delete")
    Call<ApiResponse<Void>> deleteBaju(@Query("id") int id);

    // Jenis Baju endpoints
    @GET("api/jenis_baju/read")
    Call<ApiResponse<List<JenisBaju>>> getAllJenisBaju();

    // Ukuran Baju endpoints
    @GET("api/ukuran_baju/read")
    Call<ApiResponse<List<UkuranBaju>>> getAllUkuranBaju();
}