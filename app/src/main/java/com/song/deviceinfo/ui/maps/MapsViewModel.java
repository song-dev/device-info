package com.song.deviceinfo.ui.maps;

import android.content.Context;

import com.song.deviceinfo.info.MapsInfo;
import com.song.deviceinfo.ui.base.BaseViewModel;

import java.util.List;

/**
 * Created by chensongsong on 2021/9/10.
 */
public class MapsViewModel extends BaseViewModel {

    protected List<String> getMapsInfo(Context context){
        return MapsInfo.getMapsInfo(context);
    }

}
