package com.song.deviceinfo.info;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;

import com.google.firebase.perf.metrics.AddTrace;
import com.song.deviceinfo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/9/22.
 */
public class BluetoothInfo {

    @AddTrace(name = "BluetoothInfo.getBluetoothInfo")
    public static List<Pair<String, String>> getBluetoothInfo(Context context) {
        List<Pair<String, String>> list = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            BluetoothManager manager = ((BluetoothManager) context.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE));
            if (manager != null) {
                // 仅依赖 BLUETOOTH、ACCESS_WIFI_STATE 权限
                Set<BluetoothDevice> devices = manager.getAdapter().getBondedDevices();
                for (BluetoothDevice device : devices) {
                    list.add(new Pair<>("Bluetooth Name", device.getName()));
                    list.add(new Pair<>("Bluetooth Mac", device.getAddress()));
                    list.add(new Pair<>("Type", device.getType() + ""));
                    list.add(new Pair<>("State", device.getBondState() + ""));
                    list.add(new Pair<>("", ""));
                }
            }
        }
        if (list.isEmpty()) {
            list.add(new Pair<>("Bluetooth Scan", context.getString(R.string.usb_not_found)));
        }
        return list;
    }

}
