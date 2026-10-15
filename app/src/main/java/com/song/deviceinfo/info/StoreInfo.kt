package com.song.deviceinfo.info

import android.content.Context
import com.song.deviceinfo.model.beans.StorageBean
import com.song.deviceinfo.utils.MemoryUtils.getMemoryInfo
import com.song.deviceinfo.utils.SdUtils.getStoreInfo

/**
 * Created by chensongsong on 2020/6/1.
 */
object StoreInfo {
    /**
     * 获取内存信息
     *
     * @param context
     * @return
     */
    fun getStoreInfo(context: Context): List<StorageBean> {
        val list: MutableList<StorageBean> = ArrayList()
        val bean = StorageBean()
        getStoreInfo(context, bean)
        getMemoryInfo(context, bean)
        list.add(bean)
        return list
    }
}
