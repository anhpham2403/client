package com.anh.movie.screen.home;

import android.content.Context;
import android.databinding.Bindable;
import android.support.v4.app.FragmentManager;
import com.anh.movie.screen.BaseViewModel;

/**
 * Created by anh on 11/25/2017.
 */

public class HomeViewModel extends BaseViewModel {
    private ViewPagerAdapter mAdapter;
    private Context mContext;

    public HomeViewModel(Context context, FragmentManager manager) {
        mContext = context;
        mAdapter = new ViewPagerAdapter(manager);
    }

    @Bindable
    public ViewPagerAdapter getAdapter() {
        return mAdapter;
    }
}
