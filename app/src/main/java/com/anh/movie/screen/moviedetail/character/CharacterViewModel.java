package com.anh.movie.screen.moviedetail.character;

import android.content.Context;
import android.databinding.Bindable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;
import com.anh.movie.BR;
import com.anh.movie.data.model.Actor;
import com.anh.movie.data.model.Character;
import com.anh.movie.data.model.Movie;
import com.anh.movie.data.source.MovieServiceClient;
import com.anh.movie.data.source.RemoteDataSource;
import com.anh.movie.screen.BaseViewModel;
import com.anh.movie.screen.actordetail.ActorDetailActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;

/**
 * Created by anh on 12/5/2017.
 */

public class CharacterViewModel extends BaseViewModel
        implements CharacterAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private Movie mMovie;
    private Context mContext;
    private CharacterAdapter mAdapter;
    private Character mCharacter;
    private RemoteDataSource mDataSource;
    private boolean mIsLoading;

    public CharacterViewModel(Context context, Movie movie) {
        mMovie = movie;
        mContext = context;
        mAdapter = new CharacterAdapter(new ArrayList<Character>(), this);
        mDataSource = new RemoteDataSource(MovieServiceClient.getInstance());
        getData();
    }

    public void getData() {
        getDisposable().add(mDataSource.getMovieDetail(mMovie.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<Movie>() {
                    @Override
                    public void onNext(Movie value) {
                        mAdapter.updateAdapter(value.getCharacters());
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
    public CharacterAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(CharacterAdapter adapter) {
        mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Bindable
    public Character getCharacter() {
        return mCharacter;
    }

    public void setCharacter(Character character) {
        mCharacter = character;
        notifyPropertyChanged(BR.character);
    }

    @Override
    public void onItemClick(Actor actor) {
        mContext.startActivity(ActorDetailActivity.getIntent(mContext, actor));
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
    public void onRefresh() {
        mAdapter = new CharacterAdapter(new ArrayList<Character>(), this);
        getData();
    }
}
