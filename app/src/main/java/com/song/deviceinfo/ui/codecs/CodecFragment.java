package com.song.deviceinfo.ui.codecs;

import com.song.deviceinfo.ui.base.BaseAdapter;
import com.song.deviceinfo.ui.base.BaseFragment;
import com.song.deviceinfo.ui.base.BaseViewModel;
import com.song.deviceinfo.utils.LogUtils;
import com.song.deviceinfo.utils.ThreadPoolUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by chensongsong on 2020/6/3.
 */
public class CodecFragment extends BaseFragment<Pair<String, String>> {

    @Override
    protected BaseAdapter crateAdapter() {
        return new CodecAdapter(getContext());
    }

    @Override
    protected BaseViewModel crateViewModel() {
        return ViewModelProviders.of(this).get(CodecViewModel.class);
    }

    @Override
    protected void refreshData() {
        ThreadPoolUtils.getInstance().execute(() -> {
            final List<Pair<String, String>> list = getCodeCInfo();
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

    private List<Pair<String, String>> getCodeCInfo() {
        return ((CodecViewModel) viewModel).getCodeCInfo();
    }

}
