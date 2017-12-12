package com.anh.movie.screen.moviedetail.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.anh.movie.R;
import com.anh.movie.data.model.Movie;
import com.anh.movie.databinding.DetailFragmentBinding;
import com.anh.movie.screen.BaseFragment;
import com.anh.movie.utils.Constant;

import static com.anh.movie.utils.Constant.ID_BUNDLE;
import static com.anh.movie.utils.Constant.MOVIE_BUNDLE;

/**
 * Created by anh on 12/5/2017.
 */

public class DetailFragment extends BaseFragment{
    private DetailViewModel mViewModel;
    public static DetailFragment newInstance(Movie movie) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_BUNDLE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        Movie movie = getArguments().getParcelable(Constant.MOVIE_BUNDLE);
        mViewModel = new DetailViewModel(getContext(), movie);
        DetailFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false);
        binding.setViewModel(mViewModel);
        return binding.getRoot();
    }
}
