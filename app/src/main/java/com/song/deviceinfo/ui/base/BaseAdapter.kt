package com.song.deviceinfo.ui.base

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by chensongsong on 2020/5/25.
 */
abstract class BaseAdapter<T : Any, VH : RecyclerView.ViewHolder>(
    protected val context: Context
) : RecyclerView.Adapter<VH>() {
    
    protected val items = mutableListOf<T>()
    
    fun setData(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
    
    override fun getItemCount(): Int = items.size
}
