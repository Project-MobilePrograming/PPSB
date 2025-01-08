package com.dicoding.pp_stokbaju.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.dicoding.pp_stokbaju.R;
import com.dicoding.pp_stokbaju.adapter.BarangAdapter;
import com.dicoding.pp_stokbaju.model.Barang;

public class StokBarangActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCariBarang;
    private Button btnEdit, btnHapus;
    private BarangAdapter barangAdapter;
    private List<Barang> barangList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok_barang);

        // Inisialisasi view
        recyclerViewCariBarang = findViewById(R.id.recyclerViewCariBarang);
        btnEdit = findViewById(R.id.btnEdit);
        btnHapus = findViewById(R.id.btnHapus);

        // Inisialisasi data
        barangList = new ArrayList<>();
        initDummyData();

        // Setup RecyclerView
        barangAdapter = new BarangAdapter(barangList);
        recyclerViewCariBarang.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCariBarang.setAdapter(barangAdapter);

        // Tombol Edit
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StokBarangActivity.this, "Edit clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // Tombol Hapus
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StokBarangActivity.this, "Hapus clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDummyData() {
        barangList.add(new Barang("Barang 1", 10));
        barangList.add(new Barang("Barang 2", 15));
        barangList.add(new Barang("Barang 3", 20));
    }
}
