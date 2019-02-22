package com.example.aiaashraf.bakingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import static com.example.aiaashraf.bakingapplication.ItemDetailFragment.ARG_ITEM_ID;
import static com.example.aiaashraf.bakingapplication.ItemDetailFragment.ARG_ITEM_Main_indSize;

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
//    List<Step>stepList;
    private List<Ingredient> ingredientList;
    public static List<Step> stepList = new ArrayList<>();


    private int size, id;
    private String idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        RecipesPojoModel object = getIntent().getParcelableExtra("myDataKey");
        idd= object.getId();
        Log.e("aiaaaaaaaaaaaaaaa",idd);


        recyclerView = findViewById(R.id.rv_ingredient);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        ingredientAdapter = new IngredientAdapter(ItemDetailActivity.this, ingredientList);
        recyclerView.setAdapter(ingredientAdapter);
        ingredientList = new ArrayList<>();
        ingredientList = object.getIngredients();
        ingredientAdapter.setRecipesModelList(ingredientList);


        recyclerView2 = findViewById(R.id.rv_steps);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.getLayoutManager().setMeasurementCacheEnabled(false);
        stepsAdapter = new StepsAdapter(ItemDetailActivity.this, stepList);
        recyclerView2.setAdapter(stepsAdapter);
        stepList = new ArrayList<>();
        stepList = object.getSteps();
        stepsAdapter.setRecipesModelList(stepList);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ARG_ITEM_ID,
                    getIntent().getStringExtra(ARG_ITEM_ID));
            arguments.putString(ItemDetailFragment.ARG_ITEM_Name,
                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_Name));
            arguments.putString(ItemDetailFragment.ARG_ITEM_Main_ingredient,
                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_Main_ingredient));

            arguments.putString(ItemDetailFragment.ARG_ITEM_Main_measure,
                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_Main_measure));

            arguments.putString(ItemDetailFragment.ARG_ITEM_Main_quantity,
                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_Main_quantity));
            arguments.putString(ARG_ITEM_Main_indSize,
                    getIntent().getStringExtra(ARG_ITEM_Main_indSize));
            arguments.putString("myDataKey",
                    getIntent().getStringExtra("myDataKey"));

            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();

            Bundle bundle = getIntent().getExtras();
            id = bundle.getInt("id", 1);
            size = Integer.parseInt(arguments.getString(ARG_ITEM_Main_indSize));

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
