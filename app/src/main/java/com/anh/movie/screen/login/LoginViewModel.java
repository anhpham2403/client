package com.anh.movie.screen.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.widget.Toast;
import com.anh.movie.BR;
import com.anh.movie.R;
import com.anh.movie.data.model.User;
import com.anh.movie.data.source.MovieServiceClient;
import com.anh.movie.data.source.RemoteDataSource;
import com.anh.movie.data.source.SharePreferenceApi;
import com.anh.movie.data.source.SharePreferenceImp;
import com.anh.movie.screen.BaseViewModel;
import com.anh.movie.screen.home.HomeActivity;
import com.anh.movie.screen.register.RegisterActivity;
import com.anh.movie.screen.register.TypeMode;
import com.google.gson.Gson;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.anh.movie.data.source.SharePreferenceKey.PASS_WORD_PREFS;
import static com.anh.movie.data.source.SharePreferenceKey.USER_NAME_PREFS;
import static com.anh.movie.data.source.SharePreferenceKey.USER_PREFS;

/**
 * Created by anh on 11/26/2017.
 */

public class LoginViewModel extends BaseViewModel {
    private String mUsername;
    private String mPassword;
    private String mUsernameError;
    private String mPasswordError;
    private boolean mIsRememberAccount;
    private Context mContext;
    private boolean mIsLoading;
    private RemoteDataSource mDataSource;
    private SharePreferenceApi mSharedPreferences;

    public LoginViewModel(Context context) {
        mDataSource = new RemoteDataSource(MovieServiceClient.getInstance());
        mSharedPreferences = new SharePreferenceImp(context);
        mContext = context;
        mIsLoading = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadDate();
    }

    public void loadDate() {
        String userName = mSharedPreferences.get(USER_NAME_PREFS, String.class);
        String passWord = mSharedPreferences.get(PASS_WORD_PREFS, String.class);
        if (userName != null && passWord != null) {
            setUsername(userName);
            setPassword(passWord);
            setRememberAccount(true);
        }
    }

    public boolean validateDataInput(String username, String password) {
        boolean isValid = true;
        if (TextUtils.isEmpty(username)) {
            isValid = false;
            setUsernameError(mContext.getString(R.string.msg_error_user_name));
        }
        if (TextUtils.isEmpty(password)) {
            isValid = false;
            setPasswordError(mContext.getString(R.string.msg_error_user_name));
        }
        return isValid;
    }

    public void login(final String userName, final String passWord) {
        getDisposable().add(mDataSource.login(userName, passWord)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<User>() {
                    @Override
                    public void onNext(User value) {
                        Gson gson = new Gson();
                        String json = gson.toJson(value);
                        mSharedPreferences.put(USER_PREFS, json);
                        if (isRememberAccount()) {
                            saveRememberAccount(userName, passWord);
                        } else {
                            removeRememberAccount();
                        }
                        setLoading(false);
                        Intent intent = new Intent(mContext, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        ((Activity) mContext).finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        setLoading(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    public void onLoginClick() {
        if (!validateDataInput(mUsername, mPassword)) {
            return;
        }
        setLoading(true);
        login(mUsername, mPassword);
    }

    @Bindable
    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getUsernameError() {
        return mUsernameError;
    }

    public void setUsernameError(String usernameError) {
        mUsernameError = usernameError;
        notifyPropertyChanged(BR.usernameError);
    }

    @Bindable
    public String getPasswordError() {
        return mPasswordError;
    }

    public void setPasswordError(String passwordError) {
        mPasswordError = passwordError;
        notifyPropertyChanged(BR.passwordError);
    }

    @Bindable
    public boolean isRememberAccount() {
        return mIsRememberAccount;
    }

    public void setRememberAccount(boolean rememberAccount) {
        mIsRememberAccount = rememberAccount;
        notifyPropertyChanged(BR.rememberAccount);
    }

    @Bindable
    public boolean isLoading() {
        return mIsLoading;
    }

    public void setLoading(boolean loading) {
        mIsLoading = loading;
        notifyPropertyChanged(BR.loadingMore);
    }

    public void saveRememberAccount(String user, String passWord) {
        mSharedPreferences.put(USER_NAME_PREFS, user);
        mSharedPreferences.put(PASS_WORD_PREFS, passWord);
    }

    public void removeRememberAccount() {
        mSharedPreferences.remove(USER_NAME_PREFS);
        mSharedPreferences.remove(PASS_WORD_PREFS);
    }

    public void onClickRegister() {
        mContext.startActivity(RegisterActivity.getIntent(mContext, TypeMode.REGISTER));
    }
}
