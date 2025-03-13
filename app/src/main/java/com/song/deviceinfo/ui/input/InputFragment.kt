package com.song.deviceinfo.ui.input

import androidx.lifecycle.ViewModelProviders
import com.song.deviceinfo.model.beans.InputBean
import com.song.deviceinfo.ui.base.BaseAdapter
import com.song.deviceinfo.ui.base.BaseFragment
import com.song.deviceinfo.ui.base.BaseViewModel
import com.song.deviceinfo.utils.LogUtils.printLongString
import com.song.deviceinfo.utils.ThreadPoolUtils
import org.json.JSONArray

/**
 * Created by chensongsong on 2020/6/4.
 */
class InputFragment : BaseFragment<InputBean>() {
    override fun createAdapter(): BaseAdapter<InputBean, *> {
        return InputAdapter(requireContext())
    }
    
    override fun createViewModel(): BaseViewModel<InputBean> {
        return ViewModelProviders.of(this).get(InputViewModel::class.java)
    }
    
    override fun refreshData() {
        ThreadPoolUtils.execute {
            val list = inputInfo
            val jsonArray = JSONArray()
            for (bean in list!!) {
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
    
    private val inputInfo: List<InputBean>
        get() = (viewModel as InputViewModel).inputInfo
}
