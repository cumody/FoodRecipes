package com.mahmoudshaaban.foodrecipes;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.mahmoudshaaban.foodrecipes.adapters.OnRecipeListener;
import com.mahmoudshaaban.foodrecipes.adapters.RecipeRecyclerAdapter;
import com.mahmoudshaaban.foodrecipes.models.Recipe;

import com.mahmoudshaaban.foodrecipes.util.Testing;
import com.mahmoudshaaban.foodrecipes.util.VerticalSpacingItemDecorator;
import com.mahmoudshaaban.foodrecipes.viewmodels.RecipeListViewModel;


import java.util.List;


public class RecipeListActivity extends BaseActivity implements OnRecipeListener {
    private static final String TAG = "RecipeListActivity";

    private RecipeListViewModel mRecipeListViewModel;
    private RecyclerView mRecyclerview;
    private RecipeRecyclerAdapter mAdapter;
    private SearchView mSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_lsit);

        mRecyclerview = findViewById(R.id.recipe_list);
        mSearchView = findViewById(R.id.search_view);
        setSupportActionBar(findViewById(R.id.toolbar));

        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);

        initRecyclerView();
        subscribeObservers();
        initSearchView();
        if (!mRecipeListViewModel.isViewingRecipes()){
            // dispaly search categories
            displaysearchCategories();

        }




    }


    public void subscribeObservers() {
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                // if we do back pressed and go back to our app it make issues
                // to avoid that we make if statment like below
                // don't forget to override onBackPressed method ...
                if (recipes != null) {
                    if (mRecipeListViewModel.isViewingRecipes()){
                        mRecipeListViewModel.setIsPerformingQuery(false);
                        Testing.printRecipes(recipes, "recipes test");
                        mAdapter.setRecipes(recipes);
                    }


                }


            }
        });

        mRecipeListViewModel.isQueryExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Log.d(TAG, "onChanged: the query is exhausted ..  ");
                    mAdapter.setQueryExhausted();
                }
            }
        });
    }




    private void initRecyclerView() {
        mAdapter = new RecipeRecyclerAdapter(this);
        mRecyclerview.setAdapter(mAdapter);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        mRecyclerview.addItemDecoration(itemDecorator);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                // it means if it the bottom of the list
                if (!mRecyclerview.canScrollVertically(1)){
                     // search the next page
                    mRecipeListViewModel.searchNextPage();
                }
            }
        });
    }




    private void initSearchView(){
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.displayLoading();
                mRecipeListViewModel.searchRecipeApi(query, 1);
                mSearchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public void onRecipeClick(int position) {
        Log.d(TAG, "onRecipeClick: clicked. " + position);
        Intent intent = new Intent(RecipeListActivity.this,RecipeActivity.class);
        intent.putExtra("recipe" , mAdapter.getSelectedRecipe(position));
        startActivity(intent);

    }

    @Override
    public void onCategoryClick(String category) {
        mAdapter.displayLoading();
        mRecipeListViewModel.searchRecipeApi(category, 1);
        mSearchView.clearFocus();

    }

    public void displaysearchCategories(){
        mRecipeListViewModel.setIsViewingRecipes(false);
        mAdapter.displaySearchCategories();
    }

    @Override
    public void onBackPressed() {



        // if it returning true that means it returning categories activity
        if (mRecipeListViewModel.onBackPressed()){
            super.onBackPressed();

        } else {
            // if not go to activity
            displaysearchCategories();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_item_menu,menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.categroies_item){
            displaysearchCategories();
        }
        return super.onOptionsItemSelected(item);
    }
}