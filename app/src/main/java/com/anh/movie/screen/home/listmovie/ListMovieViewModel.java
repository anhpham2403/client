package com.anh.movie.screen.home.listmovie;

import android.content.Context;
import android.databinding.Bindable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.anh.movie.BR;
import com.anh.movie.data.model.Movie;
import com.anh.movie.data.source.MovieServiceClient;
import com.anh.movie.data.source.RemoteDataSource;
import com.anh.movie.screen.BaseViewModel;
import com.anh.movie.screen.home.ViewPagerAdapter;
import com.anh.movie.screen.moviedetail.DetailActivity;
import com.anh.movie.utils.Constant;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anh on 11/26/2017.
 */

public class ListMovieViewModel extends BaseViewModel implements MovieAdapter.OnItemClickListener {
    private MovieAdapter mAdapter;
    private Context mContext;
    private int mCategory;
    private boolean mIsLoadingMore;
    private int mPage;
    private List<Movie> mMovies;
    private int mId;
    private RemoteDataSource mDataSource;
    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy <= 0) {
                return;
            }
            LinearLayoutManager layoutManager =
                    (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
            if (!isLoadingMore() && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                setLoadingMore(true);
                loadMore();
            }
        }
    };

    public ListMovieViewModel(Context context, int category, int id) {
        super();
        mContext = context;
        mCategory = category;
        mIsLoadingMore = true;
        mPage = 1;
        mId = id;
        mMovies = new ArrayList<>();
        mAdapter = new MovieAdapter(mMovies, this);
        mDataSource = new RemoteDataSource(MovieServiceClient.getInstance());
        getListMovie();
    }

    public void getListMovie() {
        Observable<List<Movie>> observable = null;
        switch (mCategory) {
            case ViewPagerAdapter.StateMode.POPULAR:
                observable = mDataSource.getMoviesPopular(mPage);
                break;
            case ViewPagerAdapter.StateMode.NOW_PLAYING:
                observable = mDataSource.getMoviesNowPlaying(mPage);
                break;
            case ViewPagerAdapter.StateMode.UP_COMING:
                observable = mDataSource.getMoviesUpcoming(mPage);
                break;
            case ViewPagerAdapter.StateMode.TOP_RATE:
                observable = mDataSource.getMoviesTopRate(mPage);
                break;
            case Constant.MOVIES_OF_GENRES:
                observable = mDataSource.getMoviesByIdGenre(mPage, mId);
                break;
            default:
                break;
        }
        if (observable == null) {
            return;
        }
        getDisposable().add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Movie>>() {
                    @Override
                    public void onNext(List<Movie> value) {
                        mAdapter.updateAdapter(value);
                        setLoadingMore(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                        setLoadingMore(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
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

    @Bindable
    public boolean isLoadingMore() {
        return mIsLoadingMore;
    }

    public void setLoadingMore(boolean loadingMore) {
        mIsLoadingMore = loadingMore;
        notifyPropertyChanged(BR.loadingMore);
    }

    public void loadMore() {
        mPage++;
        getListMovie();
    }

    @Bindable
    public RecyclerView.OnScrollListener getScrollListener() {
        return mScrollListener;
    }
}
