package com.dicoding.pp_stokbaju.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
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

        recyclerViewStokBaju = findViewById(R.id.recyclerViewStokBaju);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        bajuList = new ArrayList<>();
        bajuAdapter = new BajuAdapter(bajuList, baju -> {
            Intent intent = new Intent(StokBarangActivity.this, DetailBarangActivity.class);
            intent.putExtra("BAJU_DATA", baju);
            startActivityForResult(intent, 1);
        });

        recyclerViewStokBaju.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewStokBaju.setAdapter(bajuAdapter);

        fetchDataBaju();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            fetchDataBaju(); // Refresh data stok setelah detail atau update selesai
        }
    }


    private void fetchDataBaju() {
        Call<ApiResponse<List<Baju>>> call = apiService.getAllBaju();
        call.enqueue(new Callback<ApiResponse<List<Baju>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Baju>>> call, Response<ApiResponse<List<Baju>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bajuList.clear();
                    bajuList.addAll(response.body().getData());
                    bajuAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Baju>>> call, Throwable t) {
                Log.e("StokBarangActivity", "Error: " + t.getMessage());
                Toast.makeText(StokBarangActivity.this, "Gagal memuat data stok.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
