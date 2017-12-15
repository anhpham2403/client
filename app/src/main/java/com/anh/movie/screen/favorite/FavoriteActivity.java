package com.anh.movie.screen.favorite;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.anh.movie.R;
import com.anh.movie.databinding.FavoriteActivityBinding;
import com.anh.movie.screen.BaseActivity;

/**
 * Created by anh on 12/13/2017.
 */

public class FavoriteActivity extends BaseActivity {
    private FavoriteViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new FavoriteViewModel(this);
        FavoriteActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.favorite_activity);
        binding.setViewModel(mViewModel);
    }
}
