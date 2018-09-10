package com.example.abhishek.bakeme.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.abhishek.bakeme.R;
import com.example.abhishek.bakeme.adapters.StepsAdapter;
import com.example.abhishek.bakeme.models.Ingredient;
import com.example.abhishek.bakeme.models.Step;
import com.example.abhishek.bakeme.ui.step.RecipeStepActivity;
import com.example.abhishek.bakeme.ui.step.RecipeStepFragment;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity
        implements StepsAdapter.OnStepInteraction {

    public static final String PARAM_INGREDIENT = "ingredients";
    public static final String PARAM_STEPS = "steps";
    private static final String TAG = DetailActivity.class.getSimpleName();
    private ArrayList<Ingredient> ingredients = null;
    private ArrayList<Step> steps = null;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTwoPane = findViewById(R.id.tab_container) != null;

        if (getIntent().getExtras() != null) {
            if (getIntent().hasExtra(PARAM_INGREDIENT)) {
                ingredients = getIntent().getParcelableArrayListExtra(PARAM_INGREDIENT);
                steps = getIntent().getParcelableArrayListExtra(PARAM_STEPS);
            }
        }
        MasterListFragment masterListFragment = MasterListFragment.newInstance(ingredients, steps);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.master_list_fragment, masterListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onStepClick(int id) {
        if (mTwoPane) {
            RecipeStepFragment recipeStepFragment = RecipeStepFragment.newInstance(
                    steps.get(id).getVideoURL(),
                    steps.get(id).getDescription()
            );

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.add(R.id.master_list_fragment, recipeStepFragment);
            fragmentTransaction.commit();
        } else {
            Intent intent = new Intent(this, RecipeStepActivity.class);
            intent.putParcelableArrayListExtra(PARAM_STEPS, steps);
            intent.putExtra(RecipeStepActivity.PARAM_INDEX, id);
            startActivity(intent);
        }
    }
}
