package com.song.deviceinfo.ui.thermal;

import com.song.deviceinfo.ui.base.NormalAdapter;
import com.song.deviceinfo.ui.base.NormalFragment;
import com.song.deviceinfo.ui.base.NormalViewModel;
import com.song.deviceinfo.utils.ThreadPoolUtils;

import java.util.List;

import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProviders;

public class ThermalFragment extends NormalFragment<Pair<String, String>> {

    @Override
    protected NormalAdapter crateAdapter() {
        return new ThermalAdapter(getContext());
    }

    @Override
    protected NormalViewModel crateViewModel() {
        return ViewModelProviders.of(this).get(ThermalViewModel.class);
    }

    @Override
    protected void refreshData() {
        ThreadPoolUtils.getInstance().execute(() -> {
            final List<Pair<String, String>> list = ((ThermalViewModel) viewModel).getThermalInfo();
            mainHandler.post(() -> {
                viewModel.setValue(list);
            });
        });
    }
}
