package com.anh.movie.screen.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.anh.movie.R;
import com.anh.movie.databinding.LoginActivityBinding;
import com.anh.movie.screen.BaseActivity;

/**
 * Created by anh on 11/26/2017.
 */

public class LoginActivity extends BaseActivity {
    private LoginViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.title_login));
        mViewModel = new LoginViewModel(this);
        LoginActivityBinding binding =
                DataBindingUtil.setContentView(this, R.layout.login_activity);
        binding.setViewModel(mViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
