package com.anh.movie.screen.moviedetail.slidebanner;

import android.databinding.Bindable;
import com.anh.movie.screen.BaseViewModel;

/**
 * Exposes the data to be used in the BannerFragment screen.
 */

public class BannerFragmentViewModel extends BaseViewModel {

    private String mPath;

    public BannerFragmentViewModel(String path) {
        mPath = path;
    }

    @Bindable
    public String getPath() {
        return mPath;
    }
}
