package com.example.aiaashraf.bakingapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.example.aiaashraf.bakingapplication.dummy.Ingredient;
import com.example.aiaashraf.bakingapplication.dummy.RecipesPojoModel;
import com.example.aiaashraf.bakingapplication.dummy.Step;

import java.util.ArrayList;
import java.util.List;


/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;

    private IngredientAdapter ingredientAdapter;
    private StepsAdapter stepsAdapter;
    private List<Ingredient> ingredientList;
    public static List<Step> stepList = new ArrayList<>();
    private ItemListActivity mParentActivity;
    private boolean mTwoPane;
    private String idd;

    public ItemDetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        RecipesPojoModel object = getIntent().getParcelableExtra("myDataKey");
        idd = object.getId();

        recyclerView = findViewById(R.id.rv_ingredient);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        ingredientAdapter = new IngredientAdapter(ItemDetailActivity.this, ingredientList, mParentActivity, mTwoPane);
        recyclerView.setAdapter(ingredientAdapter);
        ingredientList = new ArrayList<>();
        ingredientList = object.getIngredients();
        ingredientAdapter.setRecipesModelList(ingredientList);


        recyclerView2 = findViewById(R.id.rv_steps);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.getLayoutManager().setMeasurementCacheEnabled(false);
        stepsAdapter = new StepsAdapter(ItemDetailActivity.this, stepList, mParentActivity, mTwoPane);
        recyclerView2.setAdapter(stepsAdapter);
        stepList = new ArrayList<>();
        stepList = object.getSteps();
        stepsAdapter.setRecipesModelList(stepList);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
