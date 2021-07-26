package com.example.jokesapp.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Map;

public class JokeManager {
    /**
     * Stores the reference to the context of MainActivity
     */
    private Context context;
    /**
     * Stores the reference to sharedPreferences
     */
    private SharedPreferences sharedPreferences;

    /**
     * Constructor initialising member fields
     * @param context
     */
    public JokeManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("FavoriteJokes", 0);
    }

    /**
     * Adding favorite joke to sharedPreferences
     * @param joke
     */
    public void addJoke(Joke joke)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(joke.getJokeText(), joke.isJokeLiked());
        editor.apply();
    }

    /**
     * Removing favorite joke from sharedPreferences
     * @param joke
     */
    public void removeJoke(Joke joke)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(joke.getJokeText());
        editor.commit();
    }

    /**
     *
     * @return list of favorite jokes
     */
    public ArrayList<String> retrieveJokes()
    {
        ArrayList<String> jokesList = new ArrayList<String>();
        Map<String, ?> jokes = sharedPreferences.getAll();

        for(String jokeText: jokes.keySet())
        {
            jokesList.add(jokeText);
        }
        return jokesList;
    }
}
