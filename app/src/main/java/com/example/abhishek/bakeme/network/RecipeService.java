package com.example.abhishek.bakeme.network;

import android.content.Context;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeService {
    private static final Object LOCK = new Object();
    private static RecipeClient sInstance;

    private RecipeService() {
    }

    public static RecipeClient getInstance(Context context) {
        if (sInstance == null) {
            // 512KB of cache
            Cache cache = new Cache(context.getCacheDir(), 1024 * 512);
            OkHttpClient httpClient = new OkHttpClient.Builder().cache(cache).build();
            synchronized (LOCK) {
                Retrofit.Builder builder
                        = new Retrofit.Builder()
                        .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                        .client(httpClient)
                        .addConverterFactory(GsonConverterFactory.create());
                sInstance = builder.build().create(RecipeClient.class);
            }
        }
        return sInstance;
    }
}
