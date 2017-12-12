package com.anh.movie.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.anh.movie.BR;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anh on 12/5/2017.
 */

public class Character extends BaseObservable {
    @SerializedName("actor")
    private Actor mActor;
    @SerializedName("character")
    private String mCharacter;
    @SerializedName("profile_path")
    private String mProfilePath;
    @SerializedName("movie")
    private Movie mMovie;

    @Bindable
    public Actor getActor() {
        return mActor;
    }

    public void setActor(Actor actor) {
        mActor = actor;
        notifyPropertyChanged(BR.actor);
    }

    @Bindable
    public String getCharacter() {
        return "as " + mCharacter;
    }

    public void setCharacter(String character) {
        mCharacter = character;
        notifyPropertyChanged(BR.character);
    }

    @Bindable
    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String profilePath) {
        mProfilePath = profilePath;
        notifyPropertyChanged(BR.profilePath);
    }

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
    }
}
