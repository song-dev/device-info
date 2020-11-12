package com.song.deviceinfo.ui.bluetooth;

import android.content.Context;

import com.song.deviceinfo.info.BluetoothInfo;
import com.song.deviceinfo.ui.base.NormalViewModel;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/9/22.
 */
public class BluetoothViewModel extends NormalViewModel {

    public List<Pair<String, String>> getBluetoothInfo(Context context) {
        return BluetoothInfo.getBluetoothInfo(context);
    }

}
