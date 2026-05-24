package com.example.cookbookapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText etLoginEmail, etLoginPassword;
    private EditText etRegisterName, etRegisterEmail, etRegisterPassword;
    private LinearLayout layoutLogin, layoutRegister;
    private Button btnTabLogin, btnTabRegister;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // Ako je korisnik već ulogovan, idi na Home
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        // Inicijalizuj view-ove
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        etRegisterName = findViewById(R.id.etRegisterName);
        etRegisterEmail = findViewById(R.id.etRegisterEmail);
        etRegisterPassword = findViewById(R.id.etRegisterPassword);
        layoutLogin = findViewById(R.id.layoutLogin);
        layoutRegister = findViewById(R.id.layoutRegister);
        btnTabLogin = findViewById(R.id.btnTabLogin);
        btnTabRegister = findViewById(R.id.btnTabRegister);
        tvError = findViewById(R.id.tvError);

        // Tab switcher
        btnTabLogin.setOnClickListener(v -> showLogin());
        btnTabRegister.setOnClickListener(v -> showRegister());

        // Login dugme
        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            String email = etLoginEmail.getText().toString().trim();
            String password = etLoginPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                showError("Unesite email i lozinku.");
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(result -> {
                        startActivity(new Intent(this, HomeActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e -> showError("Pogrešan email ili lozinka."));
        });

        // Registracija dugme
        findViewById(R.id.btnRegister).setOnClickListener(v -> {
            String name = etRegisterName.getText().toString().trim();
            String email = etRegisterEmail.getText().toString().trim();
            String password = etRegisterPassword.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showError("Popunite sva polja.");
                return;
            }

            if (password.length() < 6) {
                showError("Lozinka mora imati minimum 6 znakova.");
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(result -> {
                        // Sacuvaj ime u Firebase profil
                        com.google.firebase.auth.UserProfileChangeRequest profileUpdate =
                                new com.google.firebase.auth.UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();
                        result.getUser().updateProfile(profileUpdate);
                        startActivity(new Intent(this, HomeActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e -> showError("Registracija nije uspjela. Pokušaj ponovo."));
        });
    }

    private void showLogin() {
        layoutLogin.setVisibility(View.VISIBLE);
        layoutRegister.setVisibility(View.GONE);
        btnTabLogin.setBackgroundColor(0xFFE94560);
        btnTabLogin.setTextColor(0xFFFFFFFF);
        btnTabRegister.setBackgroundColor(0x00000000);
        btnTabRegister.setTextColor(0xFFA0A0B0);
        hideError();
    }

    private void showRegister() {
        layoutLogin.setVisibility(View.GONE);
        layoutRegister.setVisibility(View.VISIBLE);
        btnTabRegister.setBackgroundColor(0xFFE94560);
        btnTabRegister.setTextColor(0xFFFFFFFF);
        btnTabLogin.setBackgroundColor(0x00000000);
        btnTabLogin.setTextColor(0xFFA0A0B0);
        hideError();
    }

    private void showError(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        tvError.setVisibility(View.GONE);
    }
}
