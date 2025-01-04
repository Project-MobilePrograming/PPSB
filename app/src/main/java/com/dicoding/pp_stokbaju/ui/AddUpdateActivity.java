package com.dicoding.pp_stokbaju.ui;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.dicoding.pp_stokbaju.R;

public class AddUpdateActivity extends AppCompatActivity {
    private EditText etNamaBaju, etIdJenisBaju, etIdUkuranBaju, etHarga, etStok;
    private Button btnPilihGambar, btnUpdate, btnTambah, btnHapus;
    private ImageView ivGambar;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri gambarUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);

        // Inisialisasi komponen
        etNamaBaju = findViewById(R.id.etNamaBaju);
        etIdJenisBaju = findViewById(R.id.etIdJenisBaju);
        etIdUkuranBaju = findViewById(R.id.etIdUkuranBaju);
        etHarga = findViewById(R.id.etHarga);
        etStok = findViewById(R.id.etStok);
        btnPilihGambar = findViewById(R.id.btnPilihGambar);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnTambah = findViewById(R.id.btnTambah);
        btnHapus = findViewById(R.id.btnHapus);
        ivGambar = findViewById(R.id.ivGambar);

        // Tombol Pilih Gambar
        btnPilihGambar.setOnClickListener(v -> pilihGambar());
    }

    private void pilihGambar() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            gambarUri = data.getData();
            ivGambar.setImageURI(gambarUri);
        }
    }
}