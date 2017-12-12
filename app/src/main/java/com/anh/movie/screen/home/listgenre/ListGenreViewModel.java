package com.anh.movie.screen.home.listgenre;

import android.content.Context;
import android.databinding.Bindable;
import android.widget.Toast;
import com.anh.movie.BR;
import com.anh.movie.data.model.Genre;
import com.anh.movie.data.source.MovieServiceClient;
import com.anh.movie.data.source.RemoteDataSource;
import com.anh.movie.screen.BaseViewModel;
import com.anh.movie.screen.moviesofgenre.MoviesActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anh on 12/3/2017.
 */

public class ListGenreViewModel extends BaseViewModel implements GenreAdapter.OnItemClickListener {
    private Context mContext;
    private GenreAdapter mAdapter;
    private boolean mIsLoading;
    private RemoteDataSource mDataSource;

    public ListGenreViewModel(Context context) {
        mContext = context;
        mDataSource = new RemoteDataSource(MovieServiceClient.getInstance());
        mAdapter = new GenreAdapter(new ArrayList<Genre>(), this);
        getData();
        mIsLoading = true;
    }

    @Override
    public void onItemClick(Genre genre) {
        mContext.startActivity(MoviesActivity.getIntentMoviesOfGenre(mContext, genre));
    }

    @Bindable
    public GenreAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(GenreAdapter adapter) {
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

    public void getData() {
        getDisposable().add(mDataSource.getGenres()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<List<Genre>>() {
                    @Override
                    public void onNext(List<Genre> value) {
                        mAdapter.updateAdapter(value);
                        setLoading(false);
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
