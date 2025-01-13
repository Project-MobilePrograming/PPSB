package com.dicoding.pp_stokbaju.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // Base URL untuk API Anda
    private static final String BASE_URL = "https://9d71-114-122-139-163.ngrok-free.app"; // Ganti dengan URL server Anda
    private static Retrofit retrofit;

    // Mendapatkan instance Retrofit
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // Menambahkan Gson converter untuk parsing JSON
                    .build();
        }
        return retrofit;
    }
}
