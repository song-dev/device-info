package com.song.deviceinfo.ui.maps

import androidx.lifecycle.ViewModelProvider
import com.song.deviceinfo.ui.base.BaseViewModel
import com.song.deviceinfo.ui.oneline.OneLineFragment
import com.song.deviceinfo.utils.LogUtils.printLongString
import org.json.JSONArray

/**
 * Created by chensongsong on 2021/9/10.
 */
class MapsFragment : OneLineFragment() {
    override fun createViewModel(): BaseViewModel<String> {
        return ViewModelProvider(this)[MapsViewModel::class.java]
    }
    
    override fun refreshData() {
        launchOnIO {
            val list = mapsInfo
            val jsonArray = JSONArray()
            for (item in list) {
                try {
                    jsonArray.put(item)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            printLongString(jsonArray.toString())
            launchOnMain { viewModel?.setItems(list) }
        }
    }
    
    private val mapsInfo: List<String>
        get() = (viewModel as MapsViewModel).getMapsInfo(context)
}
