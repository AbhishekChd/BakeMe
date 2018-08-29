package com.example.abhishek.bakeme.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.abhishek.bakeme.models.Recipe;
import com.example.abhishek.bakeme.network.RecipeNetworkDataSource;

import java.util.List;

public class RecipeRepository {
    private static RecipeRepository sInstance;
    private final RecipeNetworkDataSource mDataSource;

    private RecipeRepository() {
        mDataSource = RecipeNetworkDataSource.getInstance();
    }

    public static RecipeRepository getInstance() {
        if (sInstance == null) {
            sInstance = new RecipeRepository();
        }
        return sInstance;
    }

    public LiveData<List<Recipe>> getRecipes(Context context) {
        return mDataSource.fetchRecipes(context);
    }
}
