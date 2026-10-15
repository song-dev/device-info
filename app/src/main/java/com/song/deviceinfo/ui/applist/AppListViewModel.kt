package com.song.deviceinfo.ui.applist

import android.content.Context
import com.song.deviceinfo.info.AppListInfo
import com.song.deviceinfo.model.beans.ApplicationBean
import com.song.deviceinfo.ui.base.BaseViewModel

/**
 * Created by chensongsong on 2020/6/3.
 */
class AppListViewModel : BaseViewModel<ApplicationBean>() {
    fun getAppListInfo(context: Context): List<ApplicationBean> {
        return AppListInfo.getAppListInfo(context)
    }
}
