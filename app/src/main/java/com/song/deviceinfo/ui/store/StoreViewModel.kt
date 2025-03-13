package com.song.deviceinfo.ui.store

import android.content.Context
import com.song.deviceinfo.info.StoreInfo
import com.song.deviceinfo.model.beans.StorageBean
import com.song.deviceinfo.ui.base.BaseViewModel

/**
 * Created by chensongsong on 2020/6/1.
 */
class StoreViewModel : BaseViewModel<StorageBean>() {
    fun getStoreInfo(context: Context): List<StorageBean> {
        return StoreInfo.getStoreInfo(context)
    }
}
