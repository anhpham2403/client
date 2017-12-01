package com.anh.movie.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import com.anh.movie.utils.Constant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by anh on 11/30/2017.
 */

public class Movie extends BaseObservable implements Parcelable {
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("original_title")
    @Expose
    private String mOriginalTitle;
    @SerializedName("title")
    @Expose
    private String mTitle;
    @SerializedName("vote_average")
    @Expose
    private float mVoteAverage;
    @SerializedName("poster_path")
    @Expose
    private String mPosterUrl;
    @SerializedName("overview")
    @Expose
    private String mOverview;
    @SerializedName("release_date")
    @Expose
    private Date mReleaseDate;
    private String mTrailerUrl;

    protected Movie(Parcel in) {
        mTitle = in.readString();
        mVoteAverage = in.readFloat();
        mPosterUrl = in.readString();
        mOverview = in.readString();
        mTrailerUrl = in.readString();
        mReleaseDate = new Date(in.readLong());
    }

    @Bindable
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public float getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public Date getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getTrailerUrl() {
        return mTrailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.mTrailerUrl = trailerUrl;
    }

    @Bindable
    public String getPosterUrl() {
        return mPosterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        mPosterUrl = posterUrl;
    }

    @Bindable
    public String getDate() {
        SimpleDateFormat dt = new SimpleDateFormat(Constant.DATE_FORMAT_YYYYMMDD, Locale.US);
        return dt.format(mReleaseDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mTitle);
        parcel.writeFloat(mVoteAverage);
        parcel.writeString(mPosterUrl);
        parcel.writeString(mOverview);
        parcel.writeString(mTrailerUrl);
        parcel.writeLong(mReleaseDate.getTime());
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }
}
