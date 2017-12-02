package com.anh.movie.data.source;

import com.anh.movie.data.model.Actor;
import com.anh.movie.data.model.Crew;
import com.anh.movie.data.model.Genre;
import com.anh.movie.data.model.GenreResponse;
import com.anh.movie.data.model.Movie;
import com.anh.movie.data.model.MovieResponse;
import com.anh.movie.data.model.Review;
import com.anh.movie.data.model.ReviewResponse;
import com.anh.movie.data.model.User;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import java.util.Date;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.json.JSONObject;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    public Observable<List<Movie>> getMoviesBySearch(int page, String search) {
        return mMovieApi.getMoviesBySearch(page, search)
                .map(new Function<MovieResponse, List<Movie>>() {

                    @Override
                    public List<Movie> apply(MovieResponse movieResponse) throws Exception {
                        return movieResponse.getMovies();
                    }
                });
    }

    public Observable<User> login(String username, String password) {
        return mMovieApi.login(username, password);
    }

    public Observable<User> register(String username, String name, String email, String password,
            MultipartBody.Part image) {
        RequestBody usernameBody = RequestBody.create(okhttp3.MultipartBody.FORM, username);
        RequestBody nameBody = RequestBody.create(okhttp3.MultipartBody.FORM, name);
        RequestBody emailBody = RequestBody.create(okhttp3.MultipartBody.FORM, email);
        RequestBody passwordBody = RequestBody.create(okhttp3.MultipartBody.FORM, password);
        return mMovieApi.register(usernameBody, nameBody, emailBody, passwordBody, image);
    }

    public Observable<List<Genre>> getGenres() {
        return mMovieApi.getGenres().map(new Function<GenreResponse, List<Genre>>() {
            @Override
            public List<Genre> apply(GenreResponse genreResponse) throws Exception {
                return genreResponse.getGenre();
            }
        });
    }

    public Observable<List<String>> getImagesOfMovie(int id) {
        return mMovieApi.getImageOfMovie(id);
    }

    public Observable<Movie> getMovieDetail(int id) {
        return mMovieApi.getMovieDetail(id);
    }

    public Observable<Actor> getActorDetail(int id) {
        return mMovieApi.getActorDetail(id);
    }

    public Observable<List<Crew>> getCrewByMovieId(int id) {
        return mMovieApi.getCrewByMovieId(id);
    }

    public Observable<Crew> getDetailCrew(int id) {
        return mMovieApi.getDetailCrew(id);
    }

    public Observable<Movie> getMovieOfCrew(int page, int id) {
        return mMovieApi.getMovieOfCrew(page, id);
    }

    public Observable<User> update(String username, String name, String email, String oldPawword,
            String password, MultipartBody.Part image) {
        RequestBody usernameBody = RequestBody.create(okhttp3.MultipartBody.FORM, username);
        RequestBody nameBody = RequestBody.create(okhttp3.MultipartBody.FORM, name);
        RequestBody emailBody = RequestBody.create(okhttp3.MultipartBody.FORM, email);
        RequestBody passwordBody = RequestBody.create(okhttp3.MultipartBody.FORM, password);
        RequestBody oldPawwordBody = RequestBody.create(okhttp3.MultipartBody.FORM, oldPawword);
        return mMovieApi.update(usernameBody, nameBody, emailBody, oldPawwordBody, passwordBody,
                image);
    }

    public Observable<String> rateMovie(int id, int score) {
        return mMovieApi.rateMovie(id, score).map(new Function<ResponseBody, String>() {
            @Override
            public String apply(ResponseBody responseBody) throws Exception {
                JSONObject jsonObject = new JSONObject(responseBody.string());
                return jsonObject.getString("message");
            }
        });
    }

    public Observable<ResponseBody> getRateMovie(int id) {
        return mMovieApi.getRateMovie(id);
    }

    public Observable<List<Review>> getReviews(int id, int page) {
        return mMovieApi.getReviews(id, page).map(new Function<ReviewResponse, List<Review>>() {
            @Override
            public List<Review> apply(ReviewResponse reviewResponse) throws Exception {
                return reviewResponse.getReviews();
            }
        });
    }

    public Observable<String> reviewMovie(int id, String content, Date date) {
        return mMovieApi.reviewMovie(id, content, date.getTime())
                .map(new Function<ResponseBody, String>() {
                    @Override
                    public String apply(ResponseBody responseBody) throws Exception {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        return jsonObject.getString("message");
                    }
                });
    }

    public Observable<Movie> movieTmdb(int id, String apiKey) {
        return mMovieApi.movieTmdb(id, apiKey);
    }

    public Observable<Movie> movieImdb(String id, String apiKey) {
        return mMovieApi.movieImdb(id,apiKey);
    }
}
