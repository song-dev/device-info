package com.song.deviceinfo.ui.oneline

import androidx.lifecycle.ViewModelProviders
import com.song.deviceinfo.ui.base.BaseAdapter
import com.song.deviceinfo.ui.base.BaseFragment
import com.song.deviceinfo.ui.base.BaseViewModel
import com.song.deviceinfo.utils.LogUtils.printLongString
import org.json.JSONArray

/**
 * Created by chensongsong on 2021/9/10.
 */
open class OneLineFragment : BaseFragment<String>() {
    override fun createAdapter(): BaseAdapter<String, *> {
        return OneLineAdapter(requireContext())
    }
    
    override fun createViewModel(): BaseViewModel<String> {
        return ViewModelProviders.of(this).get(OneLineViewModel::class.java)
    }
    
    override fun refreshData() {
        launchOnIO {
            val list = oneLineInfo
            val jsonArray = JSONArray()
            for (item in list!!) {
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
    
    private val oneLineInfo: List<String>
        get() = (viewModel as OneLineViewModel).oneLineInfo
}
