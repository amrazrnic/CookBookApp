package com.example.cookbookapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    private boolean isFavorite = false;
    private TextView btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        setContentView(R.layout.activity_recipe_detail);

        String name = getIntent().getStringExtra("name");
        String emoji = getIntent().getStringExtra("emoji");
        String category = getIntent().getStringExtra("category");
        String time = getIntent().getStringExtra("time");
        String description = getIntent().getStringExtra("description");
        String ingredients = getIntent().getStringExtra("ingredients");
        String steps = getIntent().getStringExtra("steps");

        TextView tvEmoji = findViewById(R.id.tvDetailEmoji);
        TextView tvName = findViewById(R.id.tvDetailName);
        TextView tvCategory = findViewById(R.id.tvDetailCategory);
        TextView tvTime = findViewById(R.id.tvDetailTime);
        TextView tvDescription = findViewById(R.id.tvDetailDescription);
        TextView tvIngredients = findViewById(R.id.tvDetailIngredients);
        TextView tvSteps = findViewById(R.id.tvDetailSteps);
        btnFavorite = findViewById(R.id.btnFavoriteDetail);

        tvEmoji.setText(emoji);
        tvName.setText(name);
        tvCategory.setText(category);
        tvTime.setText("⏱ " + time);
        tvDescription.setText(description);
        tvIngredients.setText(ingredients);
        tvSteps.setText(steps);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        btnFavorite.setOnClickListener(v -> {
            isFavorite = !isFavorite;
            btnFavorite.setText(isFavorite ? "❤️" : "🤍");
        });
    }
}