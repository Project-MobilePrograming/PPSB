package com.dicoding.pp_stokbaju.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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

    private ImageView ivGambar;
    private TextView tvNamaBaju, tvJenisBaju, tvUkuranBaju, tvHarga, tvStok;
    private Button btnEdit, btnHapus;
    private Baju baju;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        // Inisialisasi view
        ivGambar = findViewById(R.id.ivGambar);
        tvNamaBaju = findViewById(R.id.tvNamaBaju);
        tvJenisBaju = findViewById(R.id.tvJenisBaju);
        tvUkuranBaju = findViewById(R.id.tvUkuranBaju);
        tvHarga = findViewById(R.id.tvHarga);
        tvStok = findViewById(R.id.tvStok);
        btnEdit = findViewById(R.id.btnEdit);
        btnHapus = findViewById(R.id.btnHapus);

        // Inisialisasi ApiService
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Ambil data baju dari intent
        baju = getIntent().getParcelableExtra("BAJU_DATA");
        if (baju != null) {
            // Tampilkan data baju
            tvNamaBaju.setText(baju.getNama_baju());
            tvJenisBaju.setText("Jenis: " + baju.getNama_jenis_baju());
            tvUkuranBaju.setText("Ukuran: " + baju.getUkuran_baju());
            tvHarga.setText("Harga: Rp " + baju.getHarga());
            tvStok.setText("Stok: " + baju.getStok());

            // Memuat gambar dari URL menggunakan Glide
            if (baju.getGambar_url() != null && !baju.getGambar_url().isEmpty()) {
                Glide.with(this)
                        .load(baju.getGambar_url())
                        .placeholder(R.drawable.ic_launcher_background) // Gambar placeholder jika loading
                        .error(R.drawable.ic_launcher_foreground) // Gambar jika error
                        .into(ivGambar);
            } else {
                ivGambar.setImageResource(R.drawable.ic_launcher_background); // Gambar default jika URL kosong
            }
        } else {
            Toast.makeText(this, "Data baju tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish(); // Tutup aktivitas jika data tidak valid
        }

        // Tombol Edit
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buka UpdateBarangActivity dengan mengirim data baju
                Intent intent = new Intent(DetailBarangActivity.this, UpdateBarangActivity.class);
                intent.putExtra("BAJU_DATA", baju); // Kirim data baju sebagai Parcelable
                startActivity(intent);
            }
        });

        // Tombol Hapus
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil fungsi hapus
                deleteBaju(baju.getId());
            }
        });
    }

    private void deleteBaju(int id) {
        Call<ApiResponse<Void>> call = apiService.deleteBaju(id);
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Void> apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        Toast.makeText(DetailBarangActivity.this, "Baju berhasil dihapus", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(DetailBarangActivity.this, "Gagal menghapus baju: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailBarangActivity.this, "Response tidak sukses", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Toast.makeText(DetailBarangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}