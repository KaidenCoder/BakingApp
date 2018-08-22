package com.example.khaidemsandipsingha.bakingappudacity.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.khaidemsandipsingha.bakingappudacity.R;
import com.example.khaidemsandipsingha.bakingappudacity.adapter.RecipeDetailAdapter;
import com.example.khaidemsandipsingha.bakingappudacity.RecipeIntiliaze.Recipe;
import com.example.khaidemsandipsingha.bakingappudacity.RecipeIntiliaze.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailAdapter.ListItemClickListener,RecipeStepDetailFragment.ListItemClickListener{

    private static String STACK_RECIPE_DETAIL="STACK_RECIPE_DETAIL";
    private static String STACK_RECIPE_STEP_DETAIL="STACK_RECIPE_STEP_DETAIL";
    String recipeName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setContentView(R.layout.activity_recipe_detail);

        if (savedInstanceState == null) {

            Bundle selectedRecipeBundle = getIntent().getExtras();

            ArrayList<Recipe> recipe;
            String SELECTED_RECIPES = "Selected_Recipes";
            assert selectedRecipeBundle != null;
            recipe = selectedRecipeBundle.getParcelableArrayList(SELECTED_RECIPES);
            assert recipe != null;
            recipeName = recipe.get(0).getName();

     final RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(selectedRecipeBundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment).addToBackStack(STACK_RECIPE_DETAIL)
                    .commit();

            if (findViewById(R.id.recipe_linear_layout).getTag()!=null && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet-land")) {

                final RecipeStepDetailFragment fragment2 = new RecipeStepDetailFragment();
                fragment2.setArguments(selectedRecipeBundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container2, fragment2).addToBackStack(STACK_RECIPE_STEP_DETAIL)
                        .commit();
                }
        } else {
        recipeName= savedInstanceState.getString("Title");
    }


        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(recipeName);

        myToolbar.setNavigationOnClickListener(v -> {
            FragmentManager fm = getSupportFragmentManager();
            if (findViewById(R.id.fragment_container2)==null) {
                if (fm.getBackStackEntryCount() > 1) {
                    //go back to "Recipe Detail" screen
                    fm.popBackStack(STACK_RECIPE_DETAIL, 0);
                } else if (fm.getBackStackEntryCount() > 0) {
                    //go back to "Recipe" screen
                    finish();
                    }
            }
            else {

                //go back to "Recipe" screen
                finish();
                }

        });
    }

    @Override
    public void onListItemClick(List<Step> stepsOut, int selectedItemIndex,String recipeName) {

        final RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        Objects.requireNonNull(getSupportActionBar()).setTitle(recipeName);

        Bundle stepBundle = new Bundle();
        String SELECTED_STEPS = "Selected_Steps";
        stepBundle.putParcelableArrayList(SELECTED_STEPS,(ArrayList<Step>) stepsOut);
        String SELECTED_INDEX = "Selected_Index";
        stepBundle.putInt(SELECTED_INDEX,selectedItemIndex);
        stepBundle.putString("Title",recipeName);
        fragment.setArguments(stepBundle);

        if (findViewById(R.id.recipe_linear_layout).getTag()!=null && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet-land")) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container2, fragment).addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit();
        }
        else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment).addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
       super.onSaveInstanceState(outState);
        outState.putString("Title",recipeName);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }



}
