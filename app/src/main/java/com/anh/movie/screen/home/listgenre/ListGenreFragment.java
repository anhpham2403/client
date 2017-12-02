package com.anh.movie.screen.home.listgenre;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.anh.movie.R;
import com.anh.movie.databinding.ListGenreFragmentBinding;
import com.anh.movie.screen.BaseFragment;

/**
 * Created by anh on 12/3/2017.
 */

public class ListGenreFragment extends BaseFragment {
    private ListGenreViewModel mViewModel;

    public static ListGenreFragment newInstance() {
        ListGenreFragment fragment = new ListGenreFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mViewModel = new ListGenreViewModel(getContext());
        ListGenreFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.list_genre_fragment, container, false);
        binding.setViewModel(mViewModel);
        return binding.getRoot();
    }
}
