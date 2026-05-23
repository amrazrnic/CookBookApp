package com.example.cookbookapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecipeAdapter adapter;
    private List<Recipe> allRecipes = new ArrayList<>();
    private List<Recipe> filteredRecipes = new ArrayList<>();
    private String selectedCategory = "Sve";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        setContentView(R.layout.activity_home);

        // Pozdrav sa imenom korisnika
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        TextView tvGreeting = findViewById(R.id.tvGreeting);
        if (user != null && user.getEmail() != null) {
            String email = user.getEmail();
            String name = email.substring(0, email.indexOf("@"));
            tvGreeting.setText("Zdravo, " + name + " 👋");
        }

        // Učitaj sample recepte
        loadSampleRecipes();

        // RecyclerView
        RecyclerView recycler = findViewById(R.id.recyclerRecipes);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipeAdapter(filteredRecipes);
        recycler.setAdapter(adapter);

        // Kategorije
        setupCategories();

        // Pretraga
        EditText etSearch = findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterRecipes(s.toString(), selectedCategory);
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Navigation
        findViewById(R.id.btnNavProfile).setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));

        findViewById(R.id.tvProfileBtn).setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));
    }

    private void loadSampleRecipes() {
        allRecipes.add(new Recipe("1", "Špagete Carbonara", "Pasta", "25 min", "🍝",
                "Klasično italijansko jelo sa kremastim sosom.",
                "200g špagete\n2 jaja\n100g pancete\n50g parmezana\nSo i biber",
                "1. Skuhaj špagete al dente.\n2. Isprži pancetu.\n3. Umuti jaja sa parmezanom.\n4. Pomiješaj sve zajedno na tihoj vatri."));

        allRecipes.add(new Recipe("2", "Pileća supa", "Supa", "45 min", "🍲",
                "Topla i hranjiva domaća supa.",
                "500g pilećih prsa\n2 šargarepe\n1 luk\n2 krompira\nSo, biber, peršun",
                "1. Skuhaj piletinu u vodi.\n2. Dodaj povrće i kuhaj 30 min.\n3. Začini i pospi peršunom."));

        allRecipes.add(new Recipe("3", "Palačinke", "Doručak", "20 min", "🥞",
                "Lagane i ukusne palačinke za doručak.",
                "200g brašna\n2 jaja\n300ml mlijeka\n1 kašika šećera\nPrstohvat soli\nUlje za prženje",
                "1. Pomiješaj sve sastojke u glatko tijesto.\n2. Peči na zagrijanoj tavi sa malo ulja.\n3. Poslužiti sa džemom ili Nutellom."));

        allRecipes.add(new Recipe("4", "Grčka salata", "Salata", "10 min", "🥗",
                "Svježa mediteranska salata.",
                "2 paradajza\n1 krastavac\n1 crveni luk\n100g feta sira\nMasline\nMaslinovo ulje",
                "1. Isjeći povrće na krupnije komade.\n2. Dodaj masline i feta sir.\n3. Prelij maslinovim uljem i začini."));

        allRecipes.add(new Recipe("5", "Čokoladni kolač", "Desert", "50 min", "🎂",
                "Bogat i vlažan čokoladni kolač.",
                "200g čokolade\n150g maslaca\n3 jaja\n150g šećera\n100g brašna\n1 kašičica praška za pecivo",
                "1. Otopi čokoladu i maslac.\n2. Umuti jaja sa šećerom.\n3. Pomiješaj sve i peći 35 min na 180°C."));

        allRecipes.add(new Recipe("6", "Pica Margarita", "Pica", "40 min", "🍕",
                "Klasična italijanska pica sa mocarelom.",
                "300g tijesta za picu\n200g pelati paradajza\n150g mocarele\nBazilika\nMaslinovo ulje",
                "1. Razvuci tijesto.\n2. Nанеси paradajz sos.\n3. Dodaj mocarelu.\n4. Peći 15 min na 220°C."));

        filteredRecipes.addAll(allRecipes);
    }

    private void setupCategories() {
        LinearLayout layout = findViewById(R.id.layoutCategories);
        String[] categories = {"Sve", "Pasta", "Supa", "Doručak", "Salata", "Desert", "Pica"};

        for (String cat : categories) {
            Button btn = new Button(this);
            btn.setText(cat);
            btn.setTextSize(12f);
            btn.setPadding(32, 8, 32, 8);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMarginEnd(8);
            btn.setLayoutParams(params);

            updateCategoryButton(btn, cat.equals("Sve"));

            btn.setOnClickListener(v -> {
                selectedCategory = cat;
                // Update svih dugmadi
                for (int i = 0; i < layout.getChildCount(); i++) {
                    Button b = (Button) layout.getChildAt(i);
                    updateCategoryButton(b, b.getText().toString().equals(cat));
                }
                EditText etSearch = findViewById(R.id.etSearch);
                filterRecipes(etSearch.getText().toString(), cat);
            });

            layout.addView(btn);
        }
    }

    private void updateCategoryButton(Button btn, boolean selected) {
        if (selected) {
            btn.setBackgroundColor(0xFFE94560);
            btn.setTextColor(0xFFFFFFFF);
        } else {
            btn.setBackgroundColor(0xFF16213E);
            btn.setTextColor(0xFFA0A0B0);
        }
    }

    private void filterRecipes(String query, String category) {
        filteredRecipes.clear();
        for (Recipe r : allRecipes) {
            boolean matchQuery = query.isEmpty() ||
                    r.getName().toLowerCase().contains(query.toLowerCase());
            boolean matchCategory = category.equals("Sve") ||
                    r.getCategory().equals(category);
            if (matchQuery && matchCategory) {
                filteredRecipes.add(r);
            }
        }
        adapter.updateList(filteredRecipes);
    }
}