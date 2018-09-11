package com.example.abhishek.bakeme.ui.details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhishek.bakeme.R;
import com.example.abhishek.bakeme.adapters.IngredientAdapter;
import com.example.abhishek.bakeme.adapters.StepsAdapter;
import com.example.abhishek.bakeme.models.Ingredient;
import com.example.abhishek.bakeme.models.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MasterListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MasterListFragment extends Fragment {

    // Arguments for Bundling parameters
    private static final String ARG_INGREDIENTS = "ingredients";
    private static final String ARG_STEPS = "steps";

    // List passed as Bundles to fragment
    private List<Ingredient> ingredientList;
    private List<Step> stepsList;

    // Step click callback
    private StepsAdapter.OnStepInteraction mCallback;

    public MasterListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MasterListFragment.
     */
    public static MasterListFragment newInstance(ArrayList<Ingredient> param1, ArrayList<Step> param2) {
        MasterListFragment fragment = new MasterListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_INGREDIENTS, param1);
        args.putParcelableArrayList(ARG_STEPS, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ingredientList = getArguments().getParcelableArrayList(ARG_INGREDIENTS);
            stepsList = getArguments().getParcelableArrayList(ARG_STEPS);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d("MasterList", "RecyclerView created");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_master_list, container, false);

        // Setup ingredients Recycler view
        RecyclerView rvIngredients = root.findViewById(R.id.rv_ingredients);
        IngredientAdapter ingredientAdapter = new IngredientAdapter(ingredientList);
        DividerItemDecoration decoration =
                new DividerItemDecoration(container.getContext(), DividerItemDecoration.VERTICAL);
        rvIngredients.addItemDecoration(decoration);
        rvIngredients.setAdapter(ingredientAdapter);

        // Setup steps Recycler view
        RecyclerView rvSteps = root.findViewById(R.id.rv_steps);
        StepsAdapter stepsAdapter = new StepsAdapter(stepsList, mCallback);
        rvSteps.addItemDecoration(decoration);
        rvSteps.setAdapter(stepsAdapter);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StepsAdapter.OnStepInteraction) {
            mCallback = (StepsAdapter.OnStepInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStepInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }
}
