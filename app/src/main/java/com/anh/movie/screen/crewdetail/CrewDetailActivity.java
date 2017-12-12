package com.anh.movie.screen.crewdetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.anh.movie.R;
import com.anh.movie.data.model.Actor;
import com.anh.movie.data.model.Crew;
import com.anh.movie.databinding.ActorDetailActivityBinding;
import com.anh.movie.databinding.CrewDetailActivityBinding;
import com.anh.movie.screen.BaseActivity;

import static com.anh.movie.utils.Constant.ACTOR_BUNDLE;
import static com.anh.movie.utils.Constant.CREW_BUNDLE;

/**
 * Created by anh on 12/5/2017.
 */

public class CrewDetailActivity extends BaseActivity {
    private CrewDetailViewModel mViewModel;
    private Crew mCrew;

    public static Intent getIntent(Context context, Crew crew) {
        Intent intent = new Intent(context, CrewDetailActivity.class);
        intent.putExtra(CREW_BUNDLE, crew);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrew = getIntent().getExtras().getParcelable(CREW_BUNDLE);
        mViewModel = new CrewDetailViewModel(this, mCrew, getSupportFragmentManager());
        CrewDetailActivityBinding binding =
                DataBindingUtil.setContentView(this, R.layout.crew_detail_activity);
        binding.setViewModel(mViewModel);
    }
}
