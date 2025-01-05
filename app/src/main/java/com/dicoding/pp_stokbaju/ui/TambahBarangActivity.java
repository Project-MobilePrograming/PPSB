package com.dicoding.pp_stokbaju.ui;

import android.os.Bundle;

import android.content.Intent;
import androidx.activity.EdgeToEdge;
import android.graphics.Bitmap;
import android.net.Uri;
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

public class TambahBarangActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView ivGambar;
    private Button btnPilihGambar, btnTambah, btnHapus;
    private EditText etNamaBaju, etIdJenisBaju, etIdUkuranBaju, etHarga, etStok;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tambah_barang);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        // Inisialisasi view
        ivGambar = findViewById(R.id.ivGambar);
        btnPilihGambar = findViewById(R.id.btnPilihGambar);
        btnTambah = findViewById(R.id.btnTambah);
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

        // Tambah barang
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahBarang();
            }
        });

        // Hapus semua input
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusInput();
            }
        });
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

    private void tambahBarang() {
        String namaBaju = etNamaBaju.getText().toString().trim();
        String idJenisBaju = etIdJenisBaju.getText().toString().trim();
        String idUkuranBaju = etIdUkuranBaju.getText().toString().trim();
        String harga = etHarga.getText().toString().trim();
        String stok = etStok.getText().toString().trim();

        if (namaBaju.isEmpty() || idJenisBaju.isEmpty() || idUkuranBaju.isEmpty() || harga.isEmpty() || stok.isEmpty()) {
            Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proses simpan data (dummy action)
        Toast.makeText(this, "Barang berhasil ditambahkan", Toast.LENGTH_SHORT).show();
        hapusInput();
    }

    private void hapusInput() {
        etNamaBaju.setText("");
        etIdJenisBaju.setText("");
        etIdUkuranBaju.setText("");
        etHarga.setText("");
        etStok.setText("");
        ivGambar.setImageResource(android.R.color.transparent);
    }
}