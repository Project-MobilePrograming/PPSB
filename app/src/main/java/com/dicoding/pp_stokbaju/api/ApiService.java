package com.dicoding.pp_stokbaju.api;

import com.dicoding.pp_stokbaju.model.Barang;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface ApiService {

    // Endpoint untuk menambahkan barang
    @FormUrlEncoded
    @POST("add_barang.php")
    Call<ResponseBody> addBarang(
            @Field("nama_barang") String namaBarang,
            @Field("stok") int stok,
            @Field("gambar") String gambar // Gambar bisa berupa URL atau base64
    );

    // Endpoint untuk memperbarui barang
    @PUT("update_barang.php")
    Call<ResponseBody> updateBarang(
            @Body Barang barang
    );

    // Untuk menambahkan barang dengan gambar (multipart)
    @Multipart
    @POST("add_barang.php")
    Call<ResponseBody> addBarangWithImage(
            @Part("nama_barang") RequestBody namaBarang,
            @Part("stok") RequestBody stok,
            @Part MultipartBody.Part gambar
    );
}
