package com.example.jokesapp.model;

public class Joke {
    /**
     * Stores jokeText string
     */
    private String jokeText;
    /**
     * Storing boolean value representing whether joke is liked
     */
    private boolean jokeIsLiked;

    /**
     * Constructor initialising various member fields
     * @param jokeText
     * @param jokeIsLiked
     */
    public Joke(String jokeText, boolean jokeIsLiked)
    {
        this.jokeText = jokeText;
        this.jokeIsLiked = jokeIsLiked;
    }

    /**
     * @return jokeText string
     */
    public String getJokeText() {
        return jokeText;
    }

    /**
     * @return boolean value representing whether joke is liked or not
     */
    public boolean isJokeLiked() {
        return jokeIsLiked;
    }
}
