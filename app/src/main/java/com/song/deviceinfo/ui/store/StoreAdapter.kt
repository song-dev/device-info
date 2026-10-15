package com.song.deviceinfo.ui.store

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.song.deviceinfo.R
import com.song.deviceinfo.databinding.ItemStoreBinding
import com.song.deviceinfo.model.beans.StorageBean
import com.song.deviceinfo.ui.base.BaseAdapter
import com.song.deviceinfo.ui.store.StoreAdapter.StoreHolder

/**
 * Created by chensongsong on 2020/6/1.
 */
class StoreAdapter(context: Context) : BaseAdapter<StorageBean, StoreHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreHolder {
        return StoreHolder(ItemStoreBinding.inflate(LayoutInflater.from(context), parent, false))
    }
    
    @SuppressLint("StringFormatMatches", "StringFormatInvalid", "SetTextI18n")
    override fun onBindViewHolder(holder: StoreHolder, position: Int) {
        val bean = items[position]
        holder.binding.tvStoreMemFree.text = context.getString(R.string.store_mem_free, bean.freeMemory)
        holder.binding.tvStoreMemUsed.text = context.getString(R.string.store_mem_used, bean.usedMemory, bean.ratioMemory.toString() + "")
        holder.binding.tvStoreMemTotal.text = context.getString(R.string.store_mem_total, bean.totalMemory)
        holder.binding.progressBarStoreMem.progress = bean.ratioMemory
        holder.binding.tvStoreMemInfo.text = bean.memInfo
        
        holder.binding.tvStoreSdFree.text = context.getString(R.string.store_sd_free, bean.freeStore)
        holder.binding.tvStoreSdUsed.text = context.getString(R.string.store_sd_used, bean.usedStore, bean.ratioStore.toString() + "")
        holder.binding.tvStoreSdTotal.text = context.getString(R.string.store_sd_total, bean.totalStore)
        holder.binding.progressBarStoreSd.progress = bean.ratioStore
        holder.binding.tvStoreSdPath.text = bean.romSize + " " + bean.storePath
    }
    
    class StoreHolder(var binding: ItemStoreBinding) : RecyclerView.ViewHolder(binding.root)
}
