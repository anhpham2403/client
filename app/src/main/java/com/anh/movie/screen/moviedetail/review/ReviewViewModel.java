package com.anh.movie.screen.moviedetail.review;

import android.content.Context;
import android.databinding.Bindable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;
import com.anh.movie.BR;
import com.anh.movie.data.model.Movie;
import com.anh.movie.data.model.Review;
import com.anh.movie.data.model.User;
import com.anh.movie.data.source.MovieServiceClient;
import com.anh.movie.data.source.RemoteDataSource;
import com.anh.movie.data.source.SharePreferenceApi;
import com.anh.movie.data.source.SharePreferenceImp;
import com.anh.movie.screen.BaseViewModel;
import com.google.gson.Gson;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.anh.movie.data.source.SharePreferenceKey.USER_PREFS;

/**
 * Created by anh on 12/5/2017.
 */

public class ReviewViewModel extends BaseViewModel
        implements ReviewAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private Movie mMovie;
    private Context mContext;
    private ReviewAdapter mAdapter;
    private RemoteDataSource mDataSource;
    private boolean mIsLoading;
    private String mContent;
    private int mPage;
    private Review mReview;

    public ReviewViewModel(Context context, Movie movie) {
        mMovie = movie;
        mContext = context;
        mAdapter = new ReviewAdapter(new ArrayList<Review>(), this);
        mDataSource = new RemoteDataSource(MovieServiceClient.getInstance());
        mIsLoading = true;
        mPage = 1;
        getData();
        mReview = new Review();
    }

    public void getData() {
        getDisposable().add(mDataSource.getReviews(mMovie.getId(), mPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<List<Review>>() {

                    @Override
                    public void onNext(List<Review> value) {
                        setLoading(false);
                        mAdapter.updateAdapter(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setLoading(false);
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    public void reviewMovie(String content, Date time) {
        SharePreferenceApi mPreference = new SharePreferenceImp(mContext);
        Gson gson = new Gson();
        String json = mPreference.get(USER_PREFS, String.class);
        if (json == null) {
            return;
        }
        User user = gson.fromJson(json, User.class);
        mReview.setUser(user);
        mReview.setMovie(mMovie);
        mReview.setContent(content);
        mReview.setTime(time);
        getDisposable().add(mDataSource.reviewMovie(mMovie.getId(), content, time)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String value) {
                        Toast.makeText(mContext, value, Toast.LENGTH_SHORT).show();
                        ArrayList<Review> reviews = new ArrayList<Review>();
                        reviews.add(mReview);
                        mAdapter.updateAdapter(reviews);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Bindable
    public ReviewAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(ReviewAdapter adapter) {
        mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Bindable
    public boolean isLoading() {
        return mIsLoading;
    }

    public void setLoading(boolean loading) {
        mIsLoading = loading;
        notifyPropertyChanged(BR.loading);
    }

    @Override
    public void onItemClick(Review review) {
        //TODO
    }

    @Bindable
    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
        notifyPropertyChanged(BR.content);
    }

    public void onSubmit(String content) {
        if (content == null) {
            return;
        }
        reviewMovie(content, Calendar.getInstance().getTime());
    }

    @Override
    public void onRefresh() {
        mAdapter = new ReviewAdapter(new ArrayList<Review>(), this);
        getData();
    }
}
