package com.example.abhishek.bakeme.ui.details;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.abhishek.bakeme.R;
import com.example.abhishek.bakeme.adapters.StepsAdapter;
import com.example.abhishek.bakeme.models.Ingredient;
import com.example.abhishek.bakeme.models.Step;
import com.example.abhishek.bakeme.ui.step.RecipeStepActivity;
import com.example.abhishek.bakeme.ui.step.RecipeStepFragment;
import com.example.abhishek.bakeme.ui.widget.RecipeWidget;
import com.example.abhishek.bakeme.utils.FormatHelper;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity
        implements StepsAdapter.OnStepInteraction {

    public static final String PARAM_INGREDIENT = "ingredients";
    public static final String PARAM_STEPS = "steps";
    public static final String PARAM_NAME = "name";
    private static final String TAG = DetailActivity.class.getSimpleName();
    private ArrayList<Ingredient> ingredients = null;
    private ArrayList<Step> steps = null;
    private String recipeName;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTwoPane = getResources().getBoolean(R.bool.isTablet);

        if (getIntent().getExtras() != null) {
            if (getIntent().hasExtra(PARAM_INGREDIENT)) {
                ingredients = getIntent().getParcelableArrayListExtra(PARAM_INGREDIENT);
                steps = getIntent().getParcelableArrayListExtra(PARAM_STEPS);
                recipeName = getIntent().getStringExtra(PARAM_NAME);
            }
        }

        setupActionBar();

        MasterListFragment masterListFragment = MasterListFragment.newInstance(ingredients, steps);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.master_list_fragment, masterListFragment);
        fragmentTransaction.commit();

        // Save current recipe for widget
        saveDataToSharedPreference();
        // Then Update Widget
        updateWidget();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && recipeName != null) {
            actionBar.setTitle(recipeName);
        }
    }

    @Override
    public void onStepClick(int id) {
        if (mTwoPane) {
            RecipeStepFragment recipeStepFragment = RecipeStepFragment.newInstance(steps.get(id));

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.step_description_fragment, recipeStepFragment);
            fragmentTransaction.commit();
        } else {
            Intent intent = new Intent(this, RecipeStepActivity.class);
            intent.putParcelableArrayListExtra(PARAM_STEPS, steps);
            intent.putExtra(RecipeStepActivity.PARAM_INDEX, id);
            intent.putExtra(RecipeStepActivity.PARAM_NAME, recipeName);
            startActivity(intent);
        }
    }

    private void saveDataToSharedPreference() {
        StringBuilder ingredientStringBuilder = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            ingredientStringBuilder.append(i + 1)
                    .append(". ")
                    .append(
                            getString(
                                    R.string.ingredient_item,
                                    FormatHelper.convertDecimalToFraction(
                                            ingredients.get(i).getQuantity()),
                                    ingredients.get(i).getMeasure().toLowerCase(),
                                    ingredients.get(i).getIngredient()
                            )
                    )
                    .append('\n');
        }

        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PARAM_NAME, recipeName);
        editor.putString(PARAM_INGREDIENT, ingredientStringBuilder.toString());
        editor.apply();
    }

    private void updateWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidget.class));
        RecipeWidget.updateRecipeWidgets(this, appWidgetManager, appWidgetIds);
    }
}
