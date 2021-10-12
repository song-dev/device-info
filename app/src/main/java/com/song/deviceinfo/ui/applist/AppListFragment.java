package com.song.deviceinfo.ui.applist;

import com.song.deviceinfo.model.beans.ApplicationBean;
import com.song.deviceinfo.ui.base.BaseAdapter;
import com.song.deviceinfo.ui.base.BaseFragment;
import com.song.deviceinfo.ui.base.BaseViewModel;
import com.song.deviceinfo.utils.LogUtils;
import com.song.deviceinfo.utils.ThreadPoolUtils;

import org.json.JSONArray;

import java.util.List;

import androidx.lifecycle.ViewModelProviders;

/**
 * Created by chensongsong on 2020/6/3.
 */
public class AppListFragment extends BaseFragment<ApplicationBean> {

    @Override
    protected BaseAdapter crateAdapter() {
        return new AppListAdapter(getContext());
    }

    @Override
    protected BaseViewModel crateViewModel() {
        return ViewModelProviders.of(this).get(AppListViewModel.class);
    }

    @Override
    protected void refreshData() {
        ThreadPoolUtils.getInstance().execute(() -> {
            final List<ApplicationBean> list = getAppListInfo();
            JSONArray jsonArray = new JSONArray();
            for (ApplicationBean bean : list) {
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

    private List<ApplicationBean> getAppListInfo() {
        return ((AppListViewModel) viewModel).getAppListInfo(getContext());
    }

}
