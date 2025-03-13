package com.song.deviceinfo.ui.codecs

import androidx.core.util.Pair
import androidx.lifecycle.ViewModelProviders
import com.song.deviceinfo.ui.base.BaseAdapter
import com.song.deviceinfo.ui.base.BaseFragment
import com.song.deviceinfo.ui.base.BaseViewModel
import com.song.deviceinfo.utils.LogUtils.printLongString
import com.song.deviceinfo.utils.ThreadPoolUtils
import org.json.JSONObject
import java.util.Objects

/**
 * Created by chensongsong on 2020/6/3.
 */
class CodecFragment : BaseFragment<Pair<String, String>>() {
    override fun createAdapter(): BaseAdapter<Pair<String, String>, *> {
        return CodecAdapter(requireContext())
    }
    
    override fun createViewModel(): BaseViewModel<Pair<String, String>> {
        return ViewModelProviders.of(this).get(CodecViewModel::class.java)
    }
    
    override fun refreshData() {
        ThreadPoolUtils.execute {
            val list = codeCInfo
            val jsonObject = JSONObject()
            for (pair in list) {
                try {
                    jsonObject.put(Objects.requireNonNull(pair!!.first), pair.second)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            printLongString(jsonObject.toString())
            handler.post {
                viewModel!!.setItems(list)
            }
        }
    }
    
    private val codeCInfo: List<Pair<String, String>>
        get() = (viewModel as CodecViewModel).codeCInfo
}
