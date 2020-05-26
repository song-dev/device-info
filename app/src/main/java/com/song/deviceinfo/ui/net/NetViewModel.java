package com.song.deviceinfo.ui.net;

import android.content.Context;

import com.song.deviceinfo.info.NetWorkInfo;

import java.util.List;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NetViewModel extends ViewModel {

    private MutableLiveData<List<Pair<String, String>>> mRecyclerView;

    public NetViewModel() {
        mRecyclerView = new MutableLiveData<>();
    }

    public LiveData<List<Pair<String, String>>> getRecyclerView() {
        return mRecyclerView;
    }

    public void setValue(List<Pair<String, String>> list) {
        mRecyclerView.setValue(list);
    }

    public List<Pair<String, String>> getNetWorkInfo(Context context) {
        return NetWorkInfo.getNetWorkInfo(context);
    }
}