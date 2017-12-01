package com.anh.movie.data.source;

import android.util.Log;
import com.anh.movie.data.model.Movie;
import com.anh.movie.data.model.MovieResponse;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import java.util.List;

/**
 * Created by anh on 11/30/2017.
 */

public class RemoteDataSource {
    private MovieApi mMovieApi;

    public RemoteDataSource(MovieApi movieApi) {
        mMovieApi = movieApi;
    }

    public Observable<List<Movie>> getMoviesTopRate(int page) {
        return mMovieApi.getMoviesTopRate(page).map(new Function<MovieResponse, List<Movie>>() {
            @Override
            public List<Movie> apply(MovieResponse movieResponse) throws Exception {
                return movieResponse.getMovies();
            }
        });
    }

    public Observable<List<Movie>> getMoviesUpcoming(int page) {
        return mMovieApi.getMoviesUpcoming(page).map(new Function<MovieResponse, List<Movie>>() {

            @Override
            public List<Movie> apply(MovieResponse movieResponse) throws Exception {
                return movieResponse.getMovies();
            }
        });
    }

    public Observable<List<Movie>> getMoviesPopular(int page) {
        return mMovieApi.getMoviesPopular(page).map(new Function<MovieResponse, List<Movie>>() {

            @Override
            public List<Movie> apply(MovieResponse movieResponse) throws Exception {
                return movieResponse.getMovies();
            }
        });
    }

    public Observable<List<Movie>> getMoviesByIdGenre(int page, int id) {
        return mMovieApi.getMoviesByIdGenre(page, id)
                .map(new Function<MovieResponse, List<Movie>>() {

                    @Override
                    public List<Movie> apply(MovieResponse movieResponse) throws Exception {
                        return movieResponse.getMovies();
                    }
                });
    }

    public Observable<List<Movie>> getMoviesNowPlaying(int page) {
        return mMovieApi.getMoviesNowPlaying(page).map(new Function<MovieResponse, List<Movie>>() {

            @Override
            public List<Movie> apply(MovieResponse movieResponse) throws Exception {
                return movieResponse.getMovies();
            }
        });
    }
}
