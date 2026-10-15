package com.song.deviceinfo.ui.input

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.song.deviceinfo.R
import com.song.deviceinfo.databinding.ItemInputBinding
import com.song.deviceinfo.model.beans.InputBean
import com.song.deviceinfo.ui.base.BaseAdapter
import com.song.deviceinfo.ui.input.InputAdapter.InputHolder

/**
 * Created by chensongsong on 2020/6/4.
 */
class InputAdapter(context: Context) : BaseAdapter<InputBean, InputHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InputHolder {
        val binding = ItemInputBinding.inflate(LayoutInflater.from(context), parent, false)
        return InputHolder(binding)
    }
    
    override fun onBindViewHolder(holder: InputHolder, position: Int) {
        if (position % 2 == 0) {
            holder.binding.root.setBackgroundColor(context.resources.getColor(R.color.color_white))
        } else {
            holder.binding.root.setBackgroundColor(context.resources.getColor(R.color.color_E8E8E8))
        }
        val bean = items!![position]!!
        holder.binding.tvInputName.text = bean.name
        holder.binding.tvInputAttribute.text = bean.attribute
        if (TextUtils.isEmpty(bean.sys)) {
            holder.binding.tvInputSys.visibility = View.GONE
        } else {
            holder.binding.tvInputSys.visibility = View.VISIBLE
            holder.binding.tvInputSys.text = bean.sys
        }
        holder.binding.tvInputHandlers.text = bean.handlers
    }
    
    class InputHolder(var binding: ItemInputBinding) : RecyclerView.ViewHolder(binding.root)
}
