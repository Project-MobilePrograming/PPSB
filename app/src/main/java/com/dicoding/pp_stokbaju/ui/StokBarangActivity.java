package com.dicoding.pp_stokbaju.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dicoding.pp_stokbaju.R;
import com.dicoding.pp_stokbaju.adapter.BajuAdapter;
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
    private Button btnEdit, btnHapus;
    private BajuAdapter bajuAdapter;
    private List<Baju> bajuList;
    private ApiService apiService;
    private int selectedPosition = -1; // Untuk menyimpan posisi item yang dipilih

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok_barang);

        // Inisialisasi view
        recyclerViewStokBaju = findViewById(R.id.recyclerViewStokBaju);
        btnEdit = findViewById(R.id.btnEdit);
        btnHapus = findViewById(R.id.btnHapus);

        // Inisialisasi ApiService
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Inisialisasi data
        bajuList = new ArrayList<>();

        // Setup RecyclerView
        bajuAdapter = new BajuAdapter(bajuList, new BajuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Baju baju) {
                // Simpan posisi item yang dipilih
                selectedPosition = bajuList.indexOf(baju);
                Toast.makeText(StokBarangActivity.this, "Item clicked: " + baju.getNama_baju(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewStokBaju.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewStokBaju.setAdapter(bajuAdapter);

        // Ambil data baju dari API
        fetchDataBaju();

        // Tombol Edit
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cek apakah ada item yang dipilih
                if (selectedPosition != -1) {
                    Baju baju = bajuList.get(selectedPosition);

                    // Buka UpdateBarangActivity dengan mengirim data baju
                    Intent intent = new Intent(StokBarangActivity.this, UpdateBarangActivity.class);
                    intent.putExtra("BAJU_DATA", (Parcelable) baju); // Kirim data baju sebagai parcelable
                    startActivity(intent);
                } else {
                    Toast.makeText(StokBarangActivity.this, "Pilih baju yang ingin diubah", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Tombol Hapus
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cek apakah ada item yang dipilih
                if (selectedPosition != -1) {
                    Baju baju = bajuList.get(selectedPosition);
                    deleteBaju(baju.getId()); // Panggil fungsi hapus dengan ID baju
                } else {
                    Toast.makeText(StokBarangActivity.this, "Pilih baju yang ingin dihapus", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchDataBaju() {
        // Panggil API untuk mengambil data baju
        Call<List<Baju>> call = apiService.getAllBaju();
        call.enqueue(new Callback<List<Baju>>() {
            @Override
            public void onResponse(Call<List<Baju>> call, Response<List<Baju>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Update data di adapter
                    bajuList.clear();
                    bajuList.addAll(response.body());
                    bajuAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(StokBarangActivity.this, "Gagal mengambil data baju", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Baju>> call, Throwable t) {
                Toast.makeText(StokBarangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteBaju(int id) {
        // Panggil API untuk hapus baju
        Call<Void> call = apiService.deleteBaju(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(StokBarangActivity.this, "Baju berhasil dihapus", Toast.LENGTH_SHORT).show();
                    fetchDataBaju(); // Refresh data setelah menghapus
                    selectedPosition = -1; // Reset posisi yang dipilih
                } else {
                    Toast.makeText(StokBarangActivity.this, "Gagal menghapus baju", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(StokBarangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}