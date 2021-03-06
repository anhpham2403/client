package com.anh.movie.screen.actordetail;

import android.app.Activity;
import android.content.Context;
import android.databinding.Bindable;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;
import com.anh.movie.BR;
import com.anh.movie.data.model.Actor;
import com.anh.movie.data.model.Movie;
import com.anh.movie.data.source.MovieServiceClient;
import com.anh.movie.data.source.RemoteDataSource;
import com.anh.movie.screen.BaseViewModel;
import com.anh.movie.screen.home.listmovie.MovieAdapter;
import com.anh.movie.screen.moviedetail.DetailActivity;
import com.anh.movie.screen.moviedetail.slidebanner.BannerViewPagerAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anh on 12/5/2017.
 */

public class ActorDetailViewModel extends BaseViewModel
        implements MovieAdapter.OnItemClickListener {

    private Context mContext;
    private Actor mActor;
    private BannerViewPagerAdapter mViewPager;
    private FragmentManager mFragmentManager;
    private RemoteDataSource mDataSource;
    private boolean mIsLoading;
    private MovieAdapter mAdapter;

    public ActorDetailViewModel(Context context, Actor actor, FragmentManager fragmentManager) {
        mContext = context;
        mActor = actor;
        mFragmentManager = fragmentManager;
        mIsLoading = true;
        mDataSource = new RemoteDataSource(MovieServiceClient.getInstance());
        mAdapter = new MovieAdapter(new ArrayList<Movie>(), this);
        getData();
    }

    public void getData() {
        getDisposable().add(mDataSource.getActorDetail(mActor.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<Actor>() {
                    @Override
                    public void onNext(Actor value) {
                        setActor(value);
                        setup(value.getMovies());
                        setLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        setLoading(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    @Bindable
    public Actor getActor() {
        return mActor;
    }

    public void setActor(Actor actor) {
        mActor = actor;
        notifyPropertyChanged(BR.actor);
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
    public FragmentManager getFragmentManager() {
        return mFragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
        notifyPropertyChanged(BR.fragmentManager);
    }

    @Bindable
    public boolean isLoading() {
        return mIsLoading;
    }

    public void setLoading(boolean loading) {
        mIsLoading = loading;
        notifyPropertyChanged(BR.loading);
    }

    public void back() {
        ((Activity) mContext).finish();
    }

    @Override
    public void onItemClick(Movie movie) {
        mContext.startActivity(DetailActivity.getIntent(movie, mContext));
    }

    @Bindable
    public MovieAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(MovieAdapter adapter) {
        mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    public void setup(List<Movie> movies) {
        setAdapter(new MovieAdapter(movies, this));
    }
}
