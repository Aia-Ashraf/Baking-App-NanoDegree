package com.example.aiaashraf.bakingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.example.aiaashraf.bakingapplication.dummy.RecipesPojoModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {
    private ItemListActivity mParentActivity;


    Retrofit retrofit;
    String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private RecyclerView recyclerView;
    private RecipesAdapter recipesAdapter;
    public static List<RecipesPojoModel> recipesPojoModelList = new ArrayList<>();
    public static boolean twoPane;


    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

//        checkTabletOrMobile();


        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        assert recyclerView != null;

        recipesPojoModelList = new ArrayList<>();
        recyclerView = findViewById(R.id.item_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        RetrofitGetRecipes();
        recipesAdapter = new RecipesAdapter(this, recipesPojoModelList, mParentActivity, mTwoPane);
        recyclerView.setAdapter(recipesAdapter);
        recipesPojoModelList = new ArrayList<>();
        recipesAdapter.setRecipesModelList(recipesPojoModelList);
    }


    public void RetrofitGetRecipes() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            retrofit.create(API.class).getRecipes().enqueue(new Callback<ArrayList<RecipesPojoModel>>() {
                @Override
                public void onResponse(Call<ArrayList<RecipesPojoModel>> call, Response<ArrayList<RecipesPojoModel>> response) {

                    try {
                        if (response.code() == 200) {
                            recipesPojoModelList = response.body();
                            recipesAdapter.setRecipesModelList(recipesPojoModelList);

                            Log.d("responseName", "aaaaaaaaaaa" + recipesPojoModelList);
                        } else {
//                            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG);
                            Log.d("responseName", "error");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("responseName", "error" + e);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<RecipesPojoModel>> call, Throwable t) {
                    Log.d("responseName", "erroronFailure" + t);
                    }
            });
        }
    }
    private void checkTabletOrMobile() {

        if (findViewById(R.id.tabMode) != null) {

            Bundle args = new Bundle();
            args.putInt("id", 1);
            args.putInt("listSize", 1);
            ItemDetailFragment bakeFragment = new ItemDetailFragment();
            bakeFragment.setArguments(args);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().add(R.id.item_detail_container, bakeFragment).commit();
            twoPane = true;
            return;
        } else twoPane = false;
    }

    void check(){
        recyclerView = findViewById(R.id.item_list);

    }
}
