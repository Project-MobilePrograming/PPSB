package com.dicoding.pp_stokbaju.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dicoding.pp_stokbaju.R;

public class UpdateBarangActivity extends AppCompatActivity {

    private Spinner spinnerJenisBaju, spinnerUkuranBaju;
    private EditText etNamaBaju, etHarga, etStok;
    private Button btnUpdate, btnHapus;

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

        // Set data dummy untuk Spinner
        ArrayAdapter<String> adapterJenisBaju = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Kaos", "Kemeja", "Hoodie"});
        adapterJenisBaju.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJenisBaju.setAdapter(adapterJenisBaju);

        ArrayAdapter<String> adapterUkuranBaju = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"S", "M", "L"});
        adapterUkuranBaju.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUkuranBaju.setAdapter(adapterUkuranBaju);

        // Tombol Tambah
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaBaju = etNamaBaju.getText().toString();
                String jenisBaju = spinnerJenisBaju.getSelectedItem().toString();
                String ukuranBaju = spinnerUkuranBaju.getSelectedItem().toString();
                String harga = etHarga.getText().toString();
                String stok = etStok.getText().toString();

                Toast.makeText(UpdateBarangActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            }
        });

        // Tombol Hapus
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateBarangActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
            }
        });
    }
}