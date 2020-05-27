package com.song.deviceinfo.ui.base;

import com.song.deviceinfo.utils.ThreadPoolUtils;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/5/27.
 */
public abstract class NormalFragment<P> extends BaseFragment<Pair<String, String>> {

//    @Override
//    protected BaseAdapter crateAdapter() {
//        return new NormalAdapter(getContext());
//    }
//
//    @Override
//    protected BaseViewModel crateViewModel() {
//        return ViewModelProviders.of(this).get(NormalViewModel.class);
//    }

    @Override
    protected void refreshData() {
        ThreadPoolUtils.getInstance().execute(() -> {
            final List<Pair<String, String>> list = ((NormalViewModel<P>) viewModel).getNormalInfo();
            mainHandler.post(() -> {
                viewModel.setValue(list);
            });
        });
    }
}
