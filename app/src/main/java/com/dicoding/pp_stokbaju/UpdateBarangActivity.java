package com.dicoding.pp_stokbaju;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateBarangActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;

    private EditText etItemCode, etItemName, etStockQuantity;
    private ImageView ivItemImage;
    private Button btnSelectImage, btnUpdateStock;

    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_barang);

        // Initialize Views
        etItemCode = findViewById(R.id.etItemCode);
        etItemName = findViewById(R.id.etItemName);
        etStockQuantity = findViewById(R.id.etStockQuantity);
        ivItemImage = findViewById(R.id.ivItemImage);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnUpdateStock = findViewById(R.id.btnUpdateStock);

        // Set OnClickListener for Select Image Button
        btnSelectImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_IMAGE_PICK);
        });

        // Set OnClickListener for Update Stock Button
        btnUpdateStock.setOnClickListener(view -> updateStock());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            ivItemImage.setImageURI(selectedImageUri); // Display selected image
        }
    }

    private void updateStock() {
        String itemCode = etItemCode.getText().toString().trim();
        String itemName = etItemName.getText().toString().trim();
        String stockQuantity = etStockQuantity.getText().toString().trim();

        // Validate inputs
        if (itemCode.isEmpty() || itemName.isEmpty() || stockQuantity.isEmpty()) {
            Toast.makeText(this, "Harap lengkapi semua data!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedImageUri == null) {
            Toast.makeText(this, "Harap pilih gambar barang!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simulate stock update process
        Toast.makeText(this, "Stok barang berhasil diperbarui!", Toast.LENGTH_SHORT).show();

        // Reset inputs (optional)
        etItemCode.setText("");
        etItemName.setText("");
        etStockQuantity.setText("");
        ivItemImage.setImageResource(R.drawable.ic_launcher_background); // Reset to placeholder
        selectedImageUri = null;
    }
}