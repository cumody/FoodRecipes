package com.mahmoudshaaban.foodrecipes.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoudshaaban.foodrecipes.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CircleImageView categoryImage;
    TextView categoryTitle;
    OnRecipeListener mOnRecipeListener;


    public CategoryViewHolder(@NonNull View itemView, OnRecipeListener mOnRecipeListener) {
        super(itemView);
        this.mOnRecipeListener = mOnRecipeListener;
        categoryImage = itemView.findViewById(R.id.category_image);
        categoryTitle = itemView.findViewById(R.id.category_title);

        itemView.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        mOnRecipeListener.onCategoryClick(categoryTitle.getText().toString());

    }
}
