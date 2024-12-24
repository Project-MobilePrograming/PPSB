package com.dicoding.pp_stokbaju;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inisialisasi komponen dari layout XML
        ImageView imageView = findViewById(R.id.imageView);
        EditText emailField = findViewById(R.id.emailField);
        EditText passwordField = findViewById(R.id.passwordField);
        Button loginButton = findViewById(R.id.loginButton);

        // Listener untuk tombol login
        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Email dan Password harus diisi!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "Login berhasil untuk: " + email, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
