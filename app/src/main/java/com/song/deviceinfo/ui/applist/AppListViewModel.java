package com.song.deviceinfo.ui.applist;

import android.content.Context;

import com.song.deviceinfo.info.AppListInfo;
import com.song.deviceinfo.model.beans.ApplicationBean;
import com.song.deviceinfo.ui.base.BaseViewModel;

import java.util.List;

/**
 * Created by chensongsong on 2020/6/3.
 */
public class AppListViewModel extends BaseViewModel {

    protected List<ApplicationBean> getAppListInfo(Context context) {
        return AppListInfo.getAppListInfo(context);
    }

}
