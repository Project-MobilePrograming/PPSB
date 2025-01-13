package com.dicoding.pp_stokbaju.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.dicoding.pp_stokbaju.R;
import com.dicoding.pp_stokbaju.api.ApiResponse;
import com.dicoding.pp_stokbaju.api.ApiService;
import com.dicoding.pp_stokbaju.api.RetrofitClient;
import com.dicoding.pp_stokbaju.model.Baju;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBarangActivity extends AppCompatActivity {

    private static final int UPDATE_REQUEST_CODE = 1;

    private ImageView ivGambar;
    private TextView tvNamaBaju, tvJenisBaju, tvUkuranBaju, tvHarga, tvStok;
    private Button btnEdit, btnHapus;
    private Baju baju;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        ivGambar = findViewById(R.id.ivGambar);
        tvNamaBaju = findViewById(R.id.tvNamaBaju);
        tvJenisBaju = findViewById(R.id.tvJenisBaju);
        tvUkuranBaju = findViewById(R.id.tvUkuranBaju);
        tvHarga = findViewById(R.id.tvHarga);
        tvStok = findViewById(R.id.tvStok);
        btnEdit = findViewById(R.id.btnEdit);
        btnHapus = findViewById(R.id.btnHapus);

        apiService = RetrofitClient.getClient().create(ApiService.class);

        baju = getIntent().getParcelableExtra("BAJU_DATA");
        if (baju != null) {
            displayBajuDetails(baju);
        } else {
            Toast.makeText(this, "Data baju tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(DetailBarangActivity.this, UpdateBarangActivity.class);
            intent.putExtra("BAJU_DATA", baju);
            startActivityForResult(intent, UPDATE_REQUEST_CODE);
        });

        btnHapus.setOnClickListener(v -> deleteBaju(baju.getId()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_REQUEST_CODE && resultCode == RESULT_OK) {
            fetchBajuDetails(baju.getId());
        }
    }

    private void fetchBajuDetails(int id) {
        Call<ApiResponse<Baju>> call = apiService.getBajuById(id);
        call.enqueue(new Callback<ApiResponse<Baju>>() {
            @Override
            public void onResponse(Call<ApiResponse<Baju>> call, Response<ApiResponse<Baju>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    baju = response.body().getData();
                    displayBajuDetails(baju);
                } else {
                    Toast.makeText(DetailBarangActivity.this, "Gagal memuat data terbaru.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Baju>> call, Throwable t) {
                Toast.makeText(DetailBarangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayBajuDetails(Baju baju) {
        tvNamaBaju.setText(baju.getNama_baju());
        tvJenisBaju.setText("Jenis: " + baju.getNama_jenis_baju());
        tvUkuranBaju.setText("Ukuran: " + baju.getUkuran_baju());
        tvHarga.setText("Harga: Rp " + baju.getHarga());
        tvStok.setText("Stok: " + baju.getStok());

        if (baju.getGambar_url() != null && !baju.getGambar_url().isEmpty()) {
            Glide.with(this).load(baju.getGambar_url()).into(ivGambar);
        } else {
            ivGambar.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    private void deleteBaju(int id) {
        Call<ApiResponse<Void>> call = apiService.deleteBaju(id);
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(DetailBarangActivity.this, "Baju berhasil dihapus", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DetailBarangActivity.this, "Gagal menghapus baju.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Toast.makeText(DetailBarangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
