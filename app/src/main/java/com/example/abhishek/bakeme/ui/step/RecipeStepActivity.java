package com.example.abhishek.bakeme.ui.step;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.abhishek.bakeme.R;
import com.example.abhishek.bakeme.models.Step;
import com.example.abhishek.bakeme.ui.details.DetailActivity;

import java.util.ArrayList;

public class RecipeStepActivity extends AppCompatActivity {

    public static final String PARAM_INDEX = "index";
    public static final String PARAM_NAME = "name";
    private String recipeName;
    private ArrayList<Step> steps = null;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        if (getIntent().getExtras() != null) {
            if (getIntent().hasExtra(DetailActivity.PARAM_STEPS)) {
                steps = getIntent().getParcelableArrayListExtra(DetailActivity.PARAM_STEPS);
                index = getIntent().getIntExtra(PARAM_INDEX, 0);
                recipeName = getIntent().getStringExtra(PARAM_NAME);
            }
        }

        setupActionBar();

        RecipeStepFragment recipeStepFragment = RecipeStepFragment.newInstance(
                steps.get(index).getVideoURL(),
                steps.get(index).getDescription()
        );

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.step_description_fragment, recipeStepFragment);
        fragmentTransaction.commit();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && recipeName != null) {
            actionBar.setTitle(recipeName);
        }
    }
}
