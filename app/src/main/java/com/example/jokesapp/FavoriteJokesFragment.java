package com.example.jokesapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jokesapp.controller.FavoriteJokesAdapter;
import com.example.jokesapp.model.JokeManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteJokesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteJokesFragment extends Fragment {
    /**
     * Stores the reference of recyclerView which shows list of favorite jokes
     */
    private RecyclerView recyclerView;
    /**
     * Stores the reference of favorite jokes list
     */
    private  ArrayList<String> mJokesList;

    public FavoriteJokesFragment() {
        // Required empty public constructor
    }

    /**
     * @return a new instance of FavoriteJokesFragment
     */
    public static FavoriteJokesFragment newInstance() {
        FavoriteJokesFragment fragment = new FavoriteJokesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        //Creating and initialising an instance of JokeManager
        JokeManager jokeManager = new JokeManager(context);
        //Retrieving favorite jokes list from jokeManager
        mJokesList = jokeManager.retrieveJokes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favorite_jokes, container, false);
        //Initialising recyclerView
        recyclerView = view.findViewById(R.id.favoriteJokesRecyclerView);
        //Creating an instance of FavoriteJokesAdapter
        FavoriteJokesAdapter favoriteJokesAdapter = new FavoriteJokesAdapter(getContext(), mJokesList);
        //Setting an adapter to recyclerView
        recyclerView.setAdapter(favoriteJokesAdapter);
        //Setting linear layout manager on recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //Setting nested scrolling enabled to false
        recyclerView.setNestedScrollingEnabled(false);
        //Returning view
        return view;
    }
}