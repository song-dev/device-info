package com.song.deviceinfo.ui.app

import androidx.core.util.Pair
import androidx.lifecycle.ViewModelProviders
import com.song.deviceinfo.ui.base.BaseAdapter
import com.song.deviceinfo.ui.base.BaseViewModel
import com.song.deviceinfo.ui.base.NormalAdapter
import com.song.deviceinfo.ui.base.NormalFragment

/**
 * Created by chensongsong on 2020/9/22.
 */
class AppFragment : NormalFragment() {
    
    override fun createAdapter(): BaseAdapter<Pair<String, String>, *>? {
        return NormalAdapter(requireContext())
    }
    
    override fun createViewModel(): BaseViewModel<Pair<String, String>>? {
        return ViewModelProviders.of(this).get(AppViewModel::class.java)
    }
    
    override val normalInfo: List<Pair<String, String>>
        get() = (viewModel as AppViewModel).getAppInfo(requireContext())
}
