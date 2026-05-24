package com.example.cookbookapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class ShoppingActivity extends AppCompatActivity {

    private LinearLayout layoutExtraItems;
    private EditText etNewItem;
    private SharedPreferences prefs;

    private final String[] basicItems = {
            "Brasno", "Ulje", "Jaja", "Mlijeko", "Secer",
            "So", "Maslac", "Luk", "Bijeli luk", "Paradajz"
    };

    private final int[] checkboxIds = {
            R.id.cbBrasno, R.id.cbUlje, R.id.cbJaja, R.id.cbMlijeko,
            R.id.cbSecer, R.id.cbSo, R.id.cbMaslac, R.id.cbLuk,
            R.id.cbBijeliluk, R.id.cbParadajz
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        setContentView(R.layout.activity_shopping);

        prefs = getSharedPreferences("shopping_prefs", MODE_PRIVATE);
        layoutExtraItems = findViewById(R.id.layoutExtraItems);
        etNewItem = findViewById(R.id.etNewItem);

        // Ucitaj stanje checkboxova
        for (int i = 0; i < checkboxIds.length; i++) {
            CheckBox cb = findViewById(checkboxIds[i]);
            cb.setChecked(prefs.getBoolean(basicItems[i], false));
            final String itemName = basicItems[i];
            cb.setOnCheckedChangeListener((buttonView, isChecked) ->
                    prefs.edit().putBoolean(itemName, isChecked).apply());
        }

        // Ucitaj dodane stavke
        int extraCount = prefs.getInt("extra_count", 0);
        for (int i = 0; i < extraCount; i++) {
            String text = prefs.getString("extra_" + i, "");
            boolean checked = prefs.getBoolean("extra_checked_" + i, false);
            if (!text.isEmpty()) addExtraItem(text, checked, i);
        }

        // Back dugme
        findViewById(R.id.btnBackShopping).setOnClickListener(v -> finish());

        // Dodaj stavku
        findViewById(R.id.btnAddItem).setOnClickListener(v -> {
            String text = etNewItem.getText().toString().trim();
            if (!text.isEmpty()) {
                int index = prefs.getInt("extra_count", 0);
                prefs.edit()
                        .putString("extra_" + index, text)
                        .putBoolean("extra_checked_" + index, false)
                        .putInt("extra_count", index + 1)
                        .apply();
                addExtraItem(text, false, index);
                etNewItem.setText("");
            }
        });

        // Obrisi sve
        findViewById(R.id.btnClearAll).setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            for (String item : basicItems) {
                editor.putBoolean(item, false);
            }
            int count = prefs.getInt("extra_count", 0);
            for (int i = 0; i < count; i++) {
                editor.remove("extra_" + i);
                editor.remove("extra_checked_" + i);
            }
            editor.putInt("extra_count", 0).apply();

            for (int id : checkboxIds) {
                ((CheckBox) findViewById(id)).setChecked(false);
            }
            layoutExtraItems.removeAllViews();
            etNewItem.setText("");
        });
    }

    private void addExtraItem(String text, boolean checked, int index) {
        CheckBox checkBox = new CheckBox(this);
        checkBox.setText(text);
        checkBox.setChecked(checked);
        checkBox.setTextColor(0xFFFFFFFF);
        checkBox.setTextSize(15f);
        checkBox.setButtonTintList(android.content.res.ColorStateList.valueOf(0xFFE94560));
        checkBox.setBackgroundColor(0xFF16213E);
        checkBox.setPadding(40, 0, 16, 0);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 120
        );
        params.setMargins(0, 0, 0, 4);
        checkBox.setLayoutParams(params);

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) ->
                prefs.edit().putBoolean("extra_checked_" + index, isChecked).apply());

        layoutExtraItems.addView(checkBox);
    }
}