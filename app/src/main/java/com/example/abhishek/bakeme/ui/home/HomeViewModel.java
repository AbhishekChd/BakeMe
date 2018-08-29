package com.example.abhishek.bakeme.ui.home;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.abhishek.bakeme.data.RecipeRepository;
import com.example.abhishek.bakeme.models.Recipe;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private static final RecipeRepository sRecipeRepository = RecipeRepository.getInstance();

    public HomeViewModel(@NonNull Application application) {
        super(application);

    }

    public LiveData<List<Recipe>> getRecipes() {
        return sRecipeRepository.getRecipes(getApplication().getBaseContext());
    }
}
