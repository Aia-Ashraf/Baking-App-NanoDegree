package com.example.aiaashraf.bakingapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.aiaashraf.bakingapplication.dummy.Ingredient;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredientList;
    Context context;
    private ItemListActivity mParentActivity;
    private final boolean mTwoPane;

    public IngredientAdapter(Context context, List<Ingredient> ingredients ,ItemListActivity parent, boolean twoPane) {
        this.context = context;
        this.ingredientList = ingredients;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @NonNull
    @Override
    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        int layoutIdForListItem = R.layout.item_ingredient;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.IngredientViewHolder holder, int position) {

        final Ingredient ingredientPosition = ingredientList.get(position);
        holder.ingredientText.setText("Quantity: " + ingredientPosition.getQuantity() + "\n" + "Measure: " + ingredientPosition.getMeasure() + "\n" + "Ingredient: " + ingredientPosition.getIngredient());

    }

    @Override
    public int getItemCount() {
        if (null == ingredientList) return 0;
        return ingredientList.size();
    }

    public void setRecipesModelList(List<Ingredient> ingredients) {
        ingredientList = ingredients;
        notifyDataSetChanged();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {

        public TextView ingredientText;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientText = itemView.findViewById(R.id.tv_ingredient);
        }
    }
}