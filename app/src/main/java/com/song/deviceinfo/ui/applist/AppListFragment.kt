package com.song.deviceinfo.ui.applist

import androidx.lifecycle.ViewModelProviders
import com.song.deviceinfo.model.beans.ApplicationBean
import com.song.deviceinfo.ui.base.BaseAdapter
import com.song.deviceinfo.ui.base.BaseFragment
import com.song.deviceinfo.ui.base.BaseViewModel
import com.song.deviceinfo.utils.LogUtils.printLongString
import com.song.deviceinfo.utils.ThreadPoolUtils
import org.json.JSONArray

/**
 * Created by chensongsong on 2020/6/3.
 */
class AppListFragment : BaseFragment<ApplicationBean>() {
    override fun createAdapter(): BaseAdapter<ApplicationBean, *> {
        return AppListAdapter(requireContext())
    }
    
    override fun createViewModel(): BaseViewModel<ApplicationBean> {
        return ViewModelProviders.of(this).get(AppListViewModel::class.java)
    }
    
    override fun refreshData() {
        ThreadPoolUtils.execute {
            val list = appListInfo
            val jsonArray = JSONArray()
            for (bean in list) {
                try {
                    jsonArray.put(bean.toJSON())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            printLongString(jsonArray.toString())
            handler.post {
                viewModel?.setItems(list)
            }
        }
    }
    
    private val appListInfo: List<ApplicationBean>
        get() = (viewModel as AppListViewModel).getAppListInfo(requireContext())
}
