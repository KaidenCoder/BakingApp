package com.example.khaidemsandipsingha.bakingappudacity.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.khaidemsandipsingha.bakingappudacity.R;
import com.example.khaidemsandipsingha.bakingappudacity.adapter.RecipeDetailAdapter;
import com.example.khaidemsandipsingha.bakingappudacity.RecipeIntiliaze.Ingredient;
import com.example.khaidemsandipsingha.bakingappudacity.RecipeIntiliaze.Recipe;
import com.example.khaidemsandipsingha.bakingappudacity.widget.UpdateBakingService;
import java.util.ArrayList;
import java.util.List;
import static com.example.khaidemsandipsingha.bakingappudacity.ui.RecipeActivity.SELECTED_RECIPES;

public class RecipeDetailFragment extends Fragment  {

    private ArrayList<Recipe> recipe;
    private String recipeName;

    public RecipeDetailFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView;
        TextView textView;

        recipe = new ArrayList<>();

        if(savedInstanceState != null) {
            recipe = savedInstanceState.getParcelableArrayList(SELECTED_RECIPES);

        }
        else {
            assert getArguments() != null;
            recipe =getArguments().getParcelableArrayList(SELECTED_RECIPES);
        }

        assert recipe != null;
        List<Ingredient> ingredients = recipe.get(0).getIngredients();
        recipeName=recipe.get(0).getName();

        View rootView = inflater.inflate(R.layout.recipe_detail_fragment_body_part, container, false);
        textView = rootView.findViewById(R.id.recipe_detail_text);

        ArrayList<String> recipeIngredientsForWidgets= new ArrayList<>();


        ingredients.forEach((a) ->
            {
                textView.append("\u2022 "+ a.getIngredient()+"\n");
                textView.append("\t\t\t Quantity: "+a.getQuantity().toString()+"\n");
                textView.append("\t\t\t Measure: "+a.getMeasure()+"\n\n");

                recipeIngredientsForWidgets.add(a.getIngredient()+"\n"+
                        "Quantity: "+a.getQuantity().toString()+"\n"+
                        "Measure: "+a.getMeasure()+"\n");
            });

        recyclerView = rootView.findViewById(R.id.recipe_detail_recycler);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        RecipeDetailAdapter mRecipeDetailAdapter =new RecipeDetailAdapter((RecipeDetailActivity)getActivity());
        recyclerView.setAdapter(mRecipeDetailAdapter);
        mRecipeDetailAdapter.setMasterRecipeData(recipe,getContext());

        //update widget
        UpdateBakingService.startBakingService(getContext(),recipeIngredientsForWidgets);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(SELECTED_RECIPES, recipe);
        currentState.putString("Title",recipeName);
    }


}


