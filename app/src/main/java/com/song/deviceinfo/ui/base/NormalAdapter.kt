package com.song.deviceinfo.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.song.deviceinfo.R
import com.song.deviceinfo.ui.base.NormalAdapter.NormalHolder

/**
 * Created by chensongsong on 2020/5/27.
 */
class NormalAdapter(context: Context) : BaseAdapter<Pair<String, String>, NormalHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NormalHolder {
        val root = LayoutInflater.from(context).inflate(R.layout.item_normal, parent, false)
        return NormalHolder(root)
    }
    
    override fun onBindViewHolder(holder: NormalHolder, position: Int) {
        if (position % 2 == 0) {
            holder.root.setBackgroundColor(context.getColor(R.color.color_white))
        } else {
            holder.root.setBackgroundColor(context.getColor(R.color.color_E8E8E8))
        }
        val pair = items[position]
        holder.keyTv.text = pair.first
        holder.valueTv.text = pair.second
    }
    
    class NormalHolder(var root: View) : RecyclerView.ViewHolder(root) {
        var keyTv: TextView = root.findViewById(R.id.tv_key)
        var valueTv: TextView = root.findViewById(R.id.tv_value)
    }
}
