package com.example.jokesapp.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jokesapp.R;
import com.zerobranch.layout.SwipeLayout;

public class FavoriteJokeViewHolder extends RecyclerView.ViewHolder{
    /**
     * Stores the reference to right view
     */
    private ImageView rightView;
    /**
     * Stores the reference of swipeLayout
     */
    private SwipeLayout swipeLayout;
    /**
     * Stores the reference to favoriteJokeTextView
     */
    private TextView favoriteJokeTextView;
    public FavoriteJokeViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
        super(itemView);
        //Initialising various views
        favoriteJokeTextView = itemView.findViewById(R.id.favoriteJokeTextView);
        swipeLayout = itemView.findViewById(R.id.swipe_layout);
        rightView = itemView.findViewById(R.id.right_view);
    }

    /**
     * @return reference to favoriteJokeTextView
     */
    public TextView getFavoriteJokeTextView() {
        return favoriteJokeTextView;
    }

    /**
     * @return reference to swipeLayout
     */
    public SwipeLayout getSwipeLayout() {
        return swipeLayout;
    }

    /**
     * @return reference to rightView
     */
    public ImageView getRightView() {
        return rightView;
    }
}
