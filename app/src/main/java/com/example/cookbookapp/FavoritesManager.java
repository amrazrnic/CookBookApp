package com.example.cookbookapp;

import java.util.ArrayList;
import java.util.List;

public class FavoritesManager {

    private static FavoritesManager instance;
    private List<Recipe> favorites = new ArrayList<>();

    private FavoritesManager() {}

    public static FavoritesManager getInstance() {
        if (instance == null) {
            instance = new FavoritesManager();
        }
        return instance;
    }

    public void addFavorite(Recipe recipe) {
        if (!isFavorite(recipe)) {
            favorites.add(recipe);
        }
    }

    public void removeFavorite(Recipe recipe) {
        favorites.removeIf(r -> r.getName().equals(recipe.getName()));
    }

    public boolean isFavorite(Recipe recipe) {
        for (Recipe r : favorites) {
            if (r.getName().equals(recipe.getName())) return true;
        }
        return false;
    }

    public List<Recipe> getFavorites() {
        return favorites;
    }
}