package com.song.deviceinfo.ui.input;

import com.song.deviceinfo.model.beans.InputBean;
import com.song.deviceinfo.ui.base.BaseAdapter;
import com.song.deviceinfo.ui.base.BaseFragment;
import com.song.deviceinfo.ui.base.BaseViewModel;
import com.song.deviceinfo.utils.LogUtils;
import com.song.deviceinfo.utils.ThreadPoolUtils;

import org.json.JSONArray;

import java.util.List;

import androidx.lifecycle.ViewModelProviders;

/**
 * Created by chensongsong on 2020/6/4.
 */
public class InputFragment extends BaseFragment<InputBean> {

    @Override
    protected BaseAdapter crateAdapter() {
        return new InputAdapter(getContext());
    }

    @Override
    protected BaseViewModel crateViewModel() {
        return ViewModelProviders.of(this).get(InputViewModel.class);
    }

    @Override
    protected void refreshData() {
        ThreadPoolUtils.getInstance().execute(() -> {
            final List<InputBean> list = getInputInfo();
            JSONArray jsonArray = new JSONArray();
            for (InputBean bean : list) {
                try {
                    jsonArray.put(bean.toJSON());
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

    private List<InputBean> getInputInfo() {
        return ((InputViewModel) viewModel).getInputInfo();
    }

}
