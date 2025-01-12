package com.dicoding.pp_stokbaju.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dicoding.pp_stokbaju.R;
import com.dicoding.pp_stokbaju.adapter.BajuAdapter;
import com.dicoding.pp_stokbaju.api.ApiResponse;
import com.dicoding.pp_stokbaju.api.ApiService;
import com.dicoding.pp_stokbaju.api.RetrofitClient;
import com.dicoding.pp_stokbaju.model.Baju;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StokBarangActivity extends AppCompatActivity {

    private RecyclerView recyclerViewStokBaju;
    private BajuAdapter bajuAdapter;
    private List<Baju> bajuList;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok_barang);

        // Inisialisasi view
        recyclerViewStokBaju = findViewById(R.id.recyclerViewStokBaju);

        // Inisialisasi ApiService
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Inisialisasi data
        bajuList = new ArrayList<>();

        // Setup RecyclerView
        bajuAdapter = new BajuAdapter(bajuList, new BajuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Baju baju) {
                // Buka DetailBarangActivity dengan mengirim data baju
                Intent intent = new Intent(StokBarangActivity.this, DetailBarangActivity.class);
                intent.putExtra("BAJU_DATA", baju); // Kirim data baju sebagai Parcelable
                startActivity(intent);
            }
        });

        recyclerViewStokBaju.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewStokBaju.setAdapter(bajuAdapter);

        // Ambil data baju dari API
        fetchDataBaju();
    }
    private void fetchDataBaju() {
        Call<ApiResponse<List<Baju>>> call = apiService.getAllBaju();
        call.enqueue(new Callback<ApiResponse<List<Baju>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Baju>>> call, Response<ApiResponse<List<Baju>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<Baju>> apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        List<Baju> bajuListResponse = apiResponse.getData();
                        if (bajuListResponse != null) {
                            bajuList.clear();
                            bajuList.addAll(bajuListResponse);

                            // Ensure image URLs are complete
                            for (Baju baju : bajuList) {
                                if (baju.getGambar_url() != null && !baju.getGambar_url().startsWith("http")) {
                                    baju.setGambar_url("http://example.com/" + baju.getGambar_url());
                                }
                            }

                            bajuAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(StokBarangActivity.this, "Data baju kosong", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(StokBarangActivity.this, "Gagal mengambil data baju: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(StokBarangActivity.this, "Response tidak sukses", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Baju>>> call, Throwable t) {
                Toast.makeText(StokBarangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}