
package com.example.cookbookapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void updateList(List<Recipe> newList) {
        this.recipes = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);

        holder.tvEmoji.setText(recipe.getEmoji());
        holder.tvName.setText(recipe.getName());
        holder.tvCategory.setText(recipe.getCategory());
        holder.tvTime.setText("⏱ " + recipe.getTime());
        holder.tvFavorite.setText(recipe.isFavorite() ? "❤️" : "🤍");

        // Klik na favorit
        holder.tvFavorite.setOnClickListener(v -> {
            recipe.setFavorite(!recipe.isFavorite());
            holder.tvFavorite.setText(recipe.isFavorite() ? "❤️" : "🤍");
        });

        // Klik na recept, otvori detail ekran
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RecipeDetailActivity.class);
            intent.putExtra("name", recipe.getName());
            intent.putExtra("emoji", recipe.getEmoji());
            intent.putExtra("category", recipe.getCategory());
            intent.putExtra("time", recipe.getTime());
            intent.putExtra("description", recipe.getDescription());
            intent.putExtra("ingredients", recipe.getIngredients());
            intent.putExtra("steps", recipe.getSteps());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmoji, tvName, tvCategory, tvTime, tvFavorite;

        ViewHolder(View itemView) {
            super(itemView);
            tvEmoji = itemView.findViewById(R.id.tvRecipeEmoji);
            tvName = itemView.findViewById(R.id.tvRecipeName);
            tvCategory = itemView.findViewById(R.id.tvRecipeCategory);
            tvTime = itemView.findViewById(R.id.tvRecipeTime);
            tvFavorite = itemView.findViewById(R.id.tvFavorite);
        }
    }
}