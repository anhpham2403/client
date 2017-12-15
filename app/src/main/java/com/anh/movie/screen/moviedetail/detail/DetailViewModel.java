package com.anh.movie.screen.moviedetail.detail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.Bindable;
import android.os.Build;
import android.widget.RatingBar;
import android.widget.Toast;
import com.anh.movie.BR;
import com.anh.movie.BuildConfig;
import com.anh.movie.data.model.Movie;
import com.anh.movie.data.model.User;
import com.anh.movie.data.source.MovieServiceClient;
import com.anh.movie.data.source.RemoteDataSource;
import com.anh.movie.data.source.SharePreferenceApi;
import com.anh.movie.data.source.SharePreferenceImp;
import com.anh.movie.screen.BaseViewModel;
import com.anh.movie.screen.login.LoginActivity;
import com.anh.movie.screen.moviesofgenre.MoviesActivity;
import com.anh.movie.utils.Constant;
import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;

import static com.anh.movie.data.source.SharePreferenceKey.USER_PREFS;

/**
 * Created by anh on 12/5/2017.
 */

public class DetailViewModel extends BaseViewModel implements RatingBar.OnRatingBarChangeListener {
    private Context mContext;
    private Movie mMovie;
    private RemoteDataSource mDataSource;
    private RemoteDataSource mDataSource1;
    private boolean mIsLoading;
    private int mScore;
    private TagView.OnTagClickListener mListener = new TagView.OnTagClickListener() {
        @Override
        public void onTagClick(Tag tag, int i) {
            mContext.startActivity(
                    MoviesActivity.getIntentMoviesOfGenre(mContext, mMovie.getGenres().get(i)));
        }
    };

    public DetailViewModel(Context context, Movie movie) {
        mContext = context;
        mMovie = movie;
        mDataSource = new RemoteDataSource(MovieServiceClient.getInstance());
        getDetail();
        mIsLoading = true;
        getScore(movie.getId());
        getMovieRateImdb(movie.getIdImdb(), BuildConfig.API_KEY1);
        getMovieRateTmdb(movie.getId(), BuildConfig.API_KEY);
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

    public void getScore(int movie) {
        getDisposable().add(mDataSource.getRateMovie(movie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<ResponseBody>() {

                    @Override
                    public void onNext(ResponseBody value) {
                        try {
                            JSONObject jsonObject = new JSONObject(value.string());
                            setScore(jsonObject.getInt("score"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    public void rateMovie(int movie, int score) {
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
                    .setMessage("Login to rate this movie")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            mMovie.setImdbRating(0);
                        }
                    })
                    .show();
        } else {
            getDisposable().add(mDataSource.rateMovie(movie, score)
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

    public void getMovieRateImdb(String id, String apiKey) {
        mDataSource1 = new RemoteDataSource(MovieServiceClient.getInstance2());
        getDisposable().add(mDataSource1.movieImdb(id, apiKey)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<Movie>() {
                    @Override
                    public void onNext(Movie value) {
                        mMovie.setImdbRating(value.getImdbRating());
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

    public void getMovieRateTmdb(int id, String apiKey) {
        mDataSource1 = new RemoteDataSource(MovieServiceClient.getInstance1());
        getDisposable().add(mDataSource1.movieTmdb(id, apiKey)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<Movie>() {
                    @Override
                    public void onNext(Movie value) {
                        mMovie.setTmdbRating(value.getVoteAverage());
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

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        int score = (int) (v * 2);
        if (mScore == score) {
            return;
        }
        rateMovie(mMovie.getId(), score);
    }

    @Bindable
    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
        notifyPropertyChanged(BR.score);
    }
    @Bindable
    public TagView.OnTagClickListener getListener() {
        return mListener;
    }

    public void setListener(TagView.OnTagClickListener listener) {
        mListener = listener;
        notifyPropertyChanged(BR.listener);
    }

}
