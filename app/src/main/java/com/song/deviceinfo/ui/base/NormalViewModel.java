package com.song.deviceinfo.ui.base;

import java.util.List;

import androidx.core.util.Pair;

public abstract class NormalViewModel<P> extends BaseViewModel<Pair<String, String>> {

    public abstract List<Pair<String, String>> getNormalInfo();

}
