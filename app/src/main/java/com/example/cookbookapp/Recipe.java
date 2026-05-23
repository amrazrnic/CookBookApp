package com.example.cookbookapp;

public class Recipe {
    private String id;
    private String name;
    private String category;
    private String time;
    private String emoji;
    private String description;
    private String ingredients;
    private String steps;
    private boolean favorite;

    public Recipe() {}

    public Recipe(String id, String name, String category, String time, String emoji,
                  String description, String ingredients, String steps) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.time = time;
        this.emoji = emoji;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
        this.favorite = false;
    }

    // Getteri i setteri
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getEmoji() { return emoji; }
    public void setEmoji(String emoji) { this.emoji = emoji; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public String getSteps() { return steps; }
    public void setSteps(String steps) { this.steps = steps; }

    public boolean isFavorite() { return favorite; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }
}