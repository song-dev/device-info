package com.song.deviceinfo.ui.base;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by chensongsong on 2020/5/26.
 */
public class NormalViewModel<T> extends ViewModel {

    private MutableLiveData<List<T>> mRecyclerView;

    public NormalViewModel() {
        mRecyclerView = new MutableLiveData<>();
    }

    public LiveData<List<T>> getRecyclerView() {
        return mRecyclerView;
    }

    public void setValue(List<T> list) {
        mRecyclerView.setValue(list);
    }
}
