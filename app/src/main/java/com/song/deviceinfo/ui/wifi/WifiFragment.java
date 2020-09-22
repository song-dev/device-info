package com.song.deviceinfo.ui.wifi;

import com.song.deviceinfo.ui.base.BaseAdapter;
import com.song.deviceinfo.ui.base.BaseViewModel;
import com.song.deviceinfo.ui.base.NormalAdapter;
import com.song.deviceinfo.ui.base.NormalFragment;

import java.util.List;

import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by chensongsong on 2020/9/22.
 */
public class WifiFragment extends NormalFragment {

    @Override
    protected BaseAdapter crateAdapter() {
        return new NormalAdapter(getContext());
    }

    @Override
    protected BaseViewModel crateViewModel() {
        return ViewModelProviders.of(this).get(WifiViewModel.class);
    }

    @Override
    protected List<Pair<String, String>> getNormalInfo() {
        return ((WifiViewModel) viewModel).getWifiInfo(getContext());
    }
}
