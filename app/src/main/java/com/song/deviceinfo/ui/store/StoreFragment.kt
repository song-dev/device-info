package com.song.deviceinfo.ui.store

import androidx.lifecycle.ViewModelProviders
import com.song.deviceinfo.model.beans.StorageBean
import com.song.deviceinfo.ui.base.BaseAdapter
import com.song.deviceinfo.ui.base.BaseFragment
import com.song.deviceinfo.ui.base.BaseViewModel
import com.song.deviceinfo.utils.LogUtils.release
import com.song.deviceinfo.utils.ThreadPoolUtils
import org.json.JSONArray

/**
 * Created by chensongsong on 2020/6/1.
 */
class StoreFragment : BaseFragment<StorageBean>() {
    override fun createAdapter(): BaseAdapter<StorageBean, *> {
        return StoreAdapter(requireContext())
    }
    
    override fun createViewModel(): BaseViewModel<StorageBean> {
        return ViewModelProviders.of(this).get(StoreViewModel::class.java)
    }
    
    override fun refreshData() {
        ThreadPoolUtils!!.execute {
            val list = storeInfo
            val jsonArray = JSONArray()
            for (bean in list) {
                try {
                    jsonArray.put(bean!!.toJSON())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            release(jsonArray.toString())
            handler.post {
                viewModel!!.setItems(list)
            }
        }
    }
    
    private val storeInfo: List<StorageBean>
        get() = (viewModel as StoreViewModel).getStoreInfo(requireContext())
}
