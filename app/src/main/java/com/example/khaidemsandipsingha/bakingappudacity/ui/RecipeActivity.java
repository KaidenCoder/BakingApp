package com.example.khaidemsandipsingha.bakingappudacity.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.khaidemsandipsingha.bakingappudacity.IdleResource.SimpleIdleResource;
import com.example.khaidemsandipsingha.bakingappudacity.R;
import com.example.khaidemsandipsingha.bakingappudacity.adapter.RecipeAdapter;
import com.example.khaidemsandipsingha.bakingappudacity.RecipeIntiliaze.Recipe;
import java.util.ArrayList;
import java.util.Objects;

public class RecipeActivity extends AppCompatActivity implements RecipeAdapter.ListItemClickListener{

    static String ALL_RECIPES="All_Recipes";
    static String SELECTED_RECIPES="Selected_Recipes";
    static String SELECTED_STEPS="Selected_Steps";
    static String SELECTED_INDEX="Selected_Index";
    static String SELECTED_POSITION = "position";

    @Nullable
    private SimpleIdleResource mSimpleIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdleResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mSimpleIdlingResource == null) {
            mSimpleIdlingResource = new SimpleIdleResource();
        }
        return mSimpleIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setContentView(R.layout.activity_recipe);

       Toolbar myToolbar = findViewById(R.id.my_toolbar);
       setSupportActionBar(myToolbar);
       Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(false);
       getSupportActionBar().setDisplayHomeAsUpEnabled(false);
       getSupportActionBar().setTitle("Baking Time");

// Get the IdleResource instance
        getIdlingResource();
    }

    @Override
    public void onListItemClick(Recipe selectedItemIndex) {

        Bundle selectedRecipeBundle = new Bundle();
        ArrayList<Recipe> selectedRecipe = new ArrayList<>();
        selectedRecipe.add(selectedItemIndex);
        selectedRecipeBundle.putParcelableArrayList(SELECTED_RECIPES,selectedRecipe);

        final Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtras(selectedRecipeBundle);
        startActivity(intent);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
       super.onSaveInstanceState(outState);
    }

}
