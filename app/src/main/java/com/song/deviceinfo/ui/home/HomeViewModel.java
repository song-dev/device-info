package com.song.deviceinfo.ui.home;

import android.content.Context;

import com.song.deviceinfo.info.NetWorkInfo;

import java.util.List;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Pair<String, String>>> mRecyclerView;

    public HomeViewModel() {
        mRecyclerView = new MutableLiveData<>();
    }

    public LiveData<List<Pair<String, String>>> getRecyclerView() {
        return mRecyclerView;
    }

    public void setValue(List<Pair<String, String>> list) {
        mRecyclerView.setValue(list);
    }

    public List<Pair<String, String>> getNetWorkInfo(Context context) {
        return NetWorkInfo.getNetInfo(context);
    }
}