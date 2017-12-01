package com.anh.movie.screen.home.listmovie;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.anh.movie.R;
import com.anh.movie.databinding.ListMovieFragmentBinding;
import com.anh.movie.screen.BaseFragment;

import static com.anh.movie.utils.Constant.ID_BUNDLE;
import static com.anh.movie.utils.Constant.MOVIE_BUNDLE;

/**
 * Created by anh on 11/26/2017.
 */

public class ListMovieFragment extends BaseFragment {
    private ListMovieViewModel mViewModel;
    private int mCategory;
    private int mId;

    public static ListMovieFragment newInstance(int category) {
        ListMovieFragment fragment = new ListMovieFragment();
        Bundle args = new Bundle();
        args.putInt(MOVIE_BUNDLE, category);
        fragment.setArguments(args);
        return fragment;
    }

    public static ListMovieFragment newInstance(int category, int id) {
        ListMovieFragment fragment = new ListMovieFragment();
        Bundle args = new Bundle();
        args.putInt(MOVIE_BUNDLE, category);
        args.putInt(ID_BUNDLE, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mCategory = getArguments().getInt(MOVIE_BUNDLE, -1);
        mId = getArguments().getInt(ID_BUNDLE, -1);
        mViewModel = new ListMovieViewModel(getContext(), mCategory, mId);
        ListMovieFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.list_movie_fragment, container, false);
        binding.setViewModel(mViewModel);
        return binding.getRoot();
    }
}
