package com.song.deviceinfo.ui.oneline

import com.song.deviceinfo.ui.base.BaseViewModel

/**
 * Created by chensongsong on 2021/9/10.
 */
abstract class OneLineViewModel : BaseViewModel<String>() {
    abstract val oneLineInfo: List<String>
}
