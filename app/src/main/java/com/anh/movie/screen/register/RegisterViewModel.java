package com.anh.movie.screen.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.Bindable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Toast;
import com.android.databinding.library.baseAdapters.BR;
import com.anh.movie.R;
import com.anh.movie.data.model.User;
import com.anh.movie.data.source.MovieServiceClient;
import com.anh.movie.data.source.RemoteDataSource;
import com.anh.movie.data.source.SharePreferenceApi;
import com.anh.movie.data.source.SharePreferenceImp;
import com.anh.movie.screen.BaseViewModel;
import com.google.gson.Gson;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static com.anh.movie.data.source.SharePreferenceKey.PASS_WORD_PREFS;
import static com.anh.movie.data.source.SharePreferenceKey.USER_NAME_PREFS;
import static com.anh.movie.data.source.SharePreferenceKey.USER_PREFS;

/**
 * Created by anh on 12/3/2017.
 */

public class RegisterViewModel extends BaseViewModel {
    private String mUsername;
    private String mName;
    private String mEmail;
    private String mPassword;
    private String mUsernameError;
    private String mNameError;
    private String mEmailError;
    private String mPasswordError;
    private RemoteDataSource mDataSource;
    private SharePreferenceApi mSharedPreferences;
    private Context mContext;
    private boolean mIsLoading;
    private User mUser;
    private boolean mIsEdit;
    private String mOldPassword;
    private String mOldPasswordError;
    private Activity mActivity;
    private String mImageUrl;
    private MultipartBody.Part imagePath;
    public final static int READ_EXTERNAL_REQUEST = 2;
    private int mType;
    private static final String EMAIL_REGEX =
            "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)"
                    + "+[a-zA-Z]{2,6}$";
    private String mButtonName;

    public RegisterViewModel(Activity activity, int type) {
        mActivity = activity;
        this.mContext = activity.getApplicationContext();
        mDataSource = new RemoteDataSource(MovieServiceClient.getInstance());
        mSharedPreferences = new SharePreferenceImp(mContext);
        mIsLoading = false;
        Gson gson = new Gson();
        mIsEdit = false;
        String json = mSharedPreferences.get(USER_PREFS, String.class);
        mUser = gson.fromJson(json, User.class);
        mType = type;
        switch (mType) {
            case TypeMode.REGISTER:
                mButtonName = mContext.getString(R.string.title_register);
                break;
            case TypeMode.UPDATE:
                mButtonName = mContext.getString(R.string.title_update);
                getDataUser(mUser);
                mIsEdit = true;
                break;
            default:
                break;
        }
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
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
        notifyPropertyChanged(BR.email);
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
    public String getNameError() {
        return mNameError;
    }

    public void setNameError(String nameError) {
        mNameError = nameError;
        notifyPropertyChanged(BR.nameError);
    }

    @Bindable
    public String getEmailError() {
        return mEmailError;
    }

    public void setEmailError(String emailError) {
        mEmailError = emailError;
        notifyPropertyChanged(BR.emailError);
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
    public boolean isLoading() {
        return mIsLoading;
    }

    public void setLoading(boolean loading) {
        mIsLoading = loading;
        notifyPropertyChanged(BR.loading);
    }

    public void onRegisterClick() {
        if (!validateDataInput()) {
            return;
        }
        setLoading(true);
        switch (mType) {
            case TypeMode.REGISTER:
                register(mUsername, mName, mEmail, mPassword, imagePath);
                break;
            case TypeMode.UPDATE:
                update(mUsername, mName, mEmail, mOldPassword, mPassword, imagePath);
                break;
            default:
                break;
        }
    }

    public boolean validateDataInput() {
        boolean isValid = true;
        if (TextUtils.isEmpty(mUsername)) {
            isValid = false;
            setUsernameError(mContext.getString(R.string.msg_error_user_name));
        }
        if (TextUtils.isEmpty(mName)) {
            isValid = false;
            setNameError(mContext.getString(R.string.msg_error_user_name));
        }
        if (TextUtils.isEmpty(mEmail)) {
            isValid = false;
            setEmailError(mContext.getString(R.string.msg_error_user_name));
        }
        if (TextUtils.isEmpty(mPassword)) {
            isValid = false;
            setPasswordError(mContext.getString(R.string.msg_error_user_name));
        }
        if (!mEmail.matches(EMAIL_REGEX)) {
            setEmailError(mContext.getString(R.string.msg_error_email_invalid));
            return false;
        }
        if (!isEdit()) {
            return isValid;
        }
        if (TextUtils.isEmpty(mOldPassword)) {
            isValid = false;
            setPasswordError(mContext.getString(R.string.msg_error_old_password));
        }
        return isValid;
    }

    public void register(final String userName, final String name, final String email,
            final String passWord, final MultipartBody.Part imagePath) {
        getDisposable().add(mDataSource.register(userName, name, email, passWord, imagePath)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<User>() {
                    @Override
                    public void onNext(User value) {
                        Gson gson = new Gson();
                        String json = gson.toJson(value);
                        mSharedPreferences.put(USER_PREFS, json);
                        saveRememberAccount(userName, passWord);
                        setLoading(false);
                        mActivity.finish();
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

    public void update(final String userName, final String name, final String email,
            final String oldPassword, final String passWord, final MultipartBody.Part imagePath) {
        getDisposable().add(
                mDataSource.update(userName, name, email, oldPassword, passWord, imagePath)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableObserver<User>() {
                            @Override
                            public void onNext(User value) {
                                Gson gson = new Gson();
                                String json = gson.toJson(value);
                                mSharedPreferences.put(USER_PREFS, json);
                                saveRememberAccount(userName, passWord);
                                setLoading(false);
                                mActivity.finish();
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

    public void saveRememberAccount(String user, String passWord) {
        mSharedPreferences.put(USER_NAME_PREFS, user);
        mSharedPreferences.put(PASS_WORD_PREFS, passWord);
    }

    private void getDataUser(User user) {
        setName(user.getName());
        setPassword(user.getPassword());
        setEmail(user.getEmail());
        setUsername(user.getUsername());
        setImageUrl(user.getAvatarUrl());
    }

    @Bindable
    public boolean isEdit() {
        return mIsEdit;
    }

    public void setEdit(boolean edit) {
        mIsEdit = edit;
        notifyPropertyChanged(BR.edit);
    }

    @Bindable
    public String getOldPassword() {
        return mOldPassword;
    }

    public void setOldPassword(String oldPassword) {
        mOldPassword = oldPassword;
        notifyPropertyChanged(BR.oldPassword);
    }

    @Bindable
    public String getOldPasswordError() {
        return mOldPasswordError;
    }

    public void setOldPasswordError(String oldPasswordError) {
        mOldPasswordError = oldPasswordError;
        notifyPropertyChanged(BR.oldPasswordError);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onChooseImage() {
        requestPermionAndPickImage();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();

                File file = new File(getRealPathFromURI(selectedImage));
                RequestBody requestBody = RequestBody.create(
                        MediaType.parse(mActivity.getContentResolver().getType(selectedImage)),
                        file);
                imagePath = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                setImageUrl(null);
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = mActivity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public void pickImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        mActivity.startActivityForResult(photoPickerIntent, 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermionAndPickImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            pickImage();
            return;
        }
        int result = ContextCompat.checkSelfPermission(mContext, READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            pickImage();
        } else {
            mActivity.requestPermissions(new String[] {
                    READ_EXTERNAL_STORAGE
            }, READ_EXTERNAL_REQUEST);
        }
    }

    @Bindable
    public String getButtonName() {
        return mButtonName;
    }

    public void setButtonName(String buttonName) {
        mButtonName = buttonName;
        notifyPropertyChanged(BR.buttonName);
    }

    @Bindable
    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }
}
