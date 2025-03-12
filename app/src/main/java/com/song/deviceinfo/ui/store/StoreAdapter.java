package com.song.deviceinfo.ui.store;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.song.deviceinfo.R;
import com.song.deviceinfo.databinding.ItemStoreBinding;
import com.song.deviceinfo.model.beans.StorageBean;
import com.song.deviceinfo.ui.base.BaseAdapter;

/**
 * Created by chensongsong on 2020/6/1.
 */
public class StoreAdapter extends BaseAdapter<StorageBean, StoreAdapter.StoreHolder> {

    public StoreAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public StoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoreHolder(ItemStoreBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @SuppressLint({"StringFormatMatches", "StringFormatInvalid", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull StoreHolder holder, int position) {
        StorageBean bean = data.get(position);
        holder.binding.tvStoreMemFree.setText(context.getString(R.string.store_mem_free, bean.getFreeMemory()));
        holder.binding.tvStoreMemUsed.setText(context.getString(R.string.store_mem_used, bean.getUsedMemory(), bean.getRatioMemory() + ""));
        holder.binding.tvStoreMemTotal.setText(context.getString(R.string.store_mem_total, bean.getTotalMemory()));
        holder.binding.progressBarStoreMem.setProgress(bean.getRatioMemory());
        holder.binding.tvStoreMemInfo.setText(bean.getMemInfo());

        holder.binding.tvStoreSdFree.setText(context.getString(R.string.store_sd_free, bean.getFreeStore()));
        holder.binding.tvStoreSdUsed.setText(context.getString(R.string.store_sd_used, bean.getUsedStore(), bean.getRatioStore() + ""));
        holder.binding.tvStoreSdTotal.setText(context.getString(R.string.store_sd_total, bean.getTotalStore()));
        holder.binding.progressBarStoreSd.setProgress(bean.getRatioStore());
        holder.binding.tvStoreSdPath.setText(bean.getRomSize() + " " + bean.getStorePath());
    }

    static class StoreHolder extends RecyclerView.ViewHolder {

        ItemStoreBinding binding;

        public StoreHolder(ItemStoreBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
