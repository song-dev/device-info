package com.song.deviceinfo.ui.codecs

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.song.deviceinfo.R
import com.song.deviceinfo.databinding.ItemCodecsBinding
import com.song.deviceinfo.ui.base.BaseAdapter

/**
 * Created by chensongsong on 2020/6/3.
 */
class CodecAdapter(context: Context) : BaseAdapter<Pair<String, String>, CodecAdapter.CodecHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodecHolder {
        return CodecHolder(ItemCodecsBinding.inflate(LayoutInflater.from(context), parent, false))
    }
    
    override fun onBindViewHolder(holder: CodecHolder, position: Int) {
        if (position % 2 == 0) {
            holder.binding.root.setBackgroundColor(context.resources.getColor(R.color.color_white))
        } else {
            holder.binding.root.setBackgroundColor(context.resources.getColor(R.color.color_E8E8E8))
        }
        val bean: Pair<String, String> = items[position]
        holder.binding.tvCodecsName.text = bean.first
        holder.binding.tvCodecsType.text = bean.second
    }
    
    class CodecHolder(var binding: ItemCodecsBinding) : RecyclerView.ViewHolder(binding.root)
}
