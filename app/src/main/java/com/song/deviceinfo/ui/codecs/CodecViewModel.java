package com.song.deviceinfo.ui.codecs;

import com.song.deviceinfo.info.MediaCodecInfo;
import com.song.deviceinfo.ui.base.BaseViewModel;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/6/3.
 */
public class CodecViewModel extends BaseViewModel {

    protected List<Pair<String, String>> getCodeCInfo() {
        return MediaCodecInfo.getCodeCInfo();
    }

}
