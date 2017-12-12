package com.anh.movie.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by anh on 12/10/2017.
 */

public class ReviewResponse {
    @SerializedName("page")
    @Expose
    private int mPage;
    @SerializedName("result")
    @Expose
    private List<Review> mReviews;

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    public List<Review> getReviews() {
        return mReviews;
    }

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
    }
}

