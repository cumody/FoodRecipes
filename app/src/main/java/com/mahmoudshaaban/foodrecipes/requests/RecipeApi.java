package com.mahmoudshaaban.foodrecipes.requests;

import com.mahmoudshaaban.foodrecipes.requests.responses.RecipeResponse;
import com.mahmoudshaaban.foodrecipes.requests.responses.RecipeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApi {

    @GET("api/search")
    Call<RecipeSearchResponse> searchRecipe(
        @Query("q") String query ,
        @Query("page") String page
    );

    @GET("api/get")
    Call<RecipeResponse> getRecipe(
            @Query("rId") String recipe_id
    );
}
