package com.song.deviceinfo.ui.bluetooth

import android.content.Context
import androidx.core.util.Pair
import com.song.deviceinfo.info.BluetoothInfo
import com.song.deviceinfo.ui.base.NormalViewModel

/**
 * Created by chensongsong on 2020/9/22.
 */
class BluetoothViewModel : NormalViewModel() {
    fun getBluetoothInfo(context: Context): List<Pair<String, String>> {
        return BluetoothInfo.getBluetoothInfo(context)
    }
}
