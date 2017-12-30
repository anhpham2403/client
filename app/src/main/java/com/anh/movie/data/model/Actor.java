package com.anh.movie.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.databinding.library.baseAdapters.BR;
import com.anh.movie.utils.Constant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by anh on 12/5/2017.
 */

public class Actor extends BaseObservable implements Parcelable {
    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("aslo_know_as")
    @Expose
    private String mAlsoKnowAs;
    @SerializedName("gender")
    @Expose
    private int mGender;
    @SerializedName("birthday")
    @Expose
    private Date mBirhDday;
    @SerializedName("deathday")
    @Expose
    private Date mDeathDay;
    @SerializedName("biography")
    @Expose
    private String mBiography;
    @SerializedName("place_of_birth")
    @Expose
    private String mPlaceOfBirth;
    @SerializedName("profile_path")
    @Expose
    private String mProfilePath;
    @SerializedName("movies")
    @Expose
    private List<Movie> mMovies;

    protected Actor(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mAlsoKnowAs = in.readString();
        mGender = in.readInt();
        mBiography = in.readString();
        mPlaceOfBirth = in.readString();
        mProfilePath = in.readString();
    }

    public static final Creator<Actor> CREATOR = new Creator<Actor>() {
        @Override
        public Actor createFromParcel(Parcel in) {
            return new Actor(in);
        }

        @Override
        public Actor[] newArray(int size) {
            return new Actor[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    @Bindable
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getAlsoKnowAs() {
        return mAlsoKnowAs;
    }

    public void setAlsoKnowAs(String alsoKnowAs) {
        mAlsoKnowAs = alsoKnowAs;
        notifyPropertyChanged(BR.alsoKnowAs);
    }

    @Bindable
    public int getGender() {
        return mGender;
    }

    public void setGender(int gender) {
        mGender = gender;
        notifyPropertyChanged(BR.gender);
    }

    @Bindable
    public Date getBirhDday() {
        return mBirhDday;
    }

    public void setBirhDday(Date birhDday) {
        mBirhDday = birhDday;
        notifyPropertyChanged(BR.birhDday);
    }

    @Bindable
    public Date getDeathDay() {
        return mDeathDay;
    }

    public void setDeathDay(Date deathDay) {
        mDeathDay = deathDay;
        notifyPropertyChanged(BR.deathDay);
    }

    @Bindable
    public String getBiography() {
        return mBiography;
    }

    public void setBiography(String biography) {
        mBiography = biography;
        notifyPropertyChanged(BR.biography);
    }

    @Bindable
    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
        notifyPropertyChanged(BR.placeOfBirth);
    }

    @Bindable
    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String profilePath) {
        mProfilePath = profilePath;
        notifyPropertyChanged(BR.profilePath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeString(mAlsoKnowAs);
        parcel.writeInt(mGender);
        parcel.writeString(mBiography);
        parcel.writeString(mPlaceOfBirth);
        parcel.writeString(mProfilePath);
    }

    @Bindable
    public String getNameActor() {
        if (mAlsoKnowAs == null) {
            return null;
        }
        return " also as " + mAlsoKnowAs;
    }

    @Bindable
    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT_YYYYMMDD);
        return mBirhDday != null ? dateFormat.format(mBirhDday) : "undefined";
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
    }
}
