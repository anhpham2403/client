package com.anh.movie.screen.moviesofgenre;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import com.anh.movie.R;
import com.anh.movie.data.model.Genre;
import com.anh.movie.databinding.MoviesActivityBinding;
import com.anh.movie.screen.BaseActivity;

import static com.anh.movie.utils.Constant.GENRE_BUNDLE;

/**
 * Created by anh on 12/3/2017.
 */

public class MoviesActivity extends BaseActivity {
    private MoviesViewModel mViewModel;

    public static Intent getIntentMoviesOfGenre(Context context, Genre genre) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.putExtra(GENRE_BUNDLE, genre);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Genre genre = getIntent().getExtras().getParcelable(GENRE_BUNDLE);
        setTitle(genre.getName());
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewModel = new MoviesViewModel(getApplicationContext(), fragmentManager, genre);
        MoviesActivityBinding binding =
                DataBindingUtil.setContentView(this, R.layout.movies_activity);
        binding.setViewModel(mViewModel);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
