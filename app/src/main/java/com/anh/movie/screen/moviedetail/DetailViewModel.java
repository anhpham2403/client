package com.anh.movie.screen.moviedetail;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.Bindable;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.Toast;
import com.anh.movie.BR;
import com.anh.movie.data.model.Movie;
import com.anh.movie.data.model.User;
import com.anh.movie.data.source.MovieServiceClient;
import com.anh.movie.data.source.RemoteDataSource;
import com.anh.movie.data.source.SharePreferenceApi;
import com.anh.movie.data.source.SharePreferenceImp;
import com.anh.movie.screen.BaseViewModel;
import com.anh.movie.screen.login.LoginActivity;
import com.anh.movie.screen.moviedetail.slidebanner.BannerViewPagerAdapter;
import com.google.gson.Gson;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

import static com.anh.movie.data.source.SharePreferenceKey.USER_PREFS;

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
    private boolean mIsHideButton;
    private ViewPager.OnPageChangeListener mChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position != 0) {
                setHideButton(true);
            } else {
                setHideButton(false);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public DetailViewModel(Context context, FragmentManager fragmentManager, Movie movie) {
        mContext = context;
        mMovie = movie;
        mFragmentManager = fragmentManager;
        mDataSource = new RemoteDataSource(MovieServiceClient.getInstance());
        getImage();
        getDetail();
        mIsLoading = true;
        mIsHideButton = false;
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

    public void addFavorite() {
        SharePreferenceApi mSharedPreferences = new SharePreferenceImp(mContext);
        Gson gson = new Gson();
        String json = mSharedPreferences.get(USER_PREFS, String.class);
        User mUser = gson.fromJson(json, User.class);
        if (mUser == null) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(mContext,
                        android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(mContext);
            }
            builder.setTitle("Login")
                    .setMessage("Login to add this movie to your favorite list")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .show();
        } else {
            getDisposable().add(mDataSource.addFavorite(mMovie.getId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableObserver<String>() {
                        @Override
                        public void onNext(String value) {
                            Toast.makeText(mContext, value, Toast.LENGTH_SHORT).show();
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
        ViewPager mViewPager;
        notifyPropertyChanged(BR.pager);
    }

    @Bindable
    public boolean isHideButton() {
        return mIsHideButton;
    }

    public void setHideButton(boolean hideButton) {
        mIsHideButton = hideButton;
        notifyPropertyChanged(BR.hideButton);
    }

    @Bindable
    public ViewPager.OnPageChangeListener getChangeListener() {
        return mChangeListener;
    }

    public void setChangeListener(ViewPager.OnPageChangeListener changeListener) {
        mChangeListener = changeListener;
        notifyPropertyChanged(BR.changeListener);
    }
}
