package com.song.deviceinfo.ui.wifi

import androidx.core.util.Pair
import androidx.lifecycle.ViewModelProviders
import com.song.deviceinfo.ui.base.BaseAdapter
import com.song.deviceinfo.ui.base.BaseViewModel
import com.song.deviceinfo.ui.base.NormalAdapter
import com.song.deviceinfo.ui.base.NormalFragment

/**
 * Created by chensongsong on 2020/9/22.
 */
class WifiFragment : NormalFragment() {
    override fun createAdapter(): BaseAdapter<Pair<String, String>, *> {
        return NormalAdapter(requireContext())
    }
    
    override fun createViewModel(): BaseViewModel<Pair<String, String>> {
        return ViewModelProviders.of(this).get(WifiViewModel::class.java)
    }
    
    override val normalInfo: List<Pair<String, String>>
        get() = (viewModel as WifiViewModel).getWifiInfo(requireContext())
}
