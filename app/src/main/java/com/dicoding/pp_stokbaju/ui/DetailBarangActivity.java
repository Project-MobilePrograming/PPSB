package com.dicoding.pp_stokbaju.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
            displayBajuDetails(baju); // Tampilkan detail baju
        } else {
            Toast.makeText(this, "Data baju tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish(); // Tutup activity jika data tidak ditemukan
        }

        // Handle tombol edit
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(DetailBarangActivity.this, UpdateBarangActivity.class);
            intent.putExtra("BAJU_DATA", baju);
            startActivityForResult(intent, UPDATE_REQUEST_CODE);
        });

        // Handle tombol hapus
        btnHapus.setOnClickListener(v -> deleteBaju(baju.getId()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_REQUEST_CODE && resultCode == RESULT_OK) {
            fetchBajuDetails(baju.getId()); // Ambil data terbaru setelah update
        }
    }

    // Method untuk mengambil detail baju dari API
    private void fetchBajuDetails(int id) {
        Call<ApiResponse<Baju>> call = apiService.getBajuById(id);
        call.enqueue(new Callback<ApiResponse<Baju>>() {
            @Override
            public void onResponse(Call<ApiResponse<Baju>> call, Response<ApiResponse<Baju>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    baju = response.body().getData(); // Update data baju
                    displayBajuDetails(baju); // Tampilkan data terbaru
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

    // Method untuk menampilkan detail baju
    private void displayBajuDetails(Baju baju) {
        tvNamaBaju.setText(baju.getNama_baju());
        tvJenisBaju.setText("Jenis: " + baju.getNama_jenis_baju());
        tvUkuranBaju.setText("Ukuran: " + baju.getUkuran_baju());
        tvHarga.setText("Harga: Rp " + baju.getHarga());
        tvStok.setText("Stok: " + baju.getStok());

        // Gunakan URL lengkap yang sudah diterima dari BajuAdapter
        String imageUrl = baju.getGambar_url();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background) // Gambar placeholder
                    .error(R.drawable.ic_edit) // Gambar jika gagal dimuat
                    .into(ivGambar);
        } else {
            ivGambar.setImageResource(R.drawable.ic_launcher_background); // Gambar default
        }
    }

    // Method untuk menghapus baju
    private void deleteBaju(int id) {
        // Tampilkan dialog konfirmasi
        new AlertDialog.Builder(this)
                .setTitle("Hapus Baju")
                .setMessage("Apakah Anda yakin ingin menghapus baju ini?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    // Panggil API untuk menghapus baju
                    Call<ApiResponse<Void>> call = apiService.deleteBaju(id);
                    Log.d("DeleteBaju", "URL: " + call.request().url()); // Log URL yang dikirim
                    call.enqueue(new Callback<ApiResponse<Void>>() {
                        @Override
                        public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                            if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                                Toast.makeText(DetailBarangActivity.this, "Baju berhasil dihapus", Toast.LENGTH_SHORT).show();
                                finish(); // Tutup activity setelah berhasil menghapus
                            } else {
                                // Log error jika respons tidak berhasil atau body null
                                if (!response.isSuccessful()) {
                                    Log.e("DeleteBaju", "Gagal menghapus baju. Kode respons: " + response.code());
                                    Log.e("DeleteBaju", "Pesan error: " + response.message());
                                } else if (response.body() == null) {
                                    Log.e("DeleteBaju", "Respons body null");
                                } else if (!response.body().isSuccess()) {
                                    Log.e("DeleteBaju", "Respons body tidak sukses. Pesan: " + response.body().getMessage());
                                }
                                Toast.makeText(DetailBarangActivity.this, "Gagal menghapus baju.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                            // Log error jika terjadi kegagalan jaringan atau lainnya
                            Log.e("DeleteBaju", "Error: " + t.getMessage(), t);
                            Toast.makeText(DetailBarangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Tidak", null)
                .show();
    }
}