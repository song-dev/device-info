package com.song.deviceinfo.ui.camera;

import android.content.Context;

import com.song.deviceinfo.info.CameraInfo;
import com.song.deviceinfo.ui.base.NormalViewModel;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/9/30.
 */
public class CameraViewModel extends NormalViewModel {

    public List<Pair<String, String>> getCameraInfo(Context context) {
        return CameraInfo.getCameraInfo(context);
    }

}
