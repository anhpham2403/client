package com.anh.movie.screen.moviedetail;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.anh.movie.data.model.Movie;
import com.anh.movie.screen.moviedetail.character.CharacterFragment;
import com.anh.movie.screen.moviedetail.crew.CrewFragment;
import com.anh.movie.screen.moviedetail.detail.DetailFragment;
import com.anh.movie.screen.moviedetail.review.ReviewFragment;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by anh on 12/5/2017.
 */

public class DetailViewPager extends FragmentPagerAdapter {
    private static final int COUNT_FRAGMENT = 4     ;
    private Movie mMovie;

    public DetailViewPager(FragmentManager fm, Movie movie) {
        super(fm);
        mMovie = movie;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case StateMode.DETAIL:
                return DetailFragment.newInstance(mMovie);
            case StateMode.CHARACTER:
                return CharacterFragment.newInstance(mMovie);
            case StateMode.CREW:
                return CrewFragment.newInstance(mMovie);
            case StateMode.REVIEW:
                return ReviewFragment.newInstance(mMovie);
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
            case StateMode.DETAIL:
                return StateName.DETAIL;
            case StateMode.CHARACTER:
                return StateName.CHARACTER;
            case StateMode.CREW:
                return StateName.CREW;
            case StateMode.REVIEW:
                return StateName.REVIEW;
            default:
                return null;
        }
    }

    @Retention(SOURCE)
    @IntDef({
            StateMode.CHARACTER, StateMode.DETAIL, StateMode.REVIEW
    })
    public @interface StateMode {
        int DETAIL = 0;
        int CHARACTER = 1;
        int CREW = 2;
        int REVIEW = 3;
    }

    @Retention(SOURCE)
    @StringDef({
            StateName.CHARACTER, StateName.DETAIL, StateName.REVIEW
    })
    public @interface StateName {
        String DETAIL = "Detail";
        String CHARACTER = "Character";
        String CREW = "Crew";
        String REVIEW = "Review";
    }
}
