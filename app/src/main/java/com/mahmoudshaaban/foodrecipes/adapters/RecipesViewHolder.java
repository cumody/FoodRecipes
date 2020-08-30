package com.mahmoudshaaban.foodrecipes.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoudshaaban.foodrecipes.R;

public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title , publisher , socialScore ;
    AppCompatImageView imaage;
    OnRecipeListener onRecipeListener;

    public RecipesViewHolder(@NonNull View itemView , OnRecipeListener onRecipeListener) {
        super(itemView);
        this.onRecipeListener = onRecipeListener;
        title = itemView.findViewById(R.id.recipe_title);
        publisher = itemView.findViewById(R.id.recipe_publisher);
        socialScore = itemView.findViewById(R.id.recipe_social_score);
        imaage = itemView.findViewById(R.id.recipe_image);


        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onRecipeListener.onRecipeClick(getAdapterPosition());
    }
}
