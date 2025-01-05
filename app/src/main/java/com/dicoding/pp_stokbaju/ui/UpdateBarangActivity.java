package com.dicoding.pp_stokbaju.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import com.dicoding.pp_stokbaju.R;

public class UpdateBarangActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView ivGambar;
    private Button btnPilihGambar, btnUpdate, btnHapus;
    private EditText etNamaBaju, etIdJenisBaju, etIdUkuranBaju, etHarga, etStok;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_barang);

        // Inisialisasi view
        ivGambar = findViewById(R.id.ivGambar);
        btnPilihGambar = findViewById(R.id.btnPilihGambar);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnHapus = findViewById(R.id.btnHapus);
        etNamaBaju = findViewById(R.id.etNamaBaju);
        etIdJenisBaju = findViewById(R.id.etIdJenisBaju);
        etIdUkuranBaju = findViewById(R.id.etIdUkuranBaju);
        etHarga = findViewById(R.id.etHarga);
        etStok = findViewById(R.id.etStok);

        // Pilih gambar dari galeri
        btnPilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihGambar();
            }
        });

        // Update barang
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBarang();
            }
        });

        // Hapus barang
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusBarang();
            }
        });

        // Dummy: Load data barang yang ada (sesuaikan dengan kebutuhan aplikasi)
        loadBarang();
    }

    private void pilihGambar() {
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
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ivGambar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateBarang() {
        String namaBaju = etNamaBaju.getText().toString().trim();
        String idJenisBaju = etIdJenisBaju.getText().toString().trim();
        String idUkuranBaju = etIdUkuranBaju.getText().toString().trim();
        String harga = etHarga.getText().toString().trim();
        String stok = etStok.getText().toString().trim();

        if (namaBaju.isEmpty() || idJenisBaju.isEmpty() || idUkuranBaju.isEmpty() || harga.isEmpty() || stok.isEmpty()) {
            Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proses update data barang (dummy action)
        Toast.makeText(this, "Barang berhasil diperbarui", Toast.LENGTH_SHORT).show();
    }

    private void hapusBarang() {
        // Proses hapus data barang (dummy action)
        Toast.makeText(this, "Barang berhasil dihapus", Toast.LENGTH_SHORT).show();
        finish(); // Kembali ke layar sebelumnya
    }

    private void loadBarang() {
        // Dummy data untuk simulasi (sesuaikan dengan data barang nyata dari database)
        etNamaBaju.setText("Contoh Baju");
        etIdJenisBaju.setText("1");
        etIdUkuranBaju.setText("2");
        etHarga.setText("50000");
        etStok.setText("10");
        ivGambar.setImageResource(R.drawable.ic_launcher_foreground); // Sesuaikan dengan gambar dummy
    }
}

