package com.anh.movie.screen.moviedetail.review;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.anh.movie.R;
import com.anh.movie.data.model.Movie;
import com.anh.movie.databinding.ReviewFragmentBinding;
import com.anh.movie.screen.BaseFragment;
import com.anh.movie.screen.moviedetail.detail.DetailFragment;

import static com.anh.movie.utils.Constant.MOVIE_BUNDLE;

/**
 * Created by anh on 12/5/2017.
 */

public class ReviewFragment extends BaseFragment {
    private ReviewViewModel mViewModel;
    public static ReviewFragment newInstance(Movie movie) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_BUNDLE, movie);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        Movie movie = getArguments().getParcelable(MOVIE_BUNDLE);
        mViewModel = new ReviewViewModel(getContext(), movie);
        ReviewFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.review_fragment, container, false);
        binding.setViewModel(mViewModel);
        return binding.getRoot();
    }
}
