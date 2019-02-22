package com.example.aiaashraf.bakingapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aiaashraf.bakingapplication.dummy.Ingredient;
import com.example.aiaashraf.bakingapplication.dummy.RecipesPojoModel;

import java.util.ArrayList;
import java.util.List;


public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    private List<RecipesPojoModel> recipesPojoModelList;
    private List<Ingredient> ingredientList;
    Context context;
    private final ItemListActivity mParentActivity;
    private final boolean mTwoPane;
    private IngredientAdapter ingredientAdapter;




    public RecipesAdapter(Context context, List<RecipesPojoModel> recipesPojoModels, ItemListActivity parent, boolean twoPane) {
        this.context = context;
        this.recipesPojoModelList = recipesPojoModels;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @NonNull
    @Override
    public RecipesAdapter.RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.item_list_content;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapter.RecipesViewHolder holder, int position) {


        final RecipesPojoModel recipesPojoModelPosition = recipesPojoModelList.get(position);
        ingredientList = recipesPojoModelPosition.getIngredients();
        final String qu =ingredientList.get(position).getQuantity();
        final String meas =ingredientList.get(position).getMeasure();
        final String ind =ingredientList.get(position).getIngredient();
        final int listSize =ingredientList.size();

        holder.recipeName.setText(recipesPojoModelPosition.getName());
        holder.recipeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, recipesPojoModelPosition.getId());
                    arguments.putString(ItemDetailFragment.ARG_ITEM_Name, recipesPojoModelPosition.getName());
                    arguments.putString(ItemDetailFragment.ARG_ITEM_Main_quantity, qu+"");
                    arguments.putString(ItemDetailFragment.ARG_ITEM_Main_measure, meas);
                    arguments.putString(ItemDetailFragment.ARG_ITEM_Main_ingredient, ind);
                    arguments.putString(ItemDetailFragment.ARG_ITEM_Main_indSize, listSize+"");
                    arguments.putString("myDataKey", String.valueOf(recipesPojoModelPosition));

                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    RecipesPojoModel recipesPojoModelparc = new RecipesPojoModel();
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, recipesPojoModelPosition.getId());
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_Name, recipesPojoModelPosition.getName());
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_Main_quantity, qu);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_Main_measure, meas);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_Main_ingredient, ind);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_Main_indSize, listSize+"");
                    intent.putExtra("myDataKey",recipesPojoModelPosition);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == recipesPojoModelList) return 0;
        return recipesPojoModelList.size();
    }

    public void setRecipesModelList(List<RecipesPojoModel> recipesModelList) {
        recipesPojoModelList = recipesModelList;
        notifyDataSetChanged();
    }

    static class RecipesViewHolder extends RecyclerView.ViewHolder {

        public TextView recipeName;

        public RecipesViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.tv_recipe);
        }
    }
}