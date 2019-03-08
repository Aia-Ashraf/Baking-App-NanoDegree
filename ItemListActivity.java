package com.example.aiaashraf.bakingapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.aiaashraf.bakingapplication.dummy.RecipesPojoModel;
import com.example.aiaashraf.bakingapplication.dummy.Step;

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
    private StepsAdapter stepsAdapter;

    public static boolean twoPane;

    public static List<Step> stepList = new ArrayList<>();


    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        isTablet();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

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



    public void isTablet() {

        // Compute screen size
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        float screenWidth = dm.widthPixels / dm.xdpi;
        float screenHeight = dm.heightPixels / dm.ydpi;
        double size = Math.sqrt(Math.pow(screenWidth, 2) +
                Math.pow(screenHeight, 2));
        // Tablet devices have a screen size greater than 6 inches
        if (size >= 5.4) {
            if(dm.heightPixels>=1953) {
                Log.e("aia", "Tablet");
                mTwoPane = true;
            }
            mTwoPane = false;

        } else {
            Log.e("aia", "Mobile");
            mTwoPane = false;
        }
    }
}
