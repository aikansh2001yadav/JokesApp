package com.example.jokesapp.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jokesapp.R;
import com.example.jokesapp.model.Joke;
import com.example.jokesapp.model.JokeManager;
import com.example.jokesapp.view.FavoriteJokeViewHolder;
import com.google.android.material.snackbar.Snackbar;
import com.zerobranch.layout.SwipeLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;

public class FavoriteJokesAdapter extends RecyclerView.Adapter<FavoriteJokeViewHolder>{

    /**
     * Stores the reference to favorite jokes_cardout layout
     */
    private View view;
    /**
     * Stores the list of favorite jokes list
     */
    private ArrayList<String> jokesList;
    /**
     * Stores the context to Favorite Jokes Activity
     */
    private Context context;

    /**
     * Constructor initialising context and jokesList member variables
     * @param context
     * @param jokesList
     */
    public FavoriteJokesAdapter(Context context, ArrayList<String> jokesList)
    {
        this.context = context;
        this.jokesList = jokesList;
    }

    @NonNull
    @NotNull
    @Override
    public FavoriteJokeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_joke_card_layout, parent, false);
        this.view = view;
        return new FavoriteJokeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FavoriteJokeViewHolder holder, int position) {
        //Setting favorite joke text to TextView
        holder.getFavoriteJokeTextView().setText(jokesList.get(position));

        SwipeLayout swipeLayout = holder.getSwipeLayout();
        ImageView rightView = holder.getRightView();
        //Setting onClickListener on swipeLayout
        swipeLayout.setOnActionsListener(new SwipeLayout.SwipeActionsListener() {
            @Override
            public void onOpen(int direction, boolean isContinuous) {
                if (direction == SwipeLayout.LEFT && isContinuous) {
                    if (position != NO_POSITION) {
                        //Removing current joke from recycler view
                        remove(position);
                    }
                }
            }

            @Override
            public void onClose() {
            }
        });

        //Setting on click listener on right view
        rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != NO_POSITION) {
                    //Removing current joke from recycler view
                    remove(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return jokesList.size();
    }

    /**
     * Removes current joke from recycler view
     * @param position
     */
    public void remove(int position) {
        //Getting current joke
        Joke joke = new Joke(jokesList.get(position), true);
        //Removing current joke from jokesList
        jokesList.remove(position);
        //Notifying recycler view
        notifyItemRemoved(position);

        //Creating and initialising an instance of JokeManager object
        JokeManager jokeManager = new JokeManager(context);
        //Removing current joke from JokeManger
        jokeManager.removeJoke(joke);

        //Making a snackbar
        Snackbar snackbar
                = Snackbar
                .make(
                        view,
                        "Message is deleted",
                        Snackbar.LENGTH_LONG)
                .setAction(
                        "UNDO",

                        // If the Undo button
                        // is pressed, show
                        // the message using Toast
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                //Adding joke again to the jokeManger
                                jokeManager.addJoke(joke);
                                //Adding joke again to jokesList
                                jokesList.add(joke.getJokeText());
                                //Notifying recycler view about the new item inserted
                                notifyItemInserted(position);
                            }
                        });

        //Showing snackbar
        snackbar.show();
    }
}
