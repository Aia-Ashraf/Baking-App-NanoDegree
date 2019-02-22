package com.example.aiaashraf.bakingapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.SyncStateContract;
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
public class ItemDetailFragment extends Fragment implements ExoPlayer.EventListener {

    private RecyclerView recyclerView;
    private TextView desc;
    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView playerView;

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
    private ItemListActivity mParentActivity;


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


        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));


            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {


                Bundle bundle = this.getArguments();
                if (bundle != null) {
                    String i = bundle.getString(ItemDetailFragment.ARG_ITEM_Name);
                    appBarLayout.setTitle(i);
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.tablet_layout, container, false);

        desc = (TextView) rootView.findViewById(R.id.tv_step);
        playerView = (SimpleExoPlayerView) rootView.findViewById(R.id.media_player);

        int idRecycler = getArguments().getInt("id");
        int listSize = getArguments().getInt("listSize");

//        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(new StepsAdapter(getActivity(), ItemDetailActivity.stepList));

        Log.i("asdsdasd", getArguments().getInt("mid") + " " + getArguments().getInt("baker") + " <<<<");
        String uri = ItemListActivity.recipesPojoModelList.get(getArguments().getInt("baker")).getSteps().get(getArguments().getInt("mid")).getVideoURL();
//        desc.setText(" ");
//        desc.setText(ItemListActivity.recipesPojoModelList.get(getArguments().getInt("baker")).getSteps().get(getArguments().getInt("mid")).getDescription() + " ");
        initializePlayer(Uri.parse("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd97a_1-mix-marscapone-nutella-creampie/1-mix-marscapone-nutella-creampie.mp4"));
        return rootView;
    }

    private void initializePlayer(Uri uri) {

        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();

        playerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.question_mark));
        exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
        playerView.setPlayer(exoPlayer);

        exoPlayer.addListener(this);

        String userAgent = com.google.android.exoplayer2.util.Util.getUserAgent(getActivity(), "Bake");
        MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getActivity(), userAgent),
                new DefaultExtractorsFactory(), null, null);
        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);
    }

    private void releasePlayer() {

        exoPlayer.stop();
        exoPlayer.release();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}

//        View rootView = inflater.inflate(R.layout.item_detail, container, false);
//
//                Bundle bundle = this.getArguments();
//                if (bundle != null) {
//                    String q = bundle.getString(ItemDetailFragment.ARG_ITEM_Main_quantity);
//                    String m = bundle.getString(ItemDetailFragment.ARG_ITEM_Main_measure);
//                    String in = bundle.getString(ItemDetailFragment.ARG_ITEM_Main_ingredient);
//                    String idd=bundle.getString(ItemDetailFragment.ARG_ITEM_ID);
//                     id =Integer.parseInt(idd);
//
//                    // Show the dummy content as text in a TextView.
//                    if (mItem != null) {
////                        ((TextView) rootView.findViewById(R.id.item_detail)).setText(q + " " + m + " " + "of" + " " + in);
//
//                    }
//        }
//
//        return rootView;
//    }
//}
