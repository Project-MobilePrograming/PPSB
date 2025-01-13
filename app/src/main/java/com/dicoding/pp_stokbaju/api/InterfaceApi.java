package com.dicoding.pp_stokbaju.api;

import android.content.Context;
import android.widget.Toast;

import com.dicoding.pp_stokbaju.model.Barang;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import okhttp3.ResponseBody;

public class InterfaceApi {

    // Fungsi untuk menambahkan barang
    public static void addBarang(String namaBarang, int stok, String gambar) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.addBarang(namaBarang, stok, gambar);

        call.enqueue(new Callback<ResponseBody>() {
            private Context context;

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Barang berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Gagal menambahkan barang", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Fungsi untuk memperbarui barang
    public static void updateBarang(Barang barang) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.updateBarang(barang);

        call.enqueue(new Callback<ResponseBody>() {
            private Context context;
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Barang berhasil diperbarui", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Gagal memperbarui barang", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Fungsi untuk menambahkan barang dengan gambar (Multipart)
    public static void addBarangWithImage(String namaBarang, int stok, String gambarPath) {
        // Convert gambar menjadi MultipartBody.Part jika ingin mengirim file gambar
        File file = new File(gambarPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part gambarPart = MultipartBody.Part.createFormData("gambar", file.getName(), requestBody);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.addBarangWithImage(
                RequestBody.create(MediaType.parse("text/plain"), namaBarang),
                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(stok)),
                gambarPart
        );

        call.enqueue(new Callback<ResponseBody>() {
            private Context context;
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Barang berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Gagal menambahkan barang", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
