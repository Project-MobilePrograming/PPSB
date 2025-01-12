package com.dicoding.pp_stokbaju.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dicoding.pp_stokbaju.R;
import com.dicoding.pp_stokbaju.api.ApiService;
import com.dicoding.pp_stokbaju.api.RetrofitClient;
import com.dicoding.pp_stokbaju.api.ApiResponse;
import com.dicoding.pp_stokbaju.model.JenisBaju;
import com.dicoding.pp_stokbaju.model.UkuranBaju;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahBarangActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView ivGambar;
    private Button btnPilihGambar, btnTambah;
    private EditText etNamaBaju, etHarga, etStok;
    private Spinner spinnerJenisBaju, spinnerUkuranBaju;
    private Uri imageUri;

    private ApiService apiService;
    private List<JenisBaju> jenisBajuList;
    private List<UkuranBaju> ukuranBajuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);

        // Inisialisasi ApiService
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Inisialisasi view
        ivGambar = findViewById(R.id.ivGambar);
        btnPilihGambar = findViewById(R.id.btnPilihGambar);
        btnTambah = findViewById(R.id.btnTambah);
        etNamaBaju = findViewById(R.id.etNamaBaju);
        spinnerJenisBaju = findViewById(R.id.spinnerJenisBaju);
        spinnerUkuranBaju = findViewById(R.id.spinnerUkuranBaju);
        etHarga = findViewById(R.id.etHarga);
        etStok = findViewById(R.id.etStok);

        // Inisialisasi list untuk jenis dan ukuran baju
        jenisBajuList = new ArrayList<>();
        ukuranBajuList = new ArrayList<>();

        // Ambil data jenis baju dan ukuran baju dari API
        fetchJenisBaju();
        fetchUkuranBaju();

        // Listener tombol pilih gambar
        btnPilihGambar.setOnClickListener(v -> pilihGambar());

        // Listener tombol tambah
        btnTambah.setOnClickListener(v -> tambahBarang());
    }

    // Fungsi untuk mengambil data jenis baju dari API
    private void fetchJenisBaju() {
        Call<ApiResponse<List<JenisBaju>>> call = apiService.getAllJenisBaju();
        call.enqueue(new Callback<ApiResponse<List<JenisBaju>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<JenisBaju>>> call, Response<ApiResponse<List<JenisBaju>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    jenisBajuList = response.body().getData();
                    ArrayAdapter<JenisBaju> adapter = new ArrayAdapter<>(
                            TambahBarangActivity.this,
                            android.R.layout.simple_spinner_item,
                            jenisBajuList
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerJenisBaju.setAdapter(adapter);
                } else {
                    Toast.makeText(TambahBarangActivity.this, "Gagal mengambil data jenis baju", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<JenisBaju>>> call, Throwable t) {
                Toast.makeText(TambahBarangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Fungsi untuk mengambil data ukuran baju dari API
    private void fetchUkuranBaju() {
        Call<ApiResponse<List<UkuranBaju>>> call = apiService.getAllUkuranBaju();
        call.enqueue(new Callback<ApiResponse<List<UkuranBaju>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<UkuranBaju>>> call, Response<ApiResponse<List<UkuranBaju>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    ukuranBajuList = response.body().getData();
                    ArrayAdapter<UkuranBaju> adapter = new ArrayAdapter<>(
                            TambahBarangActivity.this,
                            android.R.layout.simple_spinner_item,
                            ukuranBajuList
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerUkuranBaju.setAdapter(adapter);
                } else {
                    Toast.makeText(TambahBarangActivity.this, "Gagal mengambil data ukuran baju", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<UkuranBaju>>> call, Throwable t) {
                Toast.makeText(TambahBarangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Fungsi untuk memilih gambar dari galeri
    private void pilihGambar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    // Mendapatkan hasil saat aktivitas memilih gambar selesai
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ivGambar.setImageURI(imageUri);
        }
    }

    // Fungsi untuk menambahkan barang
    private void tambahBarang() {
        String namaBaju = etNamaBaju.getText().toString().trim();
        JenisBaju jenisBaju = (JenisBaju) spinnerJenisBaju.getSelectedItem();
        UkuranBaju ukuranBaju = (UkuranBaju) spinnerUkuranBaju.getSelectedItem();
        String harga = etHarga.getText().toString().trim();
        String stok = etStok.getText().toString().trim();

        // Validasi input kosong
        if (namaBaju.isEmpty() || harga.isEmpty() || stok.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validasi harga dan stok harus angka
        try {
            double hargaBaju = Double.parseDouble(harga);
            int stokBaju = Integer.parseInt(stok);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Harga dan stok harus berupa angka", Toast.LENGTH_SHORT).show();
            return;
        }

        // Konversi Uri ke MultipartBody.Part
        File file = new File(getRealPathFromURI(imageUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        // Buat RequestBody untuk setiap field
        RequestBody namaBajuBody = RequestBody.create(MediaType.parse("text/plain"), namaBaju);
        RequestBody idJenisBajuBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(jenisBaju.getId()));
        RequestBody idUkuranBajuBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ukuranBaju.getId()));
        RequestBody hargaBody = RequestBody.create(MediaType.parse("text/plain"), harga);
        RequestBody stokBody = RequestBody.create(MediaType.parse("text/plain"), stok);

        // Panggil API untuk menambahkan baju
        Call<ApiResponse<Void>> call = apiService.createBaju(
                namaBajuBody,
                idJenisBajuBody,
                idUkuranBajuBody,
                hargaBody,
                stokBody,
                imagePart
        );
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(TambahBarangActivity.this, "Barang berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    clearInput();
                } else {
                    Toast.makeText(TambahBarangActivity.this, "Gagal menambahkan barang", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Toast.makeText(TambahBarangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Fungsi untuk membersihkan input
    private void clearInput() {
        etNamaBaju.setText("");
        etHarga.setText("");
        etStok.setText("");
        spinnerJenisBaju.setSelection(0);
        spinnerUkuranBaju.setSelection(0);
        ivGambar.setImageResource(android.R.color.transparent);
        imageUri = null;
    }

    // Fungsi untuk mendapatkan path real dari URI
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }
}