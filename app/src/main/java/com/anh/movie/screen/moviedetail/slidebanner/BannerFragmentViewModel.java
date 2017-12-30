package com.anh.movie.screen.moviedetail.slidebanner;

import android.databinding.Bindable;
import android.support.v4.app.FragmentManager;
import com.anh.movie.BR;
import com.anh.movie.screen.BaseViewModel;

/**
 * Exposes the data to be used in the BannerFragment screen.
 */

public class BannerFragmentViewModel extends BaseViewModel {

    private String mPath;

    public BannerFragmentViewModel(String path, FragmentManager manager) {
        mPath = path;
    }

    @Bindable
    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
        notifyPropertyChanged(BR.path);
    }
}
