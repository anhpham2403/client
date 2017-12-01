package com.anh.movie.screen.login;

import android.content.Intent;
import android.databinding.Bindable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.anh.movie.screen.BaseViewModel;

/**
 * Created by anh on 11/26/2017.
 */

public class LoginViewModel extends BaseViewModel {
    private static final int REQUEST_CODE = 1;
    private String loginUrl = "https://www.themoviedb.org/authenticate/";
    private AppCompatActivity mActivity;
    private WebViewClient mClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (url.contains("/allow")) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.putExtra("token", mToken);
                        mActivity.setResult(REQUEST_CODE, intent);
                        mActivity.finish();
                    }
                }, 1000);
            }
        }
    };
    private String mToken;

    public LoginViewModel(AppCompatActivity activity) {
        mActivity = activity;
        mToken = activity.getIntent().getExtras().getString("token");
    }

    @Bindable
    public String getLoginUrl() {
        return loginUrl + mToken;
    }

    @Bindable
    public WebViewClient getClient() {
        return mClient;
    }
}
