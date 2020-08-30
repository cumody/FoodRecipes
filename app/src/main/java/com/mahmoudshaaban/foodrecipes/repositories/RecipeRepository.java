package com.mahmoudshaaban.foodrecipes.repositories;

import android.os.Parcelable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.mahmoudshaaban.foodrecipes.models.Recipe;
import com.mahmoudshaaban.foodrecipes.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeApiClient mRecipeApiClient;
    private String mQuery;
    private int mPageNumber;

    private MutableLiveData<Boolean> mIsQueryExhastued = new MutableLiveData<>();
    private MediatorLiveData<List<Recipe>> mRecipes = new MediatorLiveData<>();



    public static RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }
    private RecipeRepository(){
        mRecipeApiClient = RecipeApiClient.getInstance();
        initMediators();
    }
    private void initMediators(){
        LiveData<List<Recipe>> recipeListApiSource = mRecipeApiClient.getRecipes();
        mRecipes.addSource(recipeListApiSource, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> list) {
                if (list != null){
                    mRecipes.setValue(list);
                    doneQuery(list);
                } else {
                    // if the data is null so we search it in a database cache
                    doneQuery(null);
                }
            }
        });
    }

    public LiveData<Boolean> isQueryExhausted(){
        return mIsQueryExhastued;

    }


    private void doneQuery(List<Recipe> list){
        if (list != null){
            if (list.size() < 30){
                mIsQueryExhastued.setValue(true);
            }
        } else {
            mIsQueryExhastued.setValue(true);
        }
    }
    public LiveData<List<Recipe>> getRecipes(){
        return mRecipes;
    }
    public LiveData<Recipe> getRecipe(){
        return mRecipeApiClient.getRecipe();
    }


    public LiveData<Boolean> isRequestTimedOut(){
        return mRecipeApiClient.isRequestTimedOut();
    }


    public void searchRecipeApi(String query , int pageNumber){
        if (pageNumber == 0)
        {
            pageNumber = 1;
        }
        mQuery = query;
        mPageNumber = pageNumber;
        mIsQueryExhastued.setValue(false);
        mRecipeApiClient.searchRecipesApi(query,pageNumber);

    }

    public void searchNextPage(){
        searchRecipeApi(mQuery , mPageNumber + 1);

    }

    public void cancelRequest(){
        mRecipeApiClient.cancelRequest();
    }

    public void searchRecipeById(String recipeId){
        mRecipeApiClient.searchRecipeById(recipeId);
    }
}
