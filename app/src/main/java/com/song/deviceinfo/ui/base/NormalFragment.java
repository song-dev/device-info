package com.song.deviceinfo.ui.base;

import com.song.deviceinfo.utils.LogUtils;
import com.song.deviceinfo.utils.ThreadPoolUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/5/27.
 */
public abstract class NormalFragment extends BaseFragment<android.util.Pair<String, String>> {

    @Override
    protected void refreshData() {
        ThreadPoolUtils.getInstance().execute(() -> {
            final List<Pair<String, String>> list = getNormalInfo();
            JSONObject jsonObject = new JSONObject();
            for (Pair<String, String> pair : list) {
                try {
                    jsonObject.put(Objects.requireNonNull(pair.first), pair.second);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            LogUtils.printLongString(jsonObject.toString());
            mainHandler.post(() -> {
                viewModel.setValue(list);
            });
        });
    }

    protected abstract List<Pair<String, String>> getNormalInfo();
}
