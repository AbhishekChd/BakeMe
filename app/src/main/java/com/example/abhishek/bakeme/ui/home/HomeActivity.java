package com.example.abhishek.bakeme.ui.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.abhishek.bakeme.R;
import com.example.abhishek.bakeme.adapters.RecipeAdapter;
import com.example.abhishek.bakeme.models.Ingredient;
import com.example.abhishek.bakeme.models.Recipe;
import com.example.abhishek.bakeme.models.Step;
import com.example.abhishek.bakeme.ui.details.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements RecipeAdapter.OnRecipeCardInteraction {
    private static final String LOG_TAG = HomeActivity.class.getSimpleName();
    private List<Recipe> mRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupToolbar();

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        final RecipeAdapter adapter = new RecipeAdapter(this, this);
        GridLayoutManager layoutManager = new GridLayoutManager(
                this,
                getResources().getInteger(R.integer.recipe_grid_item_size)
        );
        RecyclerView recyclerView = findViewById(R.id.rv_recipe_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setMotionEventSplittingEnabled(false);
        recyclerView.setAdapter(adapter);

        setupViewModel(adapter);
    }

    private void setupViewModel(final RecipeAdapter adapter) {
        HomeViewModel viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        viewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if (recipes != null) {
                    Log.d(LOG_TAG, "Recipes: " + recipes.size());
                    mRecipes = recipes;
                    adapter.setRecipes(recipes);
                }
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onRecipeCardClick(int position) {
        Log.d(LOG_TAG, "Starting intent");
        Intent intent = new Intent(this, DetailActivity.class);

        ArrayList<Ingredient> ingredients
                = new ArrayList<>(mRecipes.get(position).getIngredients());

        ArrayList<Step> steps
                = new ArrayList<>(mRecipes.get(position).getSteps());
        Log.d(LOG_TAG, ingredients.toString());
        intent.putParcelableArrayListExtra(DetailActivity.PARAM_INGREDIENT, ingredients);
        intent.putParcelableArrayListExtra(DetailActivity.PARAM_STEPS, steps);
        startActivity(intent);
    }
}
