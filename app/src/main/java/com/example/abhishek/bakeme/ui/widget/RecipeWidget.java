package com.example.abhishek.bakeme.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.abhishek.bakeme.R;
import com.example.abhishek.bakeme.ui.details.DetailActivity;
import com.example.abhishek.bakeme.ui.home.HomeActivity;

public class RecipeWidget extends AppWidgetProvider {

    private static final String TAG = RecipeWidget.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        // Fetch the widget Recipe
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        String recipeName = sharedPreferences.getString(DetailActivity.PARAM_NAME, "");
        String ingredients = sharedPreferences.getString(DetailActivity.PARAM_INGREDIENT, "");

        // Setting up views
        views.setTextViewText(R.id.tv_recipe_name, recipeName);
        views.setTextViewText(R.id.tv_ingredients, ingredients);

        // Setting up app intent
        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.recipe_widget, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}

