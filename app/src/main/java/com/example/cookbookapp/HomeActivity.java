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


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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


        findViewById(R.id.tvProfileBtn).setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));

        findViewById(R.id.btnFavoritesHome).setOnClickListener(v ->
                startActivity(new Intent(this, FavoritesActivity.class)));

        findViewById(R.id.btnShoppingHome).setOnClickListener(v ->
                startActivity(new Intent(this, ShoppingActivity.class)));
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
                "1. Razvuci tijesto.\n2. Dodaj paradajz sos.\n3. Dodaj mocarelu.\n4. Peći 15 min na 220°C."));
        allRecipes.add(new Recipe("7", "Pica Quattro Formaggi", "Pica", "35 min", "🍕",
                "Pica sa cetiri vrste sira.",
                "300g tijesta za picu\n100g mocarele\n80g gorgonzole\n80g parmezana\n80g ementala\nMaslinovo ulje",
                "1. Razvuci tijesto.\n2. Premazati maslinovim uljem.\n3. Rasporedi sve sireve po tijestu.\n4. Peci 15 min na 220°C."));

        allRecipes.add(new Recipe("8", "Penne Arrabiata", "Pasta", "20 min", "🍝",
                "Pikantna pasta sa paradajz sosom i cili papricicama.",
                "300g penne paste\n400g pelati paradajza\n3 cena bijelog luka\n2 cili papricice\nMaslinovo ulje\nSo, peršun",
                "1. Skuhaj penne al dente.\n2. Na ulju proprziti bijeli luk i cili.\n3. Dodaj pelate i kuhaj 10 min.\n4. Pomijesaj sa pastom i pospi peršunom."));

        allRecipes.add(new Recipe("9", "Lazanje", "Pasta", "60 min", "🍝",
                "Klasicne lazanje sa mesnim sosom i becamelom.",
                "300g lista za lazanje\n400g mljevenog mesa\n400g pelata\n500ml becamel sosa\n100g parmezana\n1 luk",
                "1. Proprziti luk i meso.\n2. Dodaj pelate i kuhaj 20 min.\n3. Slažite liste, meso i besamel u tepsiju.\n4. Pospi parmezanom i peci 30 min na 180°C."));
        allRecipes.add(new Recipe("10", "Omlet sa sirom", "Doručak", "10 min", "🍳",
                "Brz i ukusan omlet za doručak.",
                "3 jaja\n50g sira\n1 kasika maslaca\nSo i biber\nPeršun",
                "1. Umuti jaja sa solju i biberom.\n2. Rastopi maslac na tavi.\n3. Sipaj jaja i peci na srednoj vatri.\n4. Dodaj sir i presavij omlet na pola."));

        allRecipes.add(new Recipe("11", "Tost sa avokadom", "Doručak", "10 min", "🥑",
                "Zdravi doručak sa avokadom i jajima.",
                "2 kriške hljeba\n1 avokado\n2 jaja\nLimunov sok\nSo, biber, cili pahuljice",
                "1. Prepeci hljeb u tosteru.\n2. Zgnjeci avokado sa limunovim sokom i solju.\n3. Namazi avokado na tost.\n4. Dodaj pohirana ili przena jaja na vrh."));

        allRecipes.add(new Recipe("12", "Zobena kasa", "Doručak", "10 min", "🥣",
                "Hranjiva zobena kasa sa vocem.",
                "80g zobenih pahuljica\n300ml mlijeka\n1 banana\n1 kasika meda\nBobice po zelji\nCimet",
                "1. Kuhaj zobene pahuljice u mlijeku 5 min.\n2. Mijesaj dok se ne zgusne.\n3. Poslužiti sa bananama, bobicama i medom.\n4. Pospi cimetom."));

        allRecipes.add(new Recipe("13", "Goveđa supa", "Supa", "60 min", "🍲",
                "Bogata i hranjiva goveđa supa.",
                "400g govedine\n2 šargarepe\n2 krompira\n1 luk\n2 stabljike celera\nSo, biber, lovorov list",
                "1. Skuhaj govedinu u vodi sa lovorovim listom.\n2. Dodaj isijeckano povrce.\n3. Kuhaj 40 min na tihoj vatri.\n4. Začini i poslužiti sa rezancima."));

        allRecipes.add(new Recipe("14", "Krem supa od bundeve", "Supa", "40 min", "🎃",
                "Kremasta supa od bundeve idealna za jesen.",
                "600g bundeve\n1 luk\n2 cena bijelog luka\n400ml kokosovog mlijeka\nDjumbir, kurkuma\nSo i biber",
                "1. Proprziti luk i bijeli luk.\n2. Dodaj isijeckanu bundevu i zacine.\n3. Prelij kokosovim mlijekom i kuhaj 20 min.\n4. Blendati do kremaste konzistencije."));

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