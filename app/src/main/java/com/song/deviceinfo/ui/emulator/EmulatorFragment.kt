package com.song.deviceinfo.ui.emulator

import androidx.core.util.Pair
import androidx.lifecycle.ViewModelProviders
import com.song.deviceinfo.ui.base.BaseAdapter
import com.song.deviceinfo.ui.base.BaseViewModel
import com.song.deviceinfo.ui.base.NormalAdapter
import com.song.deviceinfo.ui.base.NormalFragment

/**
 * Created by chensongsong on 2020/7/9.
 */
class EmulatorFragment : NormalFragment() {
    override fun createAdapter(): BaseAdapter<Pair<String, String>, *> {
        return NormalAdapter(requireContext())
    }
    
    override fun createViewModel(): BaseViewModel<Pair<String, String>> {
        return ViewModelProviders.of(this).get(EmulatorViewModel::class.java)
    }
    
    override val normalInfo: List<Pair<String, String>>
        get() = (viewModel as EmulatorViewModel).getEmulatorInfo(context)
}
