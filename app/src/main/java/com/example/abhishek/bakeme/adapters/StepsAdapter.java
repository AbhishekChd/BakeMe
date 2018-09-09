package com.example.abhishek.bakeme.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abhishek.bakeme.R;
import com.example.abhishek.bakeme.models.Step;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private final List<Step> mSteps;

    public StepsAdapter(List<Step> steps) {
        mSteps = steps;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps_list_item, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        holder.tvStepNumber.setText(String.valueOf(position + 1));
        holder.tvStepDescription.setText(mSteps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mSteps == null ? 0 : mSteps.size();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder {
        final TextView tvStepNumber;
        final TextView tvStepDescription;

        StepsViewHolder(View itemView) {
            super(itemView);
            tvStepNumber = itemView.findViewById(R.id.tv_step_number);
            tvStepDescription = itemView.findViewById(R.id.tv_step_description);
        }
    }
}
