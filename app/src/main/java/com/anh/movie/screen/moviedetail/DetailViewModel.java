package com.anh.movie.screen.moviedetail;

import android.app.Activity;
import android.content.Context;
import android.databinding.Bindable;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;
import com.anh.movie.BR;
import com.anh.movie.data.model.Movie;
import com.anh.movie.data.source.MovieServiceClient;
import com.anh.movie.data.source.RemoteDataSource;
import com.anh.movie.screen.BaseViewModel;
import com.anh.movie.screen.moviedetail.slidebanner.BannerViewPagerAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * Created by anh on 12/3/2017.
 */

public class DetailViewModel extends BaseViewModel {
    private Context mContext;
    private Movie mMovie;
    private BannerViewPagerAdapter mViewPager;
    private FragmentManager mFragmentManager;
    private RemoteDataSource mDataSource;
    private boolean mIsLoading;
    private DetailViewPager mPager;

    public DetailViewModel(Context context, FragmentManager fragmentManager, Movie movie) {
        mContext = context;
        mMovie = movie;
        mFragmentManager = fragmentManager;
        mDataSource = new RemoteDataSource(MovieServiceClient.getInstance());
        getImage();
        getDetail();
        mIsLoading = true;
        mPager = new DetailViewPager(fragmentManager, movie);
    }

    public void getImage() {
        getDisposable().add(mDataSource.getImagesOfMovie(mMovie.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<List<String>>() {

                    @Override
                    public void onNext(List<String> value) {
                        setViewPager(new BannerViewPagerAdapter(mFragmentManager, value));
                        setIsLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        setIsLoading(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    public void getDetail() {
        getDisposable().add(mDataSource.getMovieDetail(mMovie.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<Movie>() {
                    @Override
                    public void onNext(Movie value) {
                        setMovie(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        setIsLoading(false);
                    }

                    @Override
                    public void onComplete() {
                        setIsLoading(false);
                    }
                }));
    }

    @Bindable
    public BannerViewPagerAdapter getViewPager() {
        return mViewPager;
    }

    public void setViewPager(BannerViewPagerAdapter viewPager) {
        mViewPager = viewPager;
        notifyPropertyChanged(BR.viewPager);
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
    public boolean isLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isloading) {
        mIsLoading = isloading;
        notifyPropertyChanged(BR.loading);
    }

    public void back() {
        ((Activity) mContext).finish();
    }

    @Bindable
    public DetailViewPager getPager() {
        return mPager;
    }

    public void setPager(DetailViewPager pager) {
        mPager = pager;
        notifyPropertyChanged(BR.pager);
    }
}
