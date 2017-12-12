package com.anh.movie.screen.search;

import android.app.Activity;
import android.content.Context;
import android.databinding.Bindable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.widget.Toast;
import com.anh.movie.BR;
import com.anh.movie.data.model.Movie;
import com.anh.movie.data.source.MovieServiceClient;
import com.anh.movie.data.source.RemoteDataSource;
import com.anh.movie.screen.BaseViewModel;
import com.anh.movie.screen.home.listmovie.MovieAdapter;
import com.anh.movie.screen.moviedetail.DetailActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anh on 12/1/2017.
 */

public class SearchViewModel extends BaseViewModel implements MovieAdapter.OnItemClickListener {
    private MovieAdapter mAdapter;
    private Context mContext;
    private boolean mIsLoadingMore;
    private RemoteDataSource mDataSource;
    private String mSearch;
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
    private int mPage;

    public void getListMovie() {
        getDisposable().add(mDataSource.getMoviesBySearch(mPage, mSearch)
                .subscribeOn(Schedulers.io())
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

    public void getListMovieSearch() {
        getDisposable().add(mDataSource.getMoviesBySearch(mPage, mSearch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Movie>>() {
                    @Override
                    public void onNext(List<Movie> value) {
                        setListMovie(value);
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

    public void setListMovie(List<Movie> movie) {
        setAdapter(new MovieAdapter(movie, this));
    }

    public SearchViewModel(Context context) {
        mContext = context;
        mAdapter = new MovieAdapter(new ArrayList<Movie>(), this);
        mDataSource = new RemoteDataSource(MovieServiceClient.getInstance());
        mIsLoadingMore = true;
        mPage = 1;
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

    @Bindable
    public RecyclerView.OnScrollListener getScrollListener() {
        return mScrollListener;
    }

    public void setScrollListener(RecyclerView.OnScrollListener scrollListener) {
        mScrollListener = scrollListener;
        notifyPropertyChanged(BR.scrollListener);
    }

    public void loadMore() {
        mPage++;
        getListMovie();
    }

    private SearchView.OnQueryTextListener mOnQueryTextListener =
            new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mSearch = newText;
                    getListMovieSearch();
                    return true;
                }
            };

    @Bindable
    public SearchView.OnQueryTextListener getOnQueryTextListener() {
        return mOnQueryTextListener;
    }

    public void setOnQueryTextListener(SearchView.OnQueryTextListener onQueryTextListener) {
        mOnQueryTextListener = onQueryTextListener;
        notifyPropertyChanged(BR.onQueryTextListener);
    }

    public void back() {
        ((Activity) mContext).finish();
    }
}
