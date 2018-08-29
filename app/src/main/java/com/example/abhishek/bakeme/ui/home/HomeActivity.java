package com.example.abhishek.bakeme.ui.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.abhishek.bakeme.R;
import com.example.abhishek.bakeme.adapters.RecipeAdapter;
import com.example.abhishek.bakeme.models.Recipe;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final String LOG_TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final RecipeAdapter adapter = new RecipeAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.rv_recipe_list);
        recyclerView.setAdapter(adapter);

        HomeViewModel viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        viewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if (recipes != null) {
                    Log.d(LOG_TAG, "Recipes: " + recipes.size());
                    adapter.setRecipes(recipes);
                }
            }
        });
    }
}
