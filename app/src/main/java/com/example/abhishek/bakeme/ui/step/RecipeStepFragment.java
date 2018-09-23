package com.example.abhishek.bakeme.ui.step;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abhishek.bakeme.R;
import com.example.abhishek.bakeme.models.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeStepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeStepFragment extends Fragment {
    private static final String TAG = RecipeStepFragment.class.getSimpleName();
    private static final String ARG_STEP = "current-step";
    private static final String ARG_CURRENT_PLAYER_POSITION = "current-position";

    private Step currentStep;
    private long position;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mSimpleExoPlayerView;
    private Snackbar noVideoSnackbar;

    public RecipeStepFragment() {
        // Required empty public constructor
    }

    public static RecipeStepFragment newInstance(Step currentStep) {
        RecipeStepFragment fragment = new RecipeStepFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_STEP, currentStep);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentStep = getArguments().getParcelable(ARG_STEP);
        }

        if (savedInstanceState != null
                && savedInstanceState.containsKey(ARG_CURRENT_PLAYER_POSITION)) {
            position = savedInstanceState.getLong(ARG_CURRENT_PLAYER_POSITION);
            Log.d(TAG, "Retrieved current position: " + position);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        // Find and set Exo Player view
        mSimpleExoPlayerView = view.findViewById(R.id.playerView);

        // Finds and sets thumbnail to SimpleExoPlayerView
        setThumbnail();

        // Set step description for current step
        final TextView tvStepDescription = view.findViewById(R.id.tv_step_instruction);
        tvStepDescription.setText(currentStep.getDescription());

        // Setup Snackbar
        CoordinatorLayout coordinatorLayout = view.findViewById(R.id.coordinator_layout);
        noVideoSnackbar = Snackbar.make(
                coordinatorLayout,
                getString(R.string.error_no_video),
                Snackbar.LENGTH_LONG
        );

        return view;
    }

    private void setThumbnail() {
        String thumbnailUrl = currentStep.getThumbnailURL();
        if (thumbnailUrl != null && !TextUtils.isEmpty(thumbnailUrl)) {
            Picasso.get()
                    .load(thumbnailUrl)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            mSimpleExoPlayerView.setDefaultArtwork(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }
                    });
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            Log.d(TAG, "Initialised Player");
            Context context = getContext();
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            mSimpleExoPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(context, "BakeMe");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    context, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.seekTo(position);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(ARG_CURRENT_PLAYER_POSITION, position);
        super.onSaveInstanceState(outState);
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String videoUrl = currentStep.getVideoURL();
        if (videoUrl != null && !TextUtils.isEmpty(videoUrl)) {
            initializePlayer(Uri.parse(videoUrl));
        } else {
            noVideoSnackbar.show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            position = mExoPlayer.getCurrentPosition();
            releasePlayer();
        }
        noVideoSnackbar = null;
    }
}
