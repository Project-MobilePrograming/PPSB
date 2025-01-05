package com.dicoding.pp_stokbaju.ui;

import android.os.Bundle;

import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;

import com.dicoding.pp_stokbaju.R;

public class MainActivity extends AppCompatActivity {
    private Button btnTambahBaju, btnStokBaju, btnSearchBaju;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        // Inisialisasi tombol
        btnTambahBaju = findViewById(R.id.btnTambahBaju);
        btnStokBaju = findViewById(R.id.btnStokBaju);
        btnSearchBaju = findViewById(R.id.btnSearchBaju);

        // Set OnClickListener untuk tombol Tambah Barang
        btnTambahBaju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TambahBarangActivity.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener untuk tombol List Stok Baju
        btnStokBaju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StokBarangActivity.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener untuk tombol Search Stok Baju
        btnSearchBaju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CariBarangActivity.class);
                startActivity(intent);
            }
        });
    }
}