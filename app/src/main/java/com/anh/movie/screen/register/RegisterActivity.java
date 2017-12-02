package com.anh.movie.screen.register;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.anh.movie.R;
import com.anh.movie.databinding.RegisterActivityBinding;
import com.anh.movie.screen.BaseActivity;

import static com.anh.movie.utils.Constant.TYPE_BUNDLE;

/**
 * Created by anh on 12/3/2017.
 */

public class RegisterActivity extends BaseActivity {
    private RegisterViewModel mViewModel;
    public final static int READ_EXTERNAL_REQUEST = 2;

    public static Intent getIntent(Context context, @TypeMode int type) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(TYPE_BUNDLE, type);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int type = getIntent().getExtras().getInt(TYPE_BUNDLE);
        mViewModel = new RegisterViewModel(this, type);
        RegisterActivityBinding binding =
                DataBindingUtil.setContentView(this, R.layout.register_activity);
        binding.setViewModel(mViewModel);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage = data.getData();
        ImageView imageView = (ImageView) findViewById(R.id.avatar);
        imageView.setImageURI(selectedImage);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
            int[] grantResults) {
        if (requestCode != READ_EXTERNAL_REQUEST) return;
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mViewModel.pickImage();
        }
    }
}
