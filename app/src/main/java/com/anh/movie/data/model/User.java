package com.anh.movie.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anh on 12/2/2017.
 */

public class User implements Parcelable {
    @SerializedName("username")
    @Expose
    private String mUsername;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String mEmail;
    @SerializedName("password")
    @Expose
    private String mPassword;
    @SerializedName("role")
    @Expose
    private String mRole;
    @SerializedName("file_path")
    @Expose
    private String mAvatarUrl;
    @SerializedName("token")
    @Expose
    private String mToken;

    protected User(Parcel in) {
        mUsername = in.readString();
        name = in.readString();
        mEmail = in.readString();
        mPassword = in.readString();
        mRole = in.readString();
        mAvatarUrl = in.readString();
        mToken = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getRole() {
        return mRole;
    }

    public void setRole(String role) {
        mRole = role;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUsername);
        parcel.writeString(name);
        parcel.writeString(mEmail);
        parcel.writeString(mPassword);
        parcel.writeString(mRole);
        parcel.writeString(mAvatarUrl);
        parcel.writeString(mToken);
    }
}
