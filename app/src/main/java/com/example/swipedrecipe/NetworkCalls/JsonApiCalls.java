package com.example.swipedrecipe.NetworkCalls;

import com.example.swipedrecipe.Models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonApiCalls {

    @GET("reciped9d7b8c.json")
    Call<List<Recipe>> getRecipes();
}
