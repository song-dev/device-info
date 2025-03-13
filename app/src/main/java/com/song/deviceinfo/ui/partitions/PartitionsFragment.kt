package com.song.deviceinfo.ui.partitions

import androidx.lifecycle.ViewModelProviders
import com.song.deviceinfo.model.beans.PartitionsBean
import com.song.deviceinfo.ui.base.BaseAdapter
import com.song.deviceinfo.ui.base.BaseFragment
import com.song.deviceinfo.ui.base.BaseViewModel
import com.song.deviceinfo.utils.LogUtils.printLongString
import com.song.deviceinfo.utils.ThreadPoolUtils
import org.json.JSONArray

/**
 * Created by chensongsong on 2020/5/29.
 */
class PartitionsFragment : BaseFragment<PartitionsBean>() {
    override fun createAdapter(): BaseAdapter<PartitionsBean, *> {
        return PartitionsAdapter(requireContext())
    }
    
    override fun createViewModel(): BaseViewModel<PartitionsBean> {
        return ViewModelProviders.of(this).get(PartitionsViewModel::class.java)
    }
    
    override fun refreshData() {
        ThreadPoolUtils!!.execute {
            val list = partitionsInfo
            val jsonArray = JSONArray()
            for (bean in list) {
                try {
                    jsonArray.put(bean!!.toJSON())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            printLongString(jsonArray.toString())
            handler.post {
                viewModel!!.setItems(list)
            }
        }
    }
    
    private val partitionsInfo: List<PartitionsBean>
        get() = (viewModel as PartitionsViewModel).partitionsInfo
}
