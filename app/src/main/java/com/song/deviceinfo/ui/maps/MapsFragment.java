package com.song.deviceinfo.ui.maps;

import com.song.deviceinfo.ui.base.BaseViewModel;
import com.song.deviceinfo.ui.oneline.OneLineFragment;
import com.song.deviceinfo.utils.LogUtils;
import com.song.deviceinfo.utils.ThreadPoolUtils;

import org.json.JSONArray;

import java.util.List;

import androidx.lifecycle.ViewModelProviders;

/**
 * Created by chensongsong on 2021/9/10.
 */
public class MapsFragment extends OneLineFragment {

    @Override
    protected BaseViewModel crateViewModel() {
        return ViewModelProviders.of(this).get(MapsViewModel.class);
    }

    @Override
    protected void refreshData() {
        ThreadPoolUtils.getInstance().execute(() -> {
            final List<String> list = getMapsInfo();
            JSONArray jsonArray = new JSONArray();
            for (String item : list) {
                try {
                    jsonArray.put(item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            LogUtils.printLongString(jsonArray.toString());
            mainHandler.post(() -> {
                viewModel.setValue(list);
            });
        });
    }

    private List<String> getMapsInfo() {
        return ((MapsViewModel) viewModel).getMapsInfo(getContext());
    }

}
