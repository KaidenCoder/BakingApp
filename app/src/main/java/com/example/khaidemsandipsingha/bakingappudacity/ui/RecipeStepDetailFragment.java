package com.example.khaidemsandipsingha.bakingappudacity.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.khaidemsandipsingha.bakingappudacity.R;
import com.example.khaidemsandipsingha.bakingappudacity.RecipeIntiliaze.Recipe;
import com.example.khaidemsandipsingha.bakingappudacity.RecipeIntiliaze.Step;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import static com.example.khaidemsandipsingha.bakingappudacity.ui.RecipeActivity.SELECTED_INDEX;
import static com.example.khaidemsandipsingha.bakingappudacity.ui.RecipeActivity.SELECTED_RECIPES;
import static com.example.khaidemsandipsingha.bakingappudacity.ui.RecipeActivity.SELECTED_STEPS;
import static com.example.khaidemsandipsingha.bakingappudacity.ui.RecipeActivity.SELECTED_POSITION;

public class RecipeStepDetailFragment extends Fragment  {
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private BandwidthMeter bandwidthMeter;
    private ArrayList<Step> steps = new ArrayList<>();
    private int selectedIndex;
    private Handler mainHandler;
    private String recipeName;
    private long position;

    public RecipeStepDetailFragment() {

    }

   private ListItemClickListener itemClickListener;

    public interface ListItemClickListener {
        void onListItemClick(List<Step> allSteps,int Index,String recipeName);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView;
        mainHandler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();

        itemClickListener =(RecipeDetailActivity)getActivity();

        ArrayList<Recipe> recipe = new ArrayList<>();

        position = C.TIME_UNSET;
        if(savedInstanceState != null) {
            steps = savedInstanceState.getParcelableArrayList(SELECTED_STEPS);
            selectedIndex = savedInstanceState.getInt(SELECTED_INDEX);
            recipeName = savedInstanceState.getString("Title");
            position = savedInstanceState.getLong(SELECTED_POSITION, C.TIME_UNSET);

        }
        else {
            assert getArguments() != null;
            steps =getArguments().getParcelableArrayList(SELECTED_STEPS);
            if (steps!=null) {
                steps =getArguments().getParcelableArrayList(SELECTED_STEPS);
                selectedIndex=getArguments().getInt(SELECTED_INDEX);
                recipeName=getArguments().getString("Title");
            }
            else {
                recipe =getArguments().getParcelableArrayList(SELECTED_RECIPES);
                //casting List to ArrayList
                assert recipe != null;
                steps=(ArrayList<Step>) recipe.get(0).getSteps();
                selectedIndex=0;
            }

        }



        View rootView = inflater.inflate(R.layout.recipe_step_detail_fragment_body_part, container, false);
        textView = rootView.findViewById(R.id.recipe_step_detail_text);
        textView.setText(steps.get(selectedIndex).getDescription());
        textView.setVisibility(View.VISIBLE);

        simpleExoPlayerView = rootView.findViewById(R.id.playerView);
        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        String videoURL = steps.get(selectedIndex).getVideoURL();

        if (rootView.findViewWithTag("sw600dp-port-recipe_step_detail")!=null) {
           recipeName=((RecipeDetailActivity) Objects.requireNonNull(getActivity())).recipeName;
           Objects.requireNonNull(((RecipeDetailActivity) getActivity()).getSupportActionBar()).setTitle(recipeName);
        }

        String imageUrl=steps.get(selectedIndex).getThumbnailURL();
        if (!imageUrl.equals("")) {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            ImageView thumbImage = rootView.findViewById(R.id.thumbImage);
            Picasso.with(getContext()).load(builtUri).into(thumbImage);
        }

        if (!videoURL.isEmpty()) {


            initializePlayer(Uri.parse(steps.get(selectedIndex).getVideoURL()));

           if (rootView.findViewWithTag("sw600dp-land-recipe_step_detail")!=null) {
                getActivity().findViewById(R.id.fragment_container2).setLayoutParams(new LinearLayout.LayoutParams(-1,-2));
                simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
            }
            else if (isInLandscapeMode(Objects.requireNonNull(getContext()))){
                textView.setVisibility(View.GONE);
            }
        }
        else {
            player=null;
            simpleExoPlayerView.setForeground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.ic_visibility_off_white_36dp));
            simpleExoPlayerView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
        }


        Button mPrevStep = rootView.findViewById(R.id.previousStep);
        Button mNextstep = rootView.findViewById(R.id.nextStep);

        mPrevStep.setOnClickListener(view -> {
        if (steps.get(selectedIndex).getId() > 0) {
            if (player!=null){
                player.stop();
            }
            itemClickListener.onListItemClick(steps,steps.get(selectedIndex).getId() - 1,recipeName);
        }
        else {
            Toast.makeText(getActivity(),"You already are in the First step of the recipe", Toast.LENGTH_SHORT).show();

        }
    });

        mNextstep.setOnClickListener(view -> {

        int lastIndex = steps.size()-1;
        if (steps.get(selectedIndex).getId() < steps.get(lastIndex).getId()) {
            if (player!=null){
                player.stop();
            }
            itemClickListener.onListItemClick(steps,steps.get(selectedIndex).getId() + 1,recipeName);
        }
        else {
            Toast.makeText(getContext(),"You already are in the Last step of the recipe", Toast.LENGTH_SHORT).show();

        }
    });




        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (player == null || (position != C.TIME_UNSET)) {
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();

            player = ExoPlayerFactory.newSimpleInstance(Objects.requireNonNull(getContext()), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(player);

            String userAgent = Util.getUserAgent(getContext(), "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            player.seekTo(position);
            player.prepare(mediaSource, false, false);
            player.setPlayWhenReady(true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(SELECTED_STEPS,steps);
        currentState.putInt(SELECTED_INDEX,selectedIndex);
        currentState.putString("Title",recipeName);
        currentState.putLong(SELECTED_POSITION, position);
    }

    private boolean isInLandscapeMode(Context context) {
        return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    @Override
    public void onResume() {
        Uri videoUri = Uri.parse(steps.get(selectedIndex).getVideoURL());
        super.onResume();
        if (videoUri != null)
            initializePlayer(videoUri);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player!=null) {
            position = player.getCurrentPosition();
            player.stop();

        }
    }


}
