package com.anh.movie.screen.actordetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.anh.movie.R;
import com.anh.movie.data.model.Actor;
import com.anh.movie.databinding.ActorDetailActivityBinding;
import com.anh.movie.screen.BaseActivity;

import static com.anh.movie.utils.Constant.ACTOR_BUNDLE;

/**
 * Created by anh on 12/5/2017.
 */

public class ActorDetailActivity extends BaseActivity {
    private ActorDetailViewModel mViewModel;
    private Actor mActor;

    public static Intent getIntent(Context context, Actor actor) {
        Intent intent = new Intent(context, ActorDetailActivity.class);
        intent.putExtra(ACTOR_BUNDLE, actor);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActor = getIntent().getExtras().getParcelable(ACTOR_BUNDLE);
        mViewModel = new ActorDetailViewModel(this, mActor, getSupportFragmentManager());
        ActorDetailActivityBinding binding =
                DataBindingUtil.setContentView(this, R.layout.actor_detail_activity);
        binding.setViewModel(mViewModel);
    }
}
