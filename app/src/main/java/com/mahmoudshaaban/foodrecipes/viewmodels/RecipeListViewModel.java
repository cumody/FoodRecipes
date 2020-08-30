package com.mahmoudshaaban.foodrecipes.viewmodels;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mahmoudshaaban.foodrecipes.models.Recipe;
import com.mahmoudshaaban.foodrecipes.repositories.RecipeRepository;

import java.util.List;


public class RecipeListViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;
    private boolean mIsViewingRecipes;
    private boolean mIsPerformingQuery;


    public RecipeListViewModel() {
        mRecipeRepository = RecipeRepository.getInstance();
        mIsViewingRecipes = false;
        mIsPerformingQuery = false;
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipeRepository.getRecipes();
    }


    public void searchRecipeApi(String query , int pageNumber){

        mIsViewingRecipes = true;
        mRecipeRepository.searchRecipeApi(query,pageNumber);

    }

    public LiveData<Boolean> isQueryExhausted(){
        return mRecipeRepository.isQueryExhausted();
    }

    public void searchNextPage(){
        if (!mIsPerformingQuery && mIsViewingRecipes && !isQueryExhausted().getValue()){
            mRecipeRepository.searchNextPage();
        }

    }



    public boolean isViewingRecipes(){
        return mIsViewingRecipes ;
    }
    public void setIsViewingRecipes( boolean IsViewingRecipes){
        mIsViewingRecipes = IsViewingRecipes;


    }

    public void setIsPerformingQuery(boolean isPerformingQuery){
        mIsPerformingQuery = isPerformingQuery;
    }
    public boolean isPerformingQuery(){
        return mIsPerformingQuery;
    }

    public boolean onBackPressed(){

        if (mIsPerformingQuery){
            // cancel query
            mIsPerformingQuery = false;
            mRecipeRepository.cancelRequest();

        }

        if (mIsViewingRecipes){
            // if it reviewing the recipes make it false
            mIsViewingRecipes = false;
            return false;
        }
        // if not make it true
        return true;

    }

}
