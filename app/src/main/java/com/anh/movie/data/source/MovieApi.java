package com.anh.movie.data.source;

import com.anh.movie.data.model.MovieResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by anh on 11/26/2017.
 */

public interface MovieApi {
    @GET("movie/top_rate")
    Observable<MovieResponse> getMoviesTopRate(@Query("page") int page);

    @GET("movie/upcoming")
    Observable<MovieResponse> getMoviesUpcoming(@Query("page") int page);

    @GET("movie/nowplaying")
    Observable<MovieResponse> getMoviesNowPlaying(@Query("page") int page);

    @GET("movie/popular")
    Observable<MovieResponse> getMoviesPopular(@Query("page") int page);

    @GET("movie/by_genre_id")
    Observable<MovieResponse> getMoviesByIdGenre(@Query("page") int page, @Query("id") int id);
}
