package com.anh.movie.screen;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

import android.databinding.BaseObservable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * BaseViewModel
 */
public abstract class BaseViewModel extends BaseObservable {
    private final CompositeDisposable mDisposable;

    public BaseViewModel() {
        mDisposable = new CompositeDisposable();
    }

    public void onStart() {
    }

    public void onStop() {
    }

    public void onDestroy() {
        mDisposable.dispose();
    }

    public CompositeDisposable getDisposable() {
        return mDisposable;
    }
}
