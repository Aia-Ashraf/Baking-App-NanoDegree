package com.example.aiaashraf.bakingapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aiaashraf.bakingapplication.dummy.Ingredient;
import com.example.aiaashraf.bakingapplication.dummy.Step;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private List<Step> steps;
    Context context;


    public StepsAdapter(Context context, List<Step> steps) {
        this.context = context;
        this.steps = steps;
    }

    @NonNull
    @Override
    public StepsAdapter.StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.step_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.StepsViewHolder holder, int position) {

        final Step stepPosition = steps.get(position);
        holder.stepText.setText(stepPosition.getDescription());
        holder.stepText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ItemListActivity.twoPane) {

                    Bundle args = new Bundle();
                    args.putInt("step_id", stepPosition.getId());
                    ItemDetailFragment bakeFragment = new ItemDetailFragment();
                    bakeFragment.setArguments(args);
                    FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.frame, bakeFragment).commit();

                } else {
                    Intent intent = new Intent(context,VideosActivity.class);
                    intent.putExtra("step_id", stepPosition.getId());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == steps) return 0;
        return steps.size();
    }

    public void setRecipesModelList(List<Step> step) {
        steps = step;
        notifyDataSetChanged();
    }

    static class StepsViewHolder extends RecyclerView.ViewHolder {

        public TextView stepText;

        public StepsViewHolder(View itemView) {
            super(itemView);
            stepText = itemView.findViewById(R.id.tv_step);
        }
    }
}