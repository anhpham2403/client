package com.anh.movie.screen.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.anh.movie.R;
import com.anh.movie.data.model.User;
import com.anh.movie.data.source.SharePreferenceApi;
import com.anh.movie.data.source.SharePreferenceImp;
import com.anh.movie.databinding.HomeActivityBinding;
import com.anh.movie.screen.BaseActivity;
import com.anh.movie.screen.favorite.FavoriteActivity;
import com.anh.movie.screen.login.LoginActivity;
import com.anh.movie.screen.register.RegisterActivity;
import com.anh.movie.screen.register.TypeMode;
import com.anh.movie.screen.search.SearchActivity;
import com.anh.movie.utils.Constant;
import com.anh.movie.widget.CircleImageView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import static com.anh.movie.data.source.SharePreferenceKey.USER_PREFS;

/**
 * Created by anh on 11/25/2017.
 */

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private HomeViewModel mViewModel;
    private static final int REQUEST_CODE = 1;
    private SharePreferenceApi mPreference;
    private TextView mUsername;
    private TextView mPassword;
    private Menu mMenu;
    private CircleImageView mImageView;
    private User mUser;
    private SharePreferenceApi mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new HomeViewModel(this, getSupportFragmentManager());
        HomeActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.home_activity);
        binding.setViewModel(mViewModel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mPreference = new SharePreferenceImp(this);
        View header = navigationView.getHeaderView(0);
        mUsername = (TextView) header.findViewById(R.id.text_name);
        mPassword = (TextView) header.findViewById(R.id.text_email);
        mImageView = (CircleImageView) header.findViewById(R.id.avatar);
        mMenu = navigationView.getMenu();
        mSharedPreferences = new SharePreferenceImp(this);
        Gson gson = new Gson();
        String json = mSharedPreferences.get(USER_PREFS, String.class);
        mUser = gson.fromJson(json, User.class);
        if (mUser != null) {
            String imageUrl = Constant.BASE_URL_IMAGE1 + mUser.getAvatarUrl();
            Picasso.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(mImageView);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Gson gson = new Gson();
        String json = mPreference.get(USER_PREFS, String.class);
        if (json == null) {
            return;
        }
        User user = gson.fromJson(json, User.class);
        mUsername.setText(user.getUsername());
        mPassword.setText(user.getEmail());
        MenuItem login = mMenu.findItem(R.id.login);
        login.setVisible(false);
        MenuItem update = mMenu.findItem(R.id.update);
        update.setTitle(user.getName());
        update.setVisible(true);
        MenuItem logout = mMenu.findItem(R.id.log_out);
        logout.setVisible(true);
        MenuItem favorite = mMenu.findItem(R.id.favorite);
        favorite.setVisible(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.update:
                startActivity(RegisterActivity.getIntent(this, TypeMode.UPDATE));
                break;
            case R.id.favorite:
                Intent intent2 = new Intent(this, FavoriteActivity.class);
                startActivity(intent2);
                break;
            case R.id.log_out:
                mPreference.remove(USER_PREFS);
                Intent intent1 = new Intent(this, HomeActivity.class);
                startActivity(intent1);
                finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
