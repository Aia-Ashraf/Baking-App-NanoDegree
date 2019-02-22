package com.example.aiaashraf.bakingapplication;

import com.example.aiaashraf.bakingapplication.dummy.RecipesPojoModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<ArrayList<RecipesPojoModel>> getRecipes();
}
