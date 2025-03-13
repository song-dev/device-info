package com.song.deviceinfo.ui.base

import android.os.Bundle
import androidx.core.util.Pair
import android.view.View
import com.song.deviceinfo.utils.LogUtils
import com.song.deviceinfo.utils.ThreadPoolUtils
import org.json.JSONObject

/**
 * Created by chensongsong on 2020/5/27.
 */
abstract class NormalFragment : BaseFragment<Pair<String, String>>() {

    protected abstract val normalInfo: List<Pair<String, String>>

    override fun refreshData() {
        ThreadPoolUtils.execute {
            val data = normalInfo
            val json = JSONObject()
            data.forEach { pair ->
                json.put(pair.first, pair.second)
            }
            LogUtils.d("NormalFragment", json.toString())
            handler.post {
                viewModel?.setItems(data)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshData()
    }
}
