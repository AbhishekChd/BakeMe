package com.example.abhishek.bakeme.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhishek.bakeme.R;
import com.example.abhishek.bakeme.models.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private static final String TAG = RecipeAdapter.class.getSimpleName();
    private final Drawable mBackgroundGradient;
    private final Drawable mAppIcon;
    private List<Recipe> mRecipes;

    public RecipeAdapter(Context context) {
        mBackgroundGradient = context
                .getResources()
                .getDrawable(R.drawable.recipe_card_gradient);
        mAppIcon = context
                .getResources()
                .getDrawable(R.drawable.ic_logo);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parentView, int viewType) {
        View view = LayoutInflater.from(parentView.getContext())
                .inflate(R.layout.recipe_list_item, parentView, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {
        recipeViewHolder.recipeName.setText(mRecipes.get(i).getName());
        recipeViewHolder.peopleServing.setText(
                recipeViewHolder.itemView.getResources()
                        .getString(R.string.people_label, mRecipes.get(i).getServings())
        );

        String imagePath = mRecipes.get(i).getImage();

        if (TextUtils.isEmpty(imagePath)) {
            recipeViewHolder.recipeImage.setBackground(mBackgroundGradient);
            recipeViewHolder.recipeImage.setImageDrawable(mAppIcon);
        }

    }

    @Override
    public int getItemCount() {
        return mRecipes == null ? 0 : mRecipes.size();
    }

    public void setRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        final TextView recipeName;
        final TextView peopleServing;
        final ImageView recipeImage;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.tv_recipe_name);
            peopleServing = itemView.findViewById(R.id.tv_people_serving);
            recipeImage = itemView.findViewById(R.id.iv_recipe_image);
        }
    }
}
