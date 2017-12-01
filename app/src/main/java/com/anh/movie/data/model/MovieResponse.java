package com.anh.movie.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by anh on 11/30/2017.
 */

public class MovieResponse {
    @SerializedName("page")
    @Expose
    private int mPage;
    @SerializedName("result")
    @Expose
    private List<Movie> mMovies;

    public List<Movie> getMovies() {
        return mMovies;
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
    }
}
