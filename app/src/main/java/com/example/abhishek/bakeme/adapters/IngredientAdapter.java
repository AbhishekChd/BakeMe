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
        String quantityString = FormatHelper
                .convertDecimalToFraction(ingredients.get(position).getQuantity());

        String quantityStringArray[] = quantityString.split(" ");

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

        // Add the quantity to String first
        for (String s : quantityStringArray) {
            spannableStringBuilder.append(s);
        }

        // Edit spannable string for fractional units
        if (quantityString.contains("/")) {
            int start = 0;
            if (quantityStringArray.length == 2) {
                start = quantityStringArray[0].length();
            }
            spannableStringBuilder.setSpan(new SuperscriptSpan(), start, start + 1, 0);
            spannableStringBuilder.setSpan(new RelativeSizeSpan(0.65f), start, start + 3, 0);
            spannableStringBuilder.setSpan(new SubscriptSpan(), start + 2, start + 3, 0);
        }

        // Add units of quantity
        spannableStringBuilder
                .append(" ")
                .append(ingredients.get(position).getMeasure().toLowerCase());

        // Make quantity and unit bold
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 0, spannableStringBuilder.length(), 0);

        // Finally add the ingredient
        spannableStringBuilder
                .append(" ")
                .append(ingredients.get(position).getIngredient());

        // Set the text to TextView
        ingredientHolder.tvIngredient.setText(spannableStringBuilder);
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
