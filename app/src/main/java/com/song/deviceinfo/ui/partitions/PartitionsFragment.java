package com.song.deviceinfo.ui.partitions;

import com.song.deviceinfo.model.beans.PartitionsBean;
import com.song.deviceinfo.ui.base.BaseAdapter;
import com.song.deviceinfo.ui.base.BaseFragment;
import com.song.deviceinfo.ui.base.BaseViewModel;
import com.song.deviceinfo.utils.ThreadPoolUtils;

import java.util.List;

import androidx.lifecycle.ViewModelProviders;

/**
 * Created by chensongsong on 2020/5/29.
 */
public class PartitionsFragment extends BaseFragment<PartitionsBean> {

    @Override
    protected BaseAdapter crateAdapter() {
        return new PartitionsAdapter(getContext());
    }

    @Override
    protected BaseViewModel crateViewModel() {
        return ViewModelProviders.of(this).get(PartitionsViewModel.class);
    }

    @Override
    protected void refreshData() {
        ThreadPoolUtils.getInstance().execute(() -> {
            final List<PartitionsBean> list = getPartitionsInfo();
            mainHandler.post(() -> {
                viewModel.setValue(list);
            });
        });
    }

    private List<PartitionsBean> getPartitionsInfo() {
        return ((PartitionsViewModel) viewModel).getPartitionsInfo();
    }

}
