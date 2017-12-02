package com.anh.movie.screen.moviedetail.crew;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.anh.movie.R;
import com.anh.movie.data.model.Movie;
import com.anh.movie.databinding.CharacterFragmentBinding;
import com.anh.movie.databinding.CrewFragmentBinding;
import com.anh.movie.screen.BaseFragment;

import static com.anh.movie.utils.Constant.MOVIE_BUNDLE;

/**
 * Created by anh on 12/5/2017.
 */

public class CrewFragment extends BaseFragment {
    private CrewViewModel mViewModel;

    public static CrewFragment newInstance(Movie movie) {
        CrewFragment fragment = new CrewFragment();
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
        mViewModel = new CrewViewModel(getContext(), movie);
        CrewFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.crew_fragment, container, false);
        binding.setViewModel(mViewModel);
        return binding.getRoot();
    }
}
