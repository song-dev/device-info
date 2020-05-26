package com.song.deviceinfo.ui.about;

import android.content.Context;

import com.song.deviceinfo.R;
import com.song.deviceinfo.utils.Constants;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AboutViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setData(Context context) {
        mText.setValue(String.format(context.getString(R.string.about_version), Constants.VERSION));
    }
}
