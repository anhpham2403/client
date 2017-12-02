package com.anh.movie.screen.home;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.anh.movie.screen.home.listgenre.ListGenreFragment;
import com.anh.movie.screen.home.listmovie.ListMovieFragment;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by anh on 11/26/2017.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static final int COUNT_FRAGMENT = 5;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case StateMode.NOW_PLAYING:
                return ListMovieFragment.newInstance(StateMode.NOW_PLAYING);
            case StateMode.UP_COMING:
                return ListMovieFragment.newInstance(StateMode.UP_COMING);
            case StateMode.POPULAR:
                return ListMovieFragment.newInstance(StateMode.POPULAR);
            case StateMode.TOP_RATE:
                return ListMovieFragment.newInstance(StateMode.TOP_RATE);
            case StateMode.GENRES:
                return ListGenreFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return COUNT_FRAGMENT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case StateMode.NOW_PLAYING:
                return StateName.NOW_PLAYING;
            case StateMode.UP_COMING:
                return StateName.UP_COMING;
            case StateMode.POPULAR:
                return StateName.POPULAR;
            case StateMode.TOP_RATE:
                return StateName.TOP_RATE;
            case StateMode.GENRES:
                return StateName.GENRES;
            default:
                return null;
        }
    }

    @Retention(SOURCE)
    @IntDef({
            StateMode.NOW_PLAYING, StateMode.UP_COMING, StateMode.POPULAR, StateMode.TOP_RATE,
            StateMode.GENRES
    })
    public @interface StateMode {
        int NOW_PLAYING = 0;
        int UP_COMING = 1;
        int POPULAR = 2;
        int TOP_RATE = 3;
        int GENRES = 4;
    }

    @Retention(SOURCE)
    @StringDef({
            StateName.NOW_PLAYING, StateName.UP_COMING, StateName.POPULAR, StateName.TOP_RATE,
            StateName.GENRES
    })
    public @interface StateName {
        String NOW_PLAYING = "Now Playing";
        String UP_COMING = "Up Coming";
        String POPULAR = "Popular";
        String TOP_RATE = "Top Rate";
        String GENRES = "Genres";
    }
}
