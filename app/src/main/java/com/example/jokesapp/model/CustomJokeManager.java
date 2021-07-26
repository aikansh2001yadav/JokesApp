package com.example.jokesapp.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class CustomJokeManager {
    /**
     * Stores the reference to the context of MainActivity
     */
    private Context context;
    /**
     * Stores the reference to sharedPreferences
     */
    private SharedPreferences sharedPreferences;

    /**
     * Constructor initialising private member fields
     * @param context
     */
    public CustomJokeManager(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("MyPref", 0);
    }

    /**
     * Adding custom joke to sharedPreferences
     * @param category
     * @param jokeText
     */
    public void addJoke(String category, String jokeText)
    {
        HashSet<String> jokesOfCategory;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        jokesOfCategory = new HashSet<String>(sharedPreferences.getStringSet(category, new HashSet<String>()));
        jokesOfCategory.add(jokeText);
        editor.putStringSet(category, jokesOfCategory);
        editor.apply();
    }

    /**
     * @param category
     * @return list of custom jokes
     */
    public ArrayList<String> retrieveJokes(String category)
    {
        return new ArrayList<String>(sharedPreferences.getStringSet(category, new HashSet<String>()));
    }

    /**
     * @return list of categories of custom jokes
     */
    public HashSet<String> retrieveCategories() {

        HashSet<String> categoryList = new HashSet<>();
        Map<String, ?> jokes = sharedPreferences.getAll();
        for(String category: jokes.keySet())
        {
            categoryList.add(category);
        }
        return categoryList;
    }
}
