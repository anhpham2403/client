package com.anh.movie.data.source;

import com.anh.movie.data.model.Actor;
import com.anh.movie.data.model.Crew;
import com.anh.movie.data.model.GenreResponse;
import com.anh.movie.data.model.Movie;
import com.anh.movie.data.model.MovieResponse;
import com.anh.movie.data.model.ReviewResponse;
import com.anh.movie.data.model.User;
import io.reactivex.Observable;
import java.util.Date;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.json.JSONObject;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by anh on 11/26/2017.
 */

public interface MovieApi {
    @GET("movie/top_rate")
    Observable<MovieResponse> getMoviesTopRate(@Query("page") int page);

    @GET("movie/upcoming")
    Observable<MovieResponse> getMoviesUpcoming(@Query("page") int page);

    @GET("movie/now_playing")
    Observable<MovieResponse> getMoviesNowPlaying(@Query("page") int page);

    @GET("movie/popular")
    Observable<MovieResponse> getMoviesPopular(@Query("page") int page);

    @GET("movie/by_genre_id")
    Observable<MovieResponse> getMoviesByIdGenre(@Query("page") int page, @Query("id") int id);

    @GET("movie/search")
    Observable<MovieResponse> getMoviesBySearch(@Query("page") int page, @Query("name") String id);

    @GET("movie/{id}")
    Observable<MovieResponse> getMoviesBySearch(@Path("id") int id);

    @POST("user/authentication")
    @FormUrlEncoded
    Observable<User> login(@Field("username") String username, @Field("password") String password);

    @Multipart
    @POST("user/update")
    Observable<User> update(@Part("username") RequestBody username, @Part("name") RequestBody name,
            @Part("email") RequestBody email, @Part("password_old") RequestBody passwordOld,
            @Part("password") RequestBody password, @Part MultipartBody.Part image);

    @Multipart
    @POST("user/register")
    Observable<User> register(@Part("username") RequestBody username,
            @Part("name") RequestBody name, @Part("email") RequestBody email,
            @Part("password") RequestBody password, @Part MultipartBody.Part image);

    @GET("genre/genres")
    Observable<GenreResponse> getGenres();

    @GET("movie/image/{id}")
    Observable<List<String>> getImageOfMovie(@Path("id") int id);

    @GET("movie/{id}")
    Observable<Movie> getMovieDetail(@Path("id") int id);

    @GET("actor/{id}")
    Observable<Actor> getActorDetail(@Path("id") int id);

    @GET("crew/by_movie_id")
    Observable<List<Crew>> getCrewByMovieId(@Query("id") int id);

    @GET("crew/{id}")
    Observable<Crew> getDetailCrew(@Path("id") int id);

    @GET("movie/movie_of_crew")
    Observable<Movie> getMovieOfCrew(@Query("page") int page, @Query("id") int id);

    @POST("rate")
    Observable<ResponseBody> rateMovie(@Query("id") int id, @Query("score") int score);

    @GET("rate/{id}")
    Observable<ResponseBody> getRateMovie(@Path("id") int id);

    @GET("review/movie/{id}")
    Observable<ReviewResponse> getReviews(@Path("id") int id, @Query("page") int page);

    @POST("review")
    Observable<ResponseBody> reviewMovie(@Query("id") int id, @Query("content") String content,
            @Query("time") long date);

    @GET("movie/{id}")
    Observable<Movie> movieTmdb(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("?")
    Observable<Movie> movieImdb(@Query("i") String id, @Query("apiKey") String apiKey);

    @GET("favorite")
    Observable<MovieResponse> getFavorites();

    @POST("favorite/{id}")
    Observable<ResponseBody> addFavorite(@Path("id") int id);

    @DELETE("favorite/{id}")
    Observable<ResponseBody> delFavorite(@Path("id") int id);
}
