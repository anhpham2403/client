package com.anh.movie.data.source;

import static com.anh.movie.utils.Constant.BASE_URL;

/**
 * Created by anh on 11/26/2017.
 */

public class MovieServiceClient extends ServiceClient {
    private static MovieApi mMovieApi;

    public static MovieApi getInstance() {
        if (mMovieApi == null) {
            mMovieApi = createService(BASE_URL, MovieApi.class);
        }
        return mMovieApi;
    }
}
