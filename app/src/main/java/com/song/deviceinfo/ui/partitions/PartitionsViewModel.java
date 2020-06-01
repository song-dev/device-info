package com.song.deviceinfo.ui.partitions;

import com.song.deviceinfo.info.PartitionsInfo;
import com.song.deviceinfo.model.beans.PartitionsBean;
import com.song.deviceinfo.ui.base.BaseViewModel;

import java.util.List;

/**
 * Created by chensongsong on 2020/5/29.
 */
public class PartitionsViewModel extends BaseViewModel {

    protected List<PartitionsBean> getPartitionsInfo(){
        return PartitionsInfo.getPartitionsInfo();
    }

}
