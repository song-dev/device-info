package com.song.deviceinfo.ui.partitions

import com.song.deviceinfo.info.PartitionsInfo
import com.song.deviceinfo.model.beans.PartitionsBean
import com.song.deviceinfo.ui.base.BaseViewModel

/**
 * Created by chensongsong on 2020/5/29.
 */
class PartitionsViewModel : BaseViewModel<PartitionsBean>() {
    val partitionsInfo: List<PartitionsBean>
        get() = PartitionsInfo.partitionsInfo
}
