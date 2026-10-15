package com.song.deviceinfo.ui.virtual

import androidx.core.util.Pair
import androidx.lifecycle.ViewModelProvider
import com.song.deviceinfo.ui.base.BaseAdapter
import com.song.deviceinfo.ui.base.BaseViewModel
import com.song.deviceinfo.ui.base.NormalAdapter
import com.song.deviceinfo.ui.base.NormalFragment

/**
 * Created by chensongsong on 2020/7/10.
 */
class VirtualAppFragment : NormalFragment() {
    override fun createAdapter(): BaseAdapter<Pair<String, String>, *> {
        return NormalAdapter(requireContext())
    }
    
    override fun createViewModel(): BaseViewModel<Pair<String, String>> {
        return ViewModelProvider(this)[VirtualAppViewModel::class.java]
    }
    
    override val normalInfo: List<Pair<String, String>>
        get() = (viewModel as VirtualAppViewModel).getVirtualAppInfo(requireContext())
}
