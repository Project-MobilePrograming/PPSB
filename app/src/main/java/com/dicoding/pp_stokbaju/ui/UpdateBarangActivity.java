package com.dicoding.pp_stokbaju.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.dicoding.pp_stokbaju.R;
import com.dicoding.pp_stokbaju.api.ApiResponse;
import com.dicoding.pp_stokbaju.api.ApiService;
import com.dicoding.pp_stokbaju.api.RetrofitClient;
import com.dicoding.pp_stokbaju.model.Baju;
import com.dicoding.pp_stokbaju.model.JenisBaju;
import com.dicoding.pp_stokbaju.model.UkuranBaju;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
    private String existingImageUrl;

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
            etNamaBaju.setText(baju.getNama_baju());
            etHarga.setText(String.valueOf(baju.getHarga()));
            etStok.setText(String.valueOf(baju.getStok()));
            existingImageUrl = baju.getGambar_url();
            loadImage(existingImageUrl);
        } else {
            Toast.makeText(this, "Data baju tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
        }

        fetchJenisBaju();
        fetchUkuranBaju();

        btnPilihGambar.setOnClickListener(v -> openImageChooser());
        btnUpdate.setOnClickListener(v -> {
            if (validateInput()) {
                updateBaju();
            }
        });
        btnHapus.setOnClickListener(v -> deleteBaju(baju.getId()));
    }

    // Metode untuk mengatur spinner JenisBaju
    private void setSpinnerSelection(Spinner spinner, List<?> list, int targetId) {
        for (int i = 0; i < list.size(); i++) {
            Object item = list.get(i);
            if (item instanceof JenisBaju) {
                if (((JenisBaju) item).getId() == targetId) {
                    spinner.setSelection(i);
                    break;
                }
            } else if (item instanceof UkuranBaju) {
                if (((UkuranBaju) item).getId() == targetId) {
                    spinner.setSelection(i);
                    break;
                }
            }
        }
    }

    private void loadImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_edit)
                    .into(ivGambar);
        } else {
            ivGambar.setImageResource(R.drawable.ic_delete);
        }
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
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ivGambar.setImageURI(imageUri);
        }
    }

    private String getFilePathFromUri(Uri uri) {
        String filePath = null;
        if (uri != null) {
            try {
                // Buat file sementara di cache direktori
                File file = new File(getCacheDir(), "temp_image.jpg");
                try (InputStream inputStream = getContentResolver().openInputStream(uri);
                     FileOutputStream outputStream = new FileOutputStream(file)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                }
                filePath = file.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

    private void fetchJenisBaju() {
        Call<ApiResponse<List<JenisBaju>>> call = apiService.getAllJenisBaju();
        call.enqueue(new Callback<ApiResponse<List<JenisBaju>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<JenisBaju>>> call, Response<ApiResponse<List<JenisBaju>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<JenisBaju> jenisBajuList = response.body().getData();
                    ArrayAdapter<JenisBaju> adapter = new ArrayAdapter<>(UpdateBarangActivity.this, android.R.layout.simple_spinner_item, jenisBajuList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerJenisBaju.setAdapter(adapter);

                    // Set spinner ke nilai yang sesuai dengan data baju
                    setSpinnerSelection(spinnerJenisBaju, jenisBajuList, baju.getId_jenis_baju());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<JenisBaju>>> call, Throwable t) {
                Toast.makeText(UpdateBarangActivity.this, "Gagal memuat jenis baju", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUkuranBaju() {
        Call<ApiResponse<List<UkuranBaju>>> call = apiService.getAllUkuranBaju();
        call.enqueue(new Callback<ApiResponse<List<UkuranBaju>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<UkuranBaju>>> call, Response<ApiResponse<List<UkuranBaju>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<UkuranBaju> ukuranBajuList = response.body().getData();
                    ArrayAdapter<UkuranBaju> adapter = new ArrayAdapter<>(UpdateBarangActivity.this, android.R.layout.simple_spinner_item, ukuranBajuList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerUkuranBaju.setAdapter(adapter);

                    // Set ukuran baju saat ini
                    for (int i = 0; i < ukuranBajuList.size(); i++) {
                        if (ukuranBajuList.get(i).getId() == baju.getId_ukuran_baju()) {
                            spinnerUkuranBaju.setSelection(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<UkuranBaju>>> call, Throwable t) {
                Toast.makeText(UpdateBarangActivity.this, "Gagal memuat ukuran baju", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateBaju() {
        String namaBaju = etNamaBaju.getText().toString();
        double harga = Double.parseDouble(etHarga.getText().toString());
        int stok = Integer.parseInt(etStok.getText().toString());
        int jenisBajuId = ((JenisBaju) spinnerJenisBaju.getSelectedItem()).getId();
        int ukuranBajuId = ((UkuranBaju) spinnerUkuranBaju.getSelectedItem()).getId();

        // Buat RequestBody untuk setiap field
        RequestBody idBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(baju.getId()));
        RequestBody namaBajuBody = RequestBody.create(MediaType.parse("text/plain"), namaBaju);
        RequestBody idJenisBaju = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(jenisBajuId));
        RequestBody idUkuranBaju = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ukuranBajuId));
        RequestBody hargaBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(harga));
        RequestBody stokBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(stok));

        // Jika ada gambar baru, buat MultipartBody.Part
        MultipartBody.Part gambarPart = null;
        if (imageUri != null) {
            File file = new File(getFilePathFromUri(imageUri));
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            gambarPart = MultipartBody.Part.createFormData("gambar_url", file.getName(), requestFile);
        }

        // Panggil API
        Call<ApiResponse<Baju>> call = apiService.updateBaju(
                idBody, namaBajuBody, idJenisBaju, idUkuranBaju, hargaBody, stokBody, gambarPart
        );

        call.enqueue(new Callback<ApiResponse<Baju>>() {
            @Override
            public void onResponse(Call<ApiResponse<Baju>> call, Response<ApiResponse<Baju>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(UpdateBarangActivity.this, "Baju berhasil diupdate", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateBarangActivity.this, StokBarangActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorMessage = response.errorBody().string();
                            Log.e("UpdateBarangActivity", "Error response: " + errorMessage);
                            Toast.makeText(UpdateBarangActivity.this, "Gagal mengupdate baju: " + errorMessage, Toast.LENGTH_LONG).show();
                        } else {
                            Log.e("UpdateBarangActivity", "Error response: null");
                            Toast.makeText(UpdateBarangActivity.this, "Gagal mengupdate baju, response error null.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("UpdateBarangActivity", "Error parsing response: " + e.getMessage());
                        Toast.makeText(UpdateBarangActivity.this, "Gagal membaca error dari server.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Baju>> call, Throwable t) {
                Log.e("UpdateBarangActivity", "onFailure: " + t.getMessage());
                Toast.makeText(UpdateBarangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteBaju(int id) {
        Call<ApiResponse<Void>> call = apiService.deleteBaju(id);
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(UpdateBarangActivity.this, "Baju berhasil dihapus", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateBarangActivity.this, StokBarangActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
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

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }
}