package com.anh.movie.screen.moviedetail.crew;

import android.content.Context;
import android.databinding.Bindable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;
import com.anh.movie.BR;
import com.anh.movie.data.model.Crew;
import com.anh.movie.data.model.Movie;
import com.anh.movie.data.source.MovieServiceClient;
import com.anh.movie.data.source.RemoteDataSource;
import com.anh.movie.screen.BaseViewModel;
import com.anh.movie.screen.crewdetail.CrewDetailActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anh on 12/5/2017.
 */

public class CrewViewModel extends BaseViewModel implements CrewAdapter.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener {
    private Movie mMovie;
    private Context mContext;
    private CrewAdapter mAdapter;
    private RemoteDataSource mDataSource;
    private boolean mIsLoading;

    public CrewViewModel(Context context, Movie movie) {
        mMovie = movie;
        mContext = context;
        mAdapter = new CrewAdapter(new ArrayList<Crew>(), this);
        mDataSource = new RemoteDataSource(MovieServiceClient.getInstance());
        getData();
    }

    public void getData() {
        getDisposable().add(mDataSource.getCrewByMovieId(mMovie.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<List<Crew>>() {
                    @Override
                    public void onNext(List<Crew> value) {
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

    @Bindable
    public CrewAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(CrewAdapter adapter) {
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
    public void onItemClick(Crew crew) {
        mContext.startActivity(CrewDetailActivity.getIntent(mContext, crew));
    }

    @Override
    public void onRefresh() {
        mAdapter = new CrewAdapter(new ArrayList<Crew>(), this);
        getData();
    }
}
