package com.anh.movie.utils.binding;

import android.databinding.BindingAdapter;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import com.anh.movie.screen.home.ViewPagerAdapter;
import com.anh.movie.utils.Constant;
import com.anh.movie.utils.LayoutManagers;
import com.squareup.picasso.Picasso;

/**
 * Created by anh on 11/25/2017.
 */

public final class BindingUtils {
    private BindingUtils() {
    }

    @BindingAdapter({ "bind:adapter" })
    public static void setAdapterForViewPager(ViewPager viewPager, ViewPagerAdapter adapter) {
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(6);
    }

    @BindingAdapter({ "bind:viewPager" })
    public static void setViewPagerForTabLayout(TabLayout tabLayout, ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);
    }

    @BindingAdapter({ "bind:recyclerAdapter" })
    public static void setAdapterForRecyclerView(RecyclerView recyclerView,
            RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({ "bind:imageUrl" })
    public static void loadImage(ImageView view, String imagePath) {
        String imageUrl = Constant.BASE_URL_IMAGE + imagePath;
        Picasso.with(view.getContext()).load(imageUrl).into(view);
    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView,
            LayoutManagers.LayoutManagerFactory layoutManagerFactory) {
        recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
    }

    @BindingAdapter({ "scrollListenner" })
    public static void setScrollListenner(RecyclerView recyclerView,
            RecyclerView.OnScrollListener listener) {
        recyclerView.addOnScrollListener(listener);
    }

    @BindingAdapter({ "bind:manager", "bind:fragment" })
    public static void setFragmentManager(FrameLayout layout, FragmentManager manager,
            Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(layout.getId(), fragment).commit();
    }

    /* @BindingAdapter({ "bind:keyVideo" })
     public static void setInitialize(final YouTubePlayerView playerView, final String keyVideo) {
         if (keyVideo == null) {
             return;
         }
         playerView.initialize(BuildConfig.API_KEY_YOUTUBE,
             new YouTubePlayer.OnInitializedListener() {
                 @Override
                 public void onInitializationSuccess(YouTubePlayer.Provider provider,
                     YouTubePlayer youTubePlayer, boolean b) {
                     youTubePlayer.cueVideo(keyVideo);
                 }

                 @Override
                 public void onInitializationFailure(YouTubePlayer.Provider provider,
                     YouTubeInitializationResult youTubeInitializationResult) {
                     Context context = playerView.getContext();
                     Toast.makeText(context, youTubeInitializationResult.toString(),
                         Toast.LENGTH_LONG).show();
                 }
             });
     }*/
    @BindingAdapter({ "bind:webViewClient" })
    public static void setWebViewClient(WebView view, WebViewClient client) {
        view.setWebViewClient(client);
    }

    @BindingAdapter({ "bind:loadUrl" })
    public static void loadUrl(WebView view, String url) {
        view.loadUrl(url);
    }

    @BindingAdapter({ "bind:setJavaScript" })
    public static void setJavaScriptEnabled(WebView view, boolean b) {
        view.getSettings().setJavaScriptEnabled(b);
    }

    @BindingAdapter({ "bind:setQueryTextListener" })
    public static void setSearchListener(SearchView view, SearchView.OnQueryTextListener listener) {
        view.setOnQueryTextListener(listener);
        view.clearFocus();
    }
}