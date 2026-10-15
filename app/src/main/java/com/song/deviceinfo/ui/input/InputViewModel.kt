package com.song.deviceinfo.ui.input

import com.song.deviceinfo.info.InputInfo
import com.song.deviceinfo.model.beans.InputBean
import com.song.deviceinfo.ui.base.BaseViewModel

/**
 * Created by chensongsong on 2020/6/4.
 */
class InputViewModel : BaseViewModel<InputBean>() {
    val inputInfo: List<InputBean>
        get() = InputInfo.inputInfo
}
