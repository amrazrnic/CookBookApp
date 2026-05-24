package com.example.cookbookapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etProfileName, etChangeEmail, etNewPassword;
    private TextView tvProfileName, tvProfileEmail, tvProfileEmailField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        etProfileName = findViewById(R.id.etProfileName);
        etChangeEmail = findViewById(R.id.etChangeEmail);
        etNewPassword = findViewById(R.id.etNewPassword);
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        tvProfileEmailField = findViewById(R.id.tvProfileEmailField);

        // Popuni podatke korisnika
        if (user != null) {
            String email = user.getEmail();
            String name = email.substring(0, email.indexOf("@"));
            tvProfileName.setText(name);
            tvProfileEmail.setText(email);
            tvProfileEmailField.setText(email);
            etProfileName.setText(name);
            etChangeEmail.setText(email);
        }

        // Back dugme
        findViewById(R.id.btnBackProfile).setOnClickListener(v -> finish());

        // Sacuvaj ime
        findViewById(R.id.btnSaveProfile).setOnClickListener(v -> {
            String newName = etProfileName.getText().toString().trim();
            if (newName.isEmpty()) {
                Toast.makeText(this, "Unesite ime.", Toast.LENGTH_SHORT).show();
                return;
            }
            tvProfileName.setText(newName);
            Toast.makeText(this, "Profil azuriran!", Toast.LENGTH_SHORT).show();
        });

        // Promjena lozinke
        findViewById(R.id.btnChangePassword).setOnClickListener(v -> {
            String email = etChangeEmail.getText().toString().trim();
            String newPassword = etNewPassword.getText().toString().trim();

            if (email.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Unesite email i novu lozinku.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newPassword.length() < 6) {
                Toast.makeText(this, "Lozinka mora imati minimum 6 znakova.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (user != null) {
                user.updatePassword(newPassword)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(this, "Lozinka uspjesno promijenjena!", Toast.LENGTH_SHORT).show();
                            etNewPassword.setText("");
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(this, "Greska. Pokusaj ponovo.", Toast.LENGTH_SHORT).show());
            }
        });

        // Postavke
        findViewById(R.id.btnGoSettings).setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity.class)));

        // Odjava
        findViewById(R.id.btnLogoutRow).setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}