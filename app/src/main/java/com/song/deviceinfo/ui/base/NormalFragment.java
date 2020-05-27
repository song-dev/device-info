package com.song.deviceinfo.ui.base;

import com.song.deviceinfo.utils.ThreadPoolUtils;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/5/27.
 */
public abstract class NormalFragment extends BaseFragment<Pair<String, String>> {

    @Override
    protected void refreshData() {
        ThreadPoolUtils.getInstance().execute(() -> {
            final List<Pair<String, String>> list = getNormalInfo();
            mainHandler.post(() -> {
                viewModel.setValue(list);
            });
        });
    }

    protected abstract List<Pair<String, String>> getNormalInfo();
}
