package com.anh.movie.screen.favorite;

import android.content.Context;
import android.databinding.Bindable;
import android.widget.Toast;
import com.anh.movie.BR;
import com.anh.movie.data.model.Movie;
import com.anh.movie.data.source.MovieServiceClient;
import com.anh.movie.data.source.RemoteDataSource;
import com.anh.movie.screen.BaseViewModel;
import com.anh.movie.screen.home.listmovie.MovieAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anh on 12/13/2017.
 */

public class FavoriteViewModel extends BaseViewModel implements MovieAdapter.OnItemClickListener {
    private Context mContext;
    private MovieAdapter mAdapter;
    private boolean mIsLoading;
    private RemoteDataSource mDataSource;

    public FavoriteViewModel(Context context) {
        mContext = context;
        mIsLoading = true;
        mAdapter = new MovieAdapter(new ArrayList<Movie>(), this);
        mDataSource = new RemoteDataSource(MovieServiceClient.getInstance());
        getData();
    }

    public void getData() {
        getDisposable().add(mDataSource.getFavorites()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<List<Movie>>() {
                    @Override
                    public void onNext(List<Movie> value) {
                        mAdapter.updateAdapter(value);
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

    @Override
    public void onItemClick(Movie movie) {

    }

    @Bindable
    public MovieAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(MovieAdapter adapter) {
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
}
