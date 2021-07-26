package com.example.jokesapp.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jokesapp.R;
import com.example.jokesapp.model.Joke;
import com.example.jokesapp.view.CardViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CardStackDataAdapter extends RecyclerView.Adapter<CardViewHolder> {

    /**
     * Stores boolean value representing whether joke is already liked or not
     */
    private boolean clicked;
    /**
     * Stores the reference to the context of MainActivity
     */
    private Context context;
    /**
     * Stores list of jokes to show in cardStackView
     */
    private ArrayList<String> jokesList;
    /**
     * Stores the reference to MainActivity which implements likeIsClicked interface
     */
    private LikeIsClicked interfaceObject;

    /**
     * Constructor initialising various member fields
     * @param context
     * @param jokesList
     * @param interfaceObject
     */
    public CardStackDataAdapter(Context context, ArrayList<String> jokesList, LikeIsClicked interfaceObject)
   {
       this.context = context;
       this.jokesList = jokesList;
       this.interfaceObject = interfaceObject;
   }
    @NonNull
    @NotNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jokes_cardstack_layout, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CardViewHolder holder, int position) {

        //Setting jokeText as a text in textView
        holder.getTextView().setText(jokesList.get(position));

        SharedPreferences sharedPreferences = context.getSharedPreferences("FavoriteJokes", 0);
        clicked = sharedPreferences.getBoolean(jokesList.get(position), false);

        ImageButton likeButton = holder.getLikeButton();
        //If likeButton is already clicked then set image resource of like image on button otherwise set image resource of unlike image
        if(clicked)
        {
            likeButton.setImageResource(R.drawable.like_image);
        }
        else
        {
            likeButton.setImageResource(R.drawable.dislike_image);
        }

        //Setting on Click listener on likeButton and changing image resource on button when the button is clicked
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clicked)
                {
                    likeButton.setImageResource(R.drawable.like_image);
                    clicked = true;
                }
                else
                {
                    likeButton.setImageResource(R.drawable.dislike_image);
                    clicked = false;
                }
                Joke joke = new Joke(jokesList.get(position), clicked);
                interfaceObject.likeIsClicked(joke);
            }
        });

        ImageButton shareButton = holder.getShareButton();
        //Setting onClickListener on shareButton
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating a chooser for sharing jokeText
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String title = "Sharing Joke";
                intent.putExtra(Intent.EXTRA_TEXT, jokesList.get(position));
                intent.setType("text/plain");
                Intent chooser = Intent.createChooser(intent, title);
                chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(chooser);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jokesList.size();
    }
}

