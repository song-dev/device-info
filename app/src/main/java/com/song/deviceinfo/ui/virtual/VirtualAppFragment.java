package com.song.deviceinfo.ui.virtual;

import com.song.deviceinfo.ui.base.BaseAdapter;
import com.song.deviceinfo.ui.base.BaseViewModel;
import com.song.deviceinfo.ui.base.NormalAdapter;
import com.song.deviceinfo.ui.base.NormalFragment;

import java.util.List;

import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by chensongsong on 2020/7/10.
 */
public class VirtualAppFragment extends NormalFragment {

    @Override
    protected BaseAdapter crateAdapter() {
        return new NormalAdapter(getContext());
    }

    @Override
    protected BaseViewModel crateViewModel() {
        return ViewModelProviders.of(this).get(VirtualAppViewModel.class);
    }

    @Override
    protected List<Pair<String, String>> getNormalInfo() {
        return ((VirtualAppViewModel) viewModel).getVirtualAppInfo(getContext());
    }
}
