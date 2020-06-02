package com.song.deviceinfo.ui.store;

import com.song.deviceinfo.model.beans.StorageBean;
import com.song.deviceinfo.ui.base.BaseAdapter;
import com.song.deviceinfo.ui.base.BaseFragment;
import com.song.deviceinfo.ui.base.BaseViewModel;
import com.song.deviceinfo.utils.ThreadPoolUtils;

import java.util.List;

import androidx.lifecycle.ViewModelProviders;

/**
 * Created by chensongsong on 2020/6/1.
 */
public class StoreFragment extends BaseFragment<StorageBean> {

    @Override
    protected BaseAdapter crateAdapter() {
        return new StoreAdapter(getContext());
    }

    @Override
    protected BaseViewModel crateViewModel() {
        return ViewModelProviders.of(this).get(StoreViewModel.class);
    }

    @Override
    protected void refreshData() {
        ThreadPoolUtils.getInstance().execute(() -> {
            final List<StorageBean> list = getStoreInfo();
            mainHandler.post(() -> {
                viewModel.setValue(list);
            });
        });
    }

    private List<StorageBean> getStoreInfo() {
        return ((StoreViewModel) viewModel).getStoreInfo(getContext());
    }

}
