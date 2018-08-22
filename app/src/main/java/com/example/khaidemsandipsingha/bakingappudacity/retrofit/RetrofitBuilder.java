package com.example.khaidemsandipsingha.bakingappudacity.retrofit;

import com.example.khaidemsandipsingha.bakingappudacity.RecipeIntiliaze.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public final class RetrofitBuilder {

    public static IRecipe Retrieve() {

        Gson gson = new GsonBuilder().create();

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();


        return new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(httpClientBuilder.build())
                .build().create(IRecipe.class);
    }

    public interface IRecipe {
        @GET("baking.json")
        Call<ArrayList<Recipe>> getRecipe();
    }
}

