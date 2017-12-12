package com.anh.movie.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.databinding.library.baseAdapters.BR;
import com.anh.movie.utils.Constant;
import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by anh on 12/7/2017.
 */

public class Crew extends BaseObservable implements Parcelable {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("department")
    private String mDepartment;
    @SerializedName("job")
    private String mJob;
    @SerializedName("gender")
    private int mGender;
    @SerializedName("also_known_as")
    private String mAlsoKnowAs;
    @SerializedName("birthday")
    private Date mBirthday;
    @SerializedName("deathday")
    private Date mDeathday;
    @SerializedName("place_of_birth")
    private String mPlaceOfBirth;
    @SerializedName("biography")
    private String mBiography;
    @SerializedName("profile_path")
    private String mProfilePath;
    @SerializedName("movies")
    private List<Movie> mMovies;

    protected Crew(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mDepartment = in.readString();
        mJob = in.readString();
        mGender = in.readInt();
        mAlsoKnowAs = in.readString();
        mPlaceOfBirth = in.readString();
        mBiography = in.readString();
        mProfilePath = in.readString();
    }

    public static final Creator<Crew> CREATOR = new Creator<Crew>() {
        @Override
        public Crew createFromParcel(Parcel in) {
            return new Crew(in);
        }

        @Override
        public Crew[] newArray(int size) {
            return new Crew[size];
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
    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String department) {
        mDepartment = department;
        notifyPropertyChanged(BR.department);
    }

    @Bindable
    public String getJob() {
        return mJob;
    }

    public void setJob(String job) {
        mJob = job;
        notifyPropertyChanged(BR.job);
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
    public String getAlsoKnowAs() {
        return mAlsoKnowAs;
    }

    public void setAlsoKnowAs(String alsoKnowAs) {
        mAlsoKnowAs = alsoKnowAs;
        notifyPropertyChanged(BR.alsoKnowAs);
    }

    @Bindable
    public Date getBirthday() {
        return mBirthday;
    }

    public void setBirthday(Date birthday) {
        mBirthday = birthday;
        notifyPropertyChanged(BR.birthday);
    }

    @Bindable
    public Date getDeathday() {
        return mDeathday;
    }

    public void setDeathday(Date deathday) {
        mDeathday = deathday;
        notifyPropertyChanged(BR.deathday);
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
    public String getBiography() {
        return mBiography;
    }

    public void setBiography(String biography) {
        mBiography = biography;
        notifyPropertyChanged(BR.biography);
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
        parcel.writeString(mDepartment);
        parcel.writeString(mJob);
        parcel.writeInt(mGender);
        parcel.writeString(mAlsoKnowAs);
        parcel.writeString(mPlaceOfBirth);
        parcel.writeString(mBiography);
        parcel.writeString(mProfilePath);
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    @Bindable
    public String getNameCrew() {
        if (mAlsoKnowAs == null) {
            return null;
        }
        return " also as " + mAlsoKnowAs;
    }

    @Bindable
    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT_YYYYMMDD);
        return mBirthday != null ? dateFormat.format(mBirthday) : "undefined";
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
    }
}
