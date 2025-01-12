package com.dicoding.pp_stokbaju.api;
import android.net.Uri;
import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ApiService {
    public void uploadBaju(String namaBaju, String idJenisBaju, String idUkuranBaju,
                           String harga, String stok, Uri imageUri) {

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Buat RequestBody untuk data biasa
        RequestBody namaBajuBody = RequestBody.create(MediaType.parse("text/plain"), namaBaju);
        RequestBody idJenisBajuBody = RequestBody.create(MediaType.parse("text/plain"), idJenisBaju);
        RequestBody idUkuranBajuBody = RequestBody.create(MediaType.parse("text/plain"), idUkuranBaju);
        RequestBody hargaBody = RequestBody.create(MediaType.parse("text/plain"), harga);
        RequestBody stokBody = RequestBody.create(MediaType.parse("text/plain"), stok);

        // Buat MultipartBody.Part untuk gambar
        File file = new File(imageUri.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        // Panggil API
        Call<BajuResponse> call = RetrofitClient.uploadBaju(namaBajuBody, idJenisBajuBody, idUkuranBajuBody,hargaBody, stokBody, imagePart);

        call.enqueue(new Callback<BajuResponse>() {
            @Override
            public void onResponse(Call<BajuResponse> call, Response<BajuResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        // Data berhasil diunggah
                        System.out.println("Pesan: " + response.body().getMessage());
                    } else {
                        // Error dari server
                        System.err.println("Error: " + response.body().getError());
                    }
                }
            }

            @Override
            public void onFailure(Call<BajuResponse> call, Throwable t) {
                // Gagal terhubung
                System.err.println("Error: " + t.getMessage());
            }
        });
    }


}
