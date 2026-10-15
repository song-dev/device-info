package com.song.deviceinfo.ui.partitions

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.song.deviceinfo.R
import com.song.deviceinfo.databinding.ItemPartitionsBinding
import com.song.deviceinfo.model.beans.PartitionsBean
import com.song.deviceinfo.ui.base.BaseAdapter
import com.song.deviceinfo.ui.partitions.PartitionsAdapter.PartitionsHolder

/**
 * Created by chensongsong on 2020/5/29.
 */
class PartitionsAdapter(context: Context) : BaseAdapter<PartitionsBean, PartitionsHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartitionsHolder {
        return PartitionsHolder(ItemPartitionsBinding.inflate(LayoutInflater.from(context), parent, false))
    }
    
    @SuppressLint("StringFormatMatches")
    override fun onBindViewHolder(holder: PartitionsHolder, position: Int) {
        if (position % 2 == 0) {
            holder.binding.root.setBackgroundColor(context.resources.getColor(R.color.color_white))
        } else {
            holder.binding.root.setBackgroundColor(context.resources.getColor(R.color.color_E8E8E8))
        }
        val bean = items!![position]!!
        holder.binding.tvPartitionsPath.text = bean.path
        holder.binding.tvPartitionsMount.text = bean.mount
        holder.binding.tvPartitionsFs.text = String.format(context.getString(R.string.partitions_fs_mod, bean.fs, bean.mod))
        if (TextUtils.isEmpty(bean.size)) {
            holder.binding.tvPartitionsUsed.visibility = View.GONE
            holder.binding.progressBarPartitionsRatio.visibility = View.GONE
        } else {
            holder.binding.tvPartitionsUsed.visibility = View.VISIBLE
            holder.binding.progressBarPartitionsRatio.visibility = View.VISIBLE
            holder.binding.tvPartitionsUsed.text = String.format(context.getString(R.string.partitions_used, bean.used, bean.size))
            holder.binding.progressBarPartitionsRatio.progress = bean.ratio
        }
    }
    
    class PartitionsHolder(var binding: ItemPartitionsBinding) : RecyclerView.ViewHolder(binding.root)
}
