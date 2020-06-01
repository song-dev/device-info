package com.song.deviceinfo.info;

import android.content.Context;

import com.song.deviceinfo.model.beans.StorageBean;
import com.song.deviceinfo.utils.SdUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chensongsong on 2020/6/1.
 */
public class StoreInfo {

    /**
     * 获取内存信息
     *
     * @param context
     * @return
     */
    public static List<StorageBean> getStoreInfo(Context context) {

        List<StorageBean> list = new ArrayList<>();
        StorageBean bean = new StorageBean();
        SdUtils.getStoreInfo(context, bean);
        bean.setMemInfo("4 GB");
        bean.setFreeMemory("10");
        bean.setUsedMemory("10");
        bean.setTotalStore("10");
        bean.setRatioMemory(50);
        list.add(bean);
        return list;

    }

}
