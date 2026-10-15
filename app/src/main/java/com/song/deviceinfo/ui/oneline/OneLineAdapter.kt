package com.song.deviceinfo.ui.oneline

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.song.deviceinfo.R
import com.song.deviceinfo.databinding.ItemOneLineBinding
import com.song.deviceinfo.ui.base.BaseAdapter

/**
 * Created by chensongsong on 2021/9/10.
 */
class OneLineAdapter(context: Context) : BaseAdapter<String, OneLineAdapter.CodecHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodecHolder {
        return CodecHolder(ItemOneLineBinding.inflate(LayoutInflater.from(context), parent, false))
    }
    
    override fun onBindViewHolder(holder: CodecHolder, position: Int) {
        if (position % 2 == 0) {
            holder.binding.root.setBackgroundColor(context.resources.getColor(R.color.color_white))
        } else {
            holder.binding.root.setBackgroundColor(context.resources.getColor(R.color.color_E8E8E8))
        }
        val bean = items!![position]!!
        holder.binding.tvOneLine.text = bean
    }
    
    class CodecHolder(var binding: ItemOneLineBinding) : RecyclerView.ViewHolder(binding.root)
}
