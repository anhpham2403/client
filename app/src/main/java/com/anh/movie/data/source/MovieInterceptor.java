package com.anh.movie.data.source;


import com.anh.movie.App;
import com.anh.movie.data.model.User;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.anh.movie.data.source.SharePreferenceKey.USER_PREFS;

/**
 * Created by beepi on 11/05/2017.
 */

public class MovieInterceptor implements Interceptor {
    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_AUTHORIZE = "Authorization";
    public static final String HEADER_VALUE_ACCEPT = "application/json";

    public MovieInterceptor() {
    }

    public static String getToken() {
        SharePreferenceImp sharePreferenceImp =
            new SharePreferenceImp(App.getContext());
        String json = sharePreferenceImp.get(USER_PREFS, String.class);
        User user = new Gson().fromJson(json, User.class);
        return user != null ? user.getToken() : null;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder().header(HEADER_ACCEPT, HEADER_VALUE_ACCEPT);
        String token = getToken();
        if (token != null) {
            builder.header(HEADER_AUTHORIZE, token);
        }
        return chain.proceed(builder.build());
    }
}
