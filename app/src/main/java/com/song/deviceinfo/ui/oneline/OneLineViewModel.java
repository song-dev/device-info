package com.song.deviceinfo.ui.oneline;

import com.song.deviceinfo.ui.base.BaseViewModel;

import java.util.List;

/**
 * Created by chensongsong on 2021/9/10.
 */
public abstract class OneLineViewModel extends BaseViewModel {

    protected abstract List<String> getOneLineInfo();

}
