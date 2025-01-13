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
import com.dicoding.pp_stokbaju.api.ApiResponse;
import com.dicoding.pp_stokbaju.api.ApiService;
import com.dicoding.pp_stokbaju.api.RetrofitClient;
import com.dicoding.pp_stokbaju.model.Baju;
import com.dicoding.pp_stokbaju.model.JenisBaju;
import com.dicoding.pp_stokbaju.model.UkuranBaju;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateBarangActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Spinner spinnerJenisBaju, spinnerUkuranBaju;
    private EditText etNamaBaju, etHarga, etStok;
    private Button btnUpdate, btnHapus, btnPilihGambar;
    private ImageView ivGambar;
    private ApiService apiService;
    private Baju baju;
    private Uri imageUri;
    private MultipartBody.Part imagePart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_barang);

        // Inisialisasi view
        spinnerJenisBaju = findViewById(R.id.spinnerJenisBaju);
        spinnerUkuranBaju = findViewById(R.id.spinnerUkuranBaju);
        etNamaBaju = findViewById(R.id.etNamaBaju);
        etHarga = findViewById(R.id.etHarga);
        etStok = findViewById(R.id.etStok);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnHapus = findViewById(R.id.btnHapus);
        btnPilihGambar = findViewById(R.id.btnPilihGambar);
        ivGambar = findViewById(R.id.ivGambar);

        // Inisialisasi ApiService
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Ambil data baju dari intent
        baju = getIntent().getParcelableExtra("BAJU_DATA");
        if (baju != null) {
            // Isi form dengan data baju
            etNamaBaju.setText(baju.getNama_baju());
            etHarga.setText(String.valueOf(baju.getHarga()));
            etStok.setText(String.valueOf(baju.getStok()));
        } else {
            Toast.makeText(this, "Data baju tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish(); // Tutup aktivitas jika data tidak valid
        }

        // Ambil data jenis baju dan ukuran baju dari API
        fetchJenisBaju();
        fetchUkuranBaju();

        // Tombol Pilih Gambar
        btnPilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        // Tombol Update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    updateBaju(baju.getId()); // Panggil fungsi update dengan ID baju
                }
            }
        });

        // Tombol Hapus
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBaju(baju.getId()); // Panggil fungsi hapus dengan ID baju
            }
        });
    }
    private boolean validateInput() {
        if (etNamaBaju.getText().toString().isEmpty()) {
            Toast.makeText(this, "Nama baju tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etHarga.getText().toString().isEmpty()) {
            Toast.makeText(this, "Harga tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etStok.getText().toString().isEmpty()) {
            Toast.makeText(this, "Stok tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ivGambar.setImageURI(imageUri);

            // Konversi Uri ke MultipartBody.Part
            String realPath = getRealPathFromURI(imageUri);
            if (realPath != null) {
                File file = new File(realPath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
            } else {
                Toast.makeText(this, "Gagal memuat gambar", Toast.LENGTH_SHORT).show();
            }
        }
    }
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
    private void fetchJenisBaju() {
        Call<ApiResponse<List<JenisBaju>>> call = apiService.getAllJenisBaju();
        call.enqueue(new Callback<ApiResponse<List<JenisBaju>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<JenisBaju>>> call, Response<ApiResponse<List<JenisBaju>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<JenisBaju>> apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        List<JenisBaju> jenisBajuList = apiResponse.getData();

                        // Periksa apakah data tidak null
                        if (jenisBajuList != null && !jenisBajuList.isEmpty()) {
                            ArrayAdapter<JenisBaju> adapter = new ArrayAdapter<>(
                                    UpdateBarangActivity.this,
                                    android.R.layout.simple_spinner_item,
                                    jenisBajuList
                            );
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerJenisBaju.setAdapter(adapter);

                            // Set selected item berdasarkan data baju yang sedang diupdate
                            if (baju != null) {
                                for (int i = 0; i < jenisBajuList.size(); i++) {
                                    if (jenisBajuList.get(i).getId() == baju.getId_jenis_baju()) {
                                        spinnerJenisBaju.setSelection(i);
                                        break;
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(UpdateBarangActivity.this, "Data jenis baju kosong", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UpdateBarangActivity.this, "Gagal mengambil data jenis baju: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UpdateBarangActivity.this, "Response tidak sukses", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<JenisBaju>>> call, Throwable t) {
                Toast.makeText(UpdateBarangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void fetchUkuranBaju() {
        Call<ApiResponse<List<UkuranBaju>>> call = apiService.getAllUkuranBaju();
        call.enqueue(new Callback<ApiResponse<List<UkuranBaju>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<UkuranBaju>>> call, Response<ApiResponse<List<UkuranBaju>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<UkuranBaju>> apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        List<UkuranBaju> ukuranBajuList = apiResponse.getData();

                        // Periksa apakah data tidak null
                        if (ukuranBajuList != null && !ukuranBajuList.isEmpty()) {
                            ArrayAdapter<UkuranBaju> adapter = new ArrayAdapter<>(
                                    UpdateBarangActivity.this,
                                    android.R.layout.simple_spinner_item,
                                    ukuranBajuList
                            );
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerUkuranBaju.setAdapter(adapter);

                            // Set selected item berdasarkan data baju yang sedang diupdate
                            if (baju != null) {
                                for (int i = 0; i < ukuranBajuList.size(); i++) {
                                    if (ukuranBajuList.get(i).getId() == baju.getId_ukuran_baju()) {
                                        spinnerUkuranBaju.setSelection(i);
                                        break;
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(UpdateBarangActivity.this, "Data ukuran baju kosong", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UpdateBarangActivity.this, "Gagal mengambil data ukuran baju: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UpdateBarangActivity.this, "Response tidak sukses", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<UkuranBaju>>> call, Throwable t) {
                Toast.makeText(UpdateBarangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateBaju(int id) {
        try {
            String namaBaju = etNamaBaju.getText().toString();
            double harga = Double.parseDouble(etHarga.getText().toString());
            int stok = Integer.parseInt(etStok.getText().toString());

            // Buat RequestBody untuk setiap field
            RequestBody namaBajuBody = RequestBody.create(MediaType.parse("text/plain"), namaBaju);
            RequestBody hargaBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(harga));
            RequestBody stokBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(stok));

            // Panggil API untuk update baju
            Call<ApiResponse<Baju>> call = apiService.updateBaju(
                    id,
                    namaBajuBody,
                    hargaBody,
                    stokBody,
                    imagePart// Gunakan imagePart jika ada gambar baru
            );
            call.enqueue(new Callback<ApiResponse<Baju>>() {
                @Override
                public void onResponse(Call<ApiResponse<Baju>> call, Response<ApiResponse<Baju>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        Toast.makeText(UpdateBarangActivity.this, "Baju berhasil diupdate", Toast.LENGTH_SHORT).show();
                        finish(); // Tutup aktivitas setelah berhasil update
                    } else {
                        Toast.makeText(UpdateBarangActivity.this, "Gagal mengupdate baju", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<Baju>> call, Throwable t) {
                    Toast.makeText(UpdateBarangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Harga dan stok harus berupa angka", Toast.LENGTH_SHORT).show();
        }
    }
    private void deleteBaju(int id) {
        Call<ApiResponse<Void>> call = apiService.deleteBaju(id);
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(UpdateBarangActivity.this, "Baju berhasil dihapus", Toast.LENGTH_SHORT).show();
                    finish(); // Tutup aktivitas setelah berhasil hapus
                } else {
                    Toast.makeText(UpdateBarangActivity.this, "Gagal menghapus baju", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Toast.makeText(UpdateBarangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}