package com.example.cookbookapp;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        setContentView(R.layout.activity_settings);

        findViewById(R.id.btnBackSettings).setOnClickListener(v -> finish());

        Switch switchDarkMode = findViewById(R.id.switchDarkMode);
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, "Tamna tema uključena", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Tamna tema isključena", Toast.LENGTH_SHORT).show();
            }
        });

        Switch switchNotifications = findViewById(R.id.switchNotifications);
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, "Obavijesti uključene", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Obavijesti isključene", Toast.LENGTH_SHORT).show();
            }
        });


    }
}