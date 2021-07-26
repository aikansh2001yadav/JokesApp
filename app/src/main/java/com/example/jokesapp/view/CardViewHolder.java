package com.example.jokesapp.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jokesapp.R;

import org.jetbrains.annotations.NotNull;

public class CardViewHolder extends RecyclerView.ViewHolder{
    /**
     * Storing the reference of TextView in content member field
     */
    private TextView content;
    /**
     * Storing the reference of ImageButton in likeButton member field
     */
    private ImageButton likeButton;
    /**
     * Storing the reference of ImageButton in shareButton member field
     */
    private ImageButton shareButton;

    public CardViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        //Initialising various views
        content = itemView.findViewById(R.id.content);
        likeButton = itemView.findViewById(R.id.imageButton);
        shareButton = itemView.findViewById(R.id.imageButton2);
    }


    /**
     * @return reference to content textView
     */
    public TextView getTextView() {
        return content;
    }

    /**
     * @return reference to likeButton
     */

    public ImageButton getLikeButton() {
        return likeButton;
    }

    /**
     * @return reference to shareButton
     */
    public ImageButton getShareButton() {
        return shareButton;
    }
}
