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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import com.dicoding.pp_stokbaju.R;

public class TambahBarangActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1; // Request code untuk memilih gambar

    // Deklarasi variabel view
    private ImageView ivGambar; // Gambar yang dipilih
    private Button btnPilihGambar, btnTambah, btnHapus; // Tombol untuk aksi
    private EditText etNamaBaju, etHarga, etStok;// Input teks: nama, harga, dan stok baju
    private Spinner spinnerJenisBaju, spinnerUkuranBaju; // Spinner untuk jenis dan ukuran baju
    private Uri imageUri; // URI untuk gambar yang dipilih

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang); // Menggunakan layout `activity_tambah_barang`

        // Menghubungkan variabel dengan ID di layout XML
        ivGambar = findViewById(R.id.ivGambar);
        btnPilihGambar = findViewById(R.id.btnPilihGambar);
        btnTambah = findViewById(R.id.btnTambah);
        btnHapus = findViewById(R.id.btnHapus);
        etNamaBaju = findViewById(R.id.etNamaBaju);
        spinnerJenisBaju = findViewById(R.id.spinnerJenisBaju);
        spinnerUkuranBaju = findViewById(R.id.spinnerUkuranBaju);
        etHarga = findViewById(R.id.etHarga);
        etStok = findViewById(R.id.etStok);

        // Listener tombol pilih gambar, memanggil fungsi pilihGambar()
        btnPilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihGambar();
            }
        });

        // Listener tombol tambah, memanggil fungsi tambahBarang()
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahBarang();
            }
        });

        // Listener tombol hapus, memanggil fungsi hapusInput()
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusInput();
            }
        });
    }

    // Fungsi untuk memilih gambar dari galeri
    private void pilihGambar() {
        Intent intent = new Intent();                  // Membuat intent baru
        intent.setType("image/*");                     // Membatasi hanya untuk memilih file gambar
        intent.setAction(Intent.ACTION_GET_CONTENT);   // Membuka intent untuk memilih file
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    // Mendapatkan hasil saat aktivitas memilih gambar selesai
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Menyimpan URI gambar yang dipilih
            imageUri = data.getData();
            try {
                // Mengubah URI menjadi Bitmap untuk ditampilkan di ImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ivGambar.setImageBitmap(bitmap); // Menampilkan gambar ke ImageView
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Gagal memuat gambar", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Fungsi untuk menambahkan barang
    private void tambahBarang() {
        // Mengambil nilai dari input yang diisi user
        String namaBaju = etNamaBaju.getText().toString().trim(); // Nama baju
        String jenisBaju = spinnerJenisBaju.getSelectedItem().toString(); // Jenis baju dari spinner
        String ukuranBaju = spinnerUkuranBaju.getSelectedItem().toString(); // Ukuran baju dari spinner
        String harga = etHarga.getText().toString().trim();      // Harga baju
        String stok = etStok.getText().toString().trim();        // Jumlah stok

        // Validasi input kosong atau gambar belum dipilih
        if (namaBaju.isEmpty() || harga.isEmpty() || stok.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
            return; // Menghentikan proses jika validasi gagal
        }

        // Contoh proses penyimpanan data, Anda dapat mengganti dengan logika lain (Firebase, SQLite, dll.)
        Toast.makeText(this, "Barang berhasil ditambahkan", Toast.LENGTH_SHORT).show();
        hapusInput(); // Membersihkan input setelah data ditambahkan
    }

    // Fungsi untuk menghapus semua input
    private void hapusInput() {
        // Reset input form menjadi kosong
        etNamaBaju.setText("");
        etHarga.setText("");
        etStok.setText("");
        spinnerJenisBaju.setSelection(0); // Mengatur spinner kembali ke posisi awal
        spinnerUkuranBaju.setSelection(0);
        ivGambar.setImageResource(android.R.color.transparent); // Menghapus gambar yang ditampilkan
        imageUri = null; // Reset URI gambar
    }
}
