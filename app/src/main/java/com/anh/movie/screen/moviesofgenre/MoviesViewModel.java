package com.anh.movie.screen.moviesofgenre;

import android.content.Context;
import android.databinding.Bindable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.anh.movie.data.model.Genre;
import com.anh.movie.screen.BaseViewModel;
import com.anh.movie.screen.home.listmovie.ListMovieFragment;
import com.anh.movie.utils.Constant;

/**
 * Created by anh on 12/3/2017.
 */

public class MoviesViewModel extends BaseViewModel {
    private Context mContext;
    private FragmentManager mManager;
    private Genre mGenre;
    private Fragment mFragment;

    public MoviesViewModel(Context context, FragmentManager manager, Genre genre) {
        mContext = context;
        mManager = manager;
        mGenre = genre;
        mFragment = ListMovieFragment.newInstance(Constant.MOVIES_OF_GENRES, genre.getId());
    }

    @Bindable
    public FragmentManager getManager() {
        return mManager;
    }

    @Bindable
    public Fragment getFragment() {
        return mFragment;
    }
}
