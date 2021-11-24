package com.example.jokesapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jokesapp.fragments.FavoriteJokesFragment;
import com.example.jokesapp.R;

public class FavoriteJokesActivity extends AppCompatActivity {

    /**
     * Storing the reference of FavoriteJokesFragment
     */
    private FavoriteJokesFragment favoriteJokesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_jokes);

        //Creating and initialising an instance of FavoriteJokesFragment
        favoriteJokesFragment = new FavoriteJokesFragment();
        //Setting title bar to be Favorite Jokes
        getSupportActionBar().setTitle("Favorite Jokes");
        //Using supportFragmentManager to start fragment
        getSupportFragmentManager().
                beginTransaction().
                add(R.id.placeOrder, favoriteJokesFragment, null)
                .commit();
    }
}