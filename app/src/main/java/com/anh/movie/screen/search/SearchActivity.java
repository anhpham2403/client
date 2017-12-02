package com.anh.movie.screen.search;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import com.anh.movie.R;
import com.anh.movie.databinding.SearchActivityBinding;
import com.anh.movie.screen.BaseActivity;

/**
 * Created by anh on 12/1/2017.
 */

public class SearchActivity extends BaseActivity {
    private SearchViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new SearchViewModel(this);
        SearchActivityBinding binding =
                DataBindingUtil.setContentView(this, R.layout.search_activity);
        binding.setViewModel(mViewModel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem search_item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search_item.getActionView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
