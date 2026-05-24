package com.example.cookbookapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        setContentView(R.layout.activity_favorites);

        findViewById(R.id.btnBackFavorites).setOnClickListener(v -> finish());

        // Dohvati favorite iz FavoritesManager
        List<Recipe> favorites = FavoritesManager.getInstance().getFavorites();

        RecyclerView recycler = findViewById(R.id.recyclerFavorites);
        TextView tvEmpty = findViewById(R.id.tvEmptyFavorites);

        if (favorites.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            recycler.setLayoutManager(new LinearLayoutManager(this));
            recycler.setAdapter(new RecipeAdapter(favorites));
        }
    }
}