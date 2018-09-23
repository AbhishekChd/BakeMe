package com.example.abhishek.bakeme.adapters;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abhishek.bakeme.R;
import com.example.abhishek.bakeme.models.Ingredient;
import com.example.abhishek.bakeme.utils.FormatHelper;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private final List<Ingredient> ingredients;

    public IngredientAdapter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_list_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder ingredientHolder, int position) {
        // Set the text to TextView
        ingredientHolder.tvIngredient.setText(
                FormatHelper.convertIngredient(
                        ingredients.get(position).getQuantity(),
                        ingredients.get(position).getMeasure(),
                        ingredients.get(position).getIngredient()
                )
        );
    }

    @Override
    public int getItemCount() {
        return ingredients == null ? 0 : ingredients.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        final TextView tvIngredient;

        IngredientViewHolder(View itemView) {
            super(itemView);
            tvIngredient = itemView.findViewById(R.id.tv_ingredient);
        }
    }
}
