package com.example.khaidemsandipsingha.bakingappudacity.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import com.example.khaidemsandipsingha.bakingappudacity.IdleResource.SimpleIdleResource;

import com.example.khaidemsandipsingha.bakingappudacity.R;
import com.example.khaidemsandipsingha.bakingappudacity.adapter.RecipeAdapter;
import com.example.khaidemsandipsingha.bakingappudacity.RecipeIntiliaze.Recipe;
import com.example.khaidemsandipsingha.bakingappudacity.retrofit.RetrofitBuilder;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.khaidemsandipsingha.bakingappudacity.ui.RecipeActivity.ALL_RECIPES;

public class RecipeFragment extends Fragment  {

    public RecipeFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView;

        View rootView = inflater.inflate(R.layout.recipe_fragment_body_part, container, false);

        recyclerView = rootView.findViewById(R.id.recipe_recycler);
        RecipeAdapter recipesAdapter =new RecipeAdapter((RecipeActivity)getActivity());
        recyclerView.setAdapter(recipesAdapter);

        if (rootView.getTag()!=null && rootView.getTag().equals("phone-land")){
            GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),4);
            recyclerView.setLayoutManager(mLayoutManager);
        }
        else {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
        }

        RetrofitBuilder.IRecipe iRecipe = RetrofitBuilder.Retrieve();
        Call<ArrayList<Recipe>> recipe = iRecipe.getRecipe();

        SimpleIdleResource idlingResource = (SimpleIdleResource)((RecipeActivity) Objects.requireNonNull(getActivity())).getIdlingResource();

        idlingResource.setIdleState(false);

        recipe.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                Integer statusCode = response.code();
                Log.v("status code: ", statusCode.toString());

                ArrayList<Recipe> recipes = response.body();

                Bundle recipesBundle = new Bundle();
                recipesBundle.putParcelableArrayList(ALL_RECIPES, recipes);

                recipesAdapter.setRecipeData(recipes,getContext());
                idlingResource.setIdleState(true);

            }
            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.v("http fail: ", t.getMessage());
            }
        });

        return rootView;
    }


}
