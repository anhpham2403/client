package com.anh.movie.screen.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.anh.movie.R;
import com.anh.movie.data.model.Movie;
import com.anh.movie.databinding.DetailMovieActivityBinding;
import com.anh.movie.screen.BaseActivity;
import com.anh.movie.utils.Constant;

/**
 * Created by anh on 12/3/2017.
 */

public class DetailActivity extends BaseActivity {
    private DetailViewModel mViewModel;

    public static Intent getIntent(Movie movie, Context context) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.MOVIE_BUNDLE, movie);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Movie movie = getIntent().getExtras().getParcelable(Constant.MOVIE_BUNDLE);
        mViewModel = new DetailViewModel(this, getSupportFragmentManager(), movie);
        DetailMovieActivityBinding binding =
                DataBindingUtil.setContentView(this, R.layout.detail_movie_activity);
        binding.setViewModel(mViewModel);
    }
}
