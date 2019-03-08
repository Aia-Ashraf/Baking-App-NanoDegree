package com.example.aiaashraf.bakingapplication;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class VideosActivity extends AppCompatActivity implements ExoPlayer.EventListener {

    private int id, bakeId;
    //    private String uri;
    private SimpleExoPlayer exoPlayer;
    private static MediaSessionCompat mediaSession;
    private NotificationManager notificationManager;
    private PlaybackStateCompat.Builder stateBuilder;
    private static boolean flag = false;
    private static int mid;
    SimpleExoPlayerView playerView;
    TextView desc;
    Button nextButton;

    Button prevButton;
    String uri, decription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Step object = getIntent().getParcelableExtra("stepsParcelable");
        final Intent object = getIntent();
        decription = object.getStringExtra("step_desc");
        uri = object.getStringExtra("step_uri");

//        nextButton = (Button) findViewById(R.id.next);
//        prevButton = (Button) findViewById(R.id.prev);

        desc = (TextView) findViewById(R.id.desc);
        playerView = (SimpleExoPlayerView) findViewById(R.id.media_player);


        Bundle bundle = getIntent().getExtras();
        if (!flag) id = bundle.getInt("step_id", 666);
        else id = mid;
        bakeId = bundle.getInt("id", 666);
        Log.i("xzceeqw", flag + " " + id + " <<<");

        desc.setText(decription);
        initializeMediaSession();
        initializePlayer(Uri.parse(uri));

//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                flag = true;
//                if (id + 1 < ItemListActivity.recipesPojoModelList.get(bakeId).getSteps().size()) {
//                    desc.setText(ItemListActivity.recipesPojoModelList.get(bakeId).getSteps().get(id + 1).getDescription() + " ");
//                    releasePlayer();
//                    String nextUri = ItemListActivity.recipesPojoModelList.get(bakeId).getSteps().get(id + 1).getVideoURL();
//                    initializePlayer(Uri.parse(nextUri));
//                    id = id + 1;
//                    mid = id;
//                } else
//                    Toast.makeText(VideosActivity.this, "You Have Just Finished !", Toast.LENGTH_SHORT).show();
//            }
//        });

//        prevButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                flag = true;
//                if (id > 0) {
//                    desc.setText(ItemListActivity.recipesPojoModelList.get(bakeId).getSteps().get(id - 1).getDescription() + " ");
//                    releasePlayer();
//                    String nextUri =ItemListActivity.recipesPojoModelList.get(bakeId).getSteps().get(id - 1).getVideoURL();
//                    initializePlayer(Uri.parse(nextUri));
//                    id = id - 1;
//                    mid = id;
//                } else
//                    Toast.makeText(VideosActivity.this, "You Have Just Finished !", Toast.LENGTH_SHORT).show();
//            }
//        });
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
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    exoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    exoPlayer.getCurrentPosition(), 1f);
        }
        mediaSession.setPlaybackState(stateBuilder.build());
//        showNotification(stateBuilder.build());

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {

        exoPlayer.stop();
        exoPlayer.release();
    }

    private void initializeMediaSession() {

        mediaSession = new MediaSessionCompat(this, "aaa");
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mediaSession.setMediaButtonReceiver(null);
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(stateBuilder.build());
        mediaSession.setCallback(new MySessionCallback());
        mediaSession.setActive(true);

    }

    public static class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mediaSession, intent);
        }
    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            exoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            exoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            exoPlayer.seekTo(0);
        }
    }

    private void initializePlayer(Uri uri) {

        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();

        playerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.question_mark));
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        playerView.setPlayer(exoPlayer);

        exoPlayer.addListener(this);

        String userAgent = com.google.android.exoplayer2.util.Util.getUserAgent(this, "Bake");
        MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(this, userAgent),
                new DefaultExtractorsFactory(), null, null);
        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        desc.setText(ItemListActivity.recipesPojoModelList.get(savedInstanceState.getInt("stateId")).getSteps().get(savedInstanceState.getInt("Id")).getDescription() + " ");
        releasePlayer();
        String nextUri = ItemListActivity.recipesPojoModelList.get(savedInstanceState.getInt("stateId")).getSteps().get(savedInstanceState.getInt("Id")).getVideoURL();
        initializePlayer(Uri.parse(nextUri));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt("Id", id);
        outState.putInt("stateId", bakeId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        flag = false;
    }
}
