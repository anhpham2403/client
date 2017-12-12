package com.anh.movie.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.anh.movie.BR;
import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anh on 12/10/2017.
 */

public class Review extends BaseObservable {
    @SerializedName("user")
    private User mUser;
    @SerializedName("movie")
    private Movie mMovie;
    @SerializedName("content")
    private String mContent;
    @SerializedName("time")
    private Date mTime;

    @Bindable
    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
        notifyPropertyChanged(BR.user);
    }

    @Bindable
    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
        notifyPropertyChanged(BR.movie);
    }

    @Bindable
    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
        notifyPropertyChanged(BR.content);
    }

    @Bindable
    public Date getTime() {
        return mTime;
    }

    public void setTime(Date time) {
        mTime = time;
        notifyPropertyChanged(BR.time);
    }

    @Bindable
    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        return mTime != null ? dateFormat.format(mTime) : null;
    }
}
