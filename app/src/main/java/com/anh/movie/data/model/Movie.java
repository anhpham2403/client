package com.anh.movie.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import com.anh.movie.BR;
import com.anh.movie.utils.Constant;
import com.cunoraz.tagview.Tag;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    @SerializedName("genres")
    private List<Genre> mGenres;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("cast")
    private List<Character> mCharacters;
    @SerializedName("imdbRating")
    private float mImdbRating;
    private float mTmdbRating;
    @SerializedName("imdb_id")
    private String mIdImdb;

    protected Movie(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mVoteAverage = in.readFloat();
        mPosterUrl = in.readString();
        mOverview = in.readString();
        mTrailerUrl = in.readString();
        mReleaseDate = new Date(in.readLong());
        mIdImdb = in.readString();
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
        parcel.writeString(mIdImdb);
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public List<Genre> getGenres() {
        return mGenres;
    }

    public void setGenres(List<Genre> genres) {
        mGenres = genres;
    }

    @Bindable
    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT_YYYYMMDD);
        return mReleaseDate != null ? dateFormat.format(mReleaseDate) : "undefined";
    }

    @Bindable
    public String getYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mReleaseDate);
        return mReleaseDate != null ? String.valueOf(calendar.get(Calendar.YEAR)) : "undefined";
    }

    @Bindable
    public String getListGenre() {
        String s = "";
        if (mGenres == null) {
            return s;
        }
        for (int i = 0; i < mGenres.size(); i++) {
            s += mGenres.get(i).getName();
            if (i + 1 < mGenres.size()) {
                s += ", ";
            }
        }
        return s;
    }

    @Bindable
    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
        notifyPropertyChanged(BR.status);
    }

    @Bindable
    public ArrayList<Tag> getTagName() {
        ArrayList<Tag> s = new ArrayList<>();
        if (mGenres == null) {
            return s;
        }
        for (Genre genre : mGenres) {
            s.add(new Tag(genre.getName()));
        }
        return s;
    }

    public List<Character> getCharacters() {
        return mCharacters;
    }

    public void setCharacters(List<Character> characters) {
        mCharacters = characters;
    }

    @Bindable
    public float getImdbRating() {
        return mImdbRating;
    }

    public void setImdbRating(float imdbRating) {
        mImdbRating = imdbRating;
        notifyPropertyChanged(BR.imdbRating);
    }

    @Bindable
    public float getTmdbRating() {
        return mTmdbRating;
    }

    public void setTmdbRating(float tmdbRating) {
        mTmdbRating = tmdbRating;
        notifyPropertyChanged(BR.tmdbRating);
    }

    @Bindable
    public String getIdImdb() {
        return mIdImdb;
    }

    public void setIdImdb(String idImdb) {
        mIdImdb = idImdb;
        notifyPropertyChanged(BR.idImdb);
    }

    @Bindable
    public String getFloatNumber() {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(mVoteAverage);
    }
}
