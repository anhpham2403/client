package com.anh.movie.screen.moviedetail.slidebanner;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

/**
 * Created by workspace on 21/09/2017.
 */

public class BannerViewPagerAdapter extends FragmentPagerAdapter {
    private List<String> mPaths;

    public BannerViewPagerAdapter(FragmentManager fm, List<String> paths) {
        super(fm);
        mPaths = paths;
    }

    @Override
    public Fragment getItem(int position) {
        return BannerFragment.newInstance(mPaths.get(position));
    }

    @Override
    public int getCount() {
        return mPaths != null ? mPaths.size() : 0;
    }
}
