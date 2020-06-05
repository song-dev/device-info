package com.song.deviceinfo.ui.input;

import com.song.deviceinfo.info.InputInfo;
import com.song.deviceinfo.model.beans.InputBean;
import com.song.deviceinfo.ui.base.BaseViewModel;

import java.util.List;

/**
 * Created by chensongsong on 2020/6/4.
 */
public class InputViewModel extends BaseViewModel {

    protected List<InputBean> getInputInfo() {
        return InputInfo.getInputInfo();
    }

}
