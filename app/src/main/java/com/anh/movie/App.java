package com.anh.movie;

import android.app.Application;
import android.content.Context;
import com.anh.movie.data.source.SharePreferenceApi;
import com.anh.movie.data.source.SharePreferenceImp;

/**
 * Created by anh on 11/30/2017.
 */

public class App extends Application {
    private static Context mContext;
    private SharePreferenceApi mPreferenceApi;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mPreferenceApi = new SharePreferenceImp(this);
    }

    public static Context getContext() {
        return mContext;
    }
}
