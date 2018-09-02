package com.example.abhishek.bakeme.ui.details;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.abhishek.bakeme.R;
import com.example.abhishek.bakeme.models.Ingredient;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity
        implements MasterListFragment.OnFragmentInteractionListener {

    public static final String PARAM_INGREDIENT = "ingredients";
    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ArrayList<Ingredient> ingredients = null;

        if (getIntent().getExtras() != null) {
            if (getIntent().hasExtra(PARAM_INGREDIENT)) {
                ingredients = getIntent().getParcelableArrayListExtra(PARAM_INGREDIENT);
                Log.d(TAG, "Received ingredients : " + ingredients);
            }
        }
        MasterListFragment masterListFragment = MasterListFragment.newInstance(ingredients, "");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.master_list_fragment, masterListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
