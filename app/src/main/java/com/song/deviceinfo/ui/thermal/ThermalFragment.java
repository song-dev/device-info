package com.song.deviceinfo.ui.thermal;

import com.song.deviceinfo.ui.base.NormalAdapter;
import com.song.deviceinfo.ui.base.NormalFragment;
import com.song.deviceinfo.utils.ThreadPoolUtils;

import java.util.List;

import androidx.core.util.Pair;

public class ThermalFragment extends NormalFragment<Pair<String, String>> {

    @Override
    protected NormalAdapter crateAdapter() {
        return new ThermalAdapter();
    }

    @Override
    protected void refreshData() {
        ThreadPoolUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                final List<Pair<String, String>> list = viewModel.getNetWorkInfo(getContext());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.setValue(list);
                    }
                });
            }
        });
    }
}
