package com.anh.movie.data.source;

import com.anh.movie.utils.Constant;

import static com.anh.movie.utils.Constant.BASE_URL;

/**
 * Created by anh on 11/26/2017.
 */

public class MovieServiceClient extends ServiceClient {
    private static MovieApi mMovieApi;
    private static MovieApi movieApi1;
    private static MovieApi movieApi2;

    public static MovieApi getInstance() {
        if (mMovieApi == null) {
            mMovieApi = createService(BASE_URL, MovieApi.class);
        }
        return mMovieApi;
    }

    public static MovieApi getInstance1() {
        if (movieApi1 == null) {
            movieApi1 = createService(Constant.BASE_URL1, MovieApi.class);
        }
        return movieApi1;
    }

    public static MovieApi getInstance2() {
        if (movieApi2 == null) {
            movieApi2 = createService(Constant.BASE_URL2, MovieApi.class);
        }
        return movieApi2;
    }
}
