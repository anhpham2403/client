package com.anh.movie.screen.moviedetail.slidebanner;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.anh.movie.R;
import com.anh.movie.databinding.FragmentBannerBinding;
import com.anh.movie.screen.BaseFragment;
import com.anh.movie.utils.Constant;

/**
 * BannerFragment Screen.
 */
public class BannerFragment extends BaseFragment {

    private BannerFragmentViewModel mViewModel;

    public static BannerFragment newInstance(String path) {
        BannerFragment fragment = new BannerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.PATH_BUNDLE, path);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        String path = getArguments().getString(Constant.PATH_BUNDLE);
        mViewModel = new BannerFragmentViewModel(path,getActivity().getSupportFragmentManager());
        FragmentBannerBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_banner, container, false);
        binding.setViewModel(mViewModel);
        return binding.getRoot();
    }
}
