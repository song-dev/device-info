package com.song.deviceinfo.ui.store;

import android.content.Context;

import com.song.deviceinfo.info.StoreInfo;
import com.song.deviceinfo.model.beans.StorageBean;
import com.song.deviceinfo.ui.base.BaseViewModel;

import java.util.List;

/**
 * Created by chensongsong on 2020/6/1.
 */
public class StoreViewModel extends BaseViewModel {

    protected List<StorageBean> getStoreInfo(Context context) {
        return StoreInfo.getStoreInfo(context);
    }

}
