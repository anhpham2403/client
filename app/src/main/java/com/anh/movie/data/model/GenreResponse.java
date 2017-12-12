package com.anh.movie.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by anh on 12/3/2017.
 */

public class GenreResponse {
    @SerializedName("genres")
    private List<Genre> mGenre;

    public List<Genre> getGenre() {
        return mGenre;
    }

    public void setGenre(List<Genre> genre) {
        mGenre = genre;
    }
}
