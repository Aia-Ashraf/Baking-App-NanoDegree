package com.example.aiaashraf.bakingapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aiaashraf.bakingapplication.dummy.DummyContent;
import com.example.aiaashraf.bakingapplication.dummy.Ingredient;
import com.example.aiaashraf.bakingapplication.dummy.RecipesPojoModel;
import com.example.aiaashraf.bakingapplication.dummy.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment  {

    private RecyclerView recyclerView;
    private TextView desc;
    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView playerView;
    private StepsAdapter stepsAdapter;
    public static List<Step> stepList = new ArrayList<>();


    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_Name = "item_name";
    public static final String ARG_ITEM_Main_quantity = "item_quantity";
    public static final String ARG_ITEM_Main_measure = "item_measure";
    public static final String ARG_ITEM_Main_ingredient = "item_ingredient";
    public static final String ARG_ITEM_Main_indSize = "item_indSize";
    public static final String ARG_ITEM_pars = "myDataKey";


    private RecipesPojoModel s;
    private ItemListActivity mParentActivity;
    private boolean mTwoPane;


    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    private RecipesPojoModel recipesPojoModel;
    private List<Ingredient> ingredientList;
    Gson gson;

    public int id;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                String i = bundle.getString(ItemDetailFragment.ARG_ITEM_Name);
                appBarLayout.setTitle(i);
                s = bundle.getParcelable(ItemDetailFragment.ARG_ITEM_pars);
                stepList = s.getSteps();
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tablet_layout, container, false);

        if (getArguments() != null) {
            RecipesPojoModel object = getArguments().getParcelable("myDataKey");
            stepList = object.getSteps();
        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerTab);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        stepsAdapter = new StepsAdapter(getContext(), stepList, mParentActivity, mTwoPane);
        recyclerView.setAdapter(stepsAdapter);
        stepsAdapter.setRecipesModelList(stepList);
 return rootView;
    }

}