package com.example.abhishek.bakeme.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.example.abhishek.bakeme.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeNetworkDataSource {
    private static final String LOG_TAG = RecipeNetworkDataSource.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static RecipeNetworkDataSource sInstance;
    private MutableLiveData<List<Recipe>> mRecipes = new MutableLiveData<>();

    private RecipeNetworkDataSource() {
    }

    public synchronized static RecipeNetworkDataSource getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new RecipeNetworkDataSource();
            }
        }
        return sInstance;
    }

    public LiveData<List<Recipe>> fetchRecipes(Context context) {
        RecipeClient client = RecipeService.getInstance(context);
        Call<List<Recipe>> call = client.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.body() != null) {
                    mRecipes.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
        return this.mRecipes;
    }
}
