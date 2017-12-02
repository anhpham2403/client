package com.anh.movie.screen.moviedetail.character;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.anh.movie.R;
import com.anh.movie.data.model.Movie;
import com.anh.movie.databinding.CharacterFragmentBinding;
import com.anh.movie.screen.BaseFragment;

import static com.anh.movie.utils.Constant.MOVIE_BUNDLE;

/**
 * Created by anh on 12/5/2017.
 */

public class CharacterFragment extends BaseFragment {
    private CharacterViewModel mViewModel;

    public static CharacterFragment newInstance(Movie movie) {
        CharacterFragment fragment = new CharacterFragment();
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
        mViewModel = new CharacterViewModel(getContext(), movie);
        CharacterFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.character_fragment, container, false);
        binding.setViewModel(mViewModel);
        return binding.getRoot();
    }
}
