package com.example.abhishek.bakeme.ui.details;

import android.content.Context;
import android.net.Uri;
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
 * Activities that contain this fragment must implement the
 * {@link MasterListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MasterListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MasterListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_INGREDIENTS = "ingredients";
    private static final String ARG_STEPS = "steps";

    // TODO: Rename and change types of parameters
    private List<Ingredient> ingredientList;
    private List<Step> stepsList;

    private OnFragmentInteractionListener mListener;

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
    // TODO: Rename and change types and number of parameters
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
        Log.d("MasterList", "onCreateView(): " + ingredientList);
        View root = inflater.inflate(R.layout.fragment_master_list, container, false);
        // Inflate the layout for this fragment
        RecyclerView rv = root.findViewById(R.id.rv_ingredients);
        IngredientAdapter ingredientAdapter = new IngredientAdapter(ingredientList);
        DividerItemDecoration decoration =
                new DividerItemDecoration(container.getContext(), DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(decoration);
        rv.setAdapter(ingredientAdapter);

        RecyclerView rv2 = root.findViewById(R.id.rv_steps);
        StepsAdapter stepsAdapter = new StepsAdapter(stepsList);
        rv2.addItemDecoration(decoration);
        rv2.setAdapter(stepsAdapter);
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
