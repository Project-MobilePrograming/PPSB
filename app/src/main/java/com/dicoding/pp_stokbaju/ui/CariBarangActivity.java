package com.dicoding.pp_stokbaju.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
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

public class CariBarangActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCariBarang;
    private BajuAdapter bajuAdapter;
    private List<Baju> bajuList;
    private EditText etCariBarang;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_barang);

        recyclerViewCariBarang = findViewById(R.id.recyclerViewCariBarang);
        etCariBarang = findViewById(R.id.etCariBarang);
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Inisialisasi RecyclerView
        recyclerViewCariBarang.setLayoutManager(new LinearLayoutManager(this));
        bajuList = new ArrayList<>();
        bajuAdapter = new BajuAdapter(bajuList, baju -> {
            // Ketika item diklik, buka DetailBarangActivity
            Intent intent = new Intent(CariBarangActivity.this, DetailBarangActivity.class);
            intent.putExtra("BAJU_DATA", baju); // Kirim data baju
            startActivityForResult(intent, 1); // Gunakan startActivityForResult untuk refresh data jika diperlukan
        });
        recyclerViewCariBarang.setAdapter(bajuAdapter);

        // Memuat data dari API
        loadDataFromApi();

        // Implementasi pencarian
        etCariBarang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            loadDataFromApi(); // Refresh data setelah kembali dari DetailBarangActivity
        }
    }

    private void loadDataFromApi() {
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
                Log.e("CariBarangActivity", "Error: " + t.getMessage());
                Toast.makeText(CariBarangActivity.this, "Gagal memuat data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filter(String text) {
        List<Baju> filteredList = new ArrayList<>();
        for (Baju baju : bajuList) {
            if (baju.getNama_baju().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(baju);
            }
        }
        bajuAdapter = new BajuAdapter(filteredList, baju -> {
            // Ketika item diklik, buka DetailBarangActivity
            Intent intent = new Intent(CariBarangActivity.this, DetailBarangActivity.class);
            intent.putExtra("BAJU_DATA", baju); // Kirim data baju
            startActivityForResult(intent, 1); // Gunakan startActivityForResult untuk refresh data jika diperlukan
        });
        recyclerViewCariBarang.setAdapter(bajuAdapter);
    }
}