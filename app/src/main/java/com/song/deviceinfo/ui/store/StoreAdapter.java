package com.song.deviceinfo.ui.store;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.song.deviceinfo.R;
import com.song.deviceinfo.model.beans.StorageBean;
import com.song.deviceinfo.ui.base.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

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
        View root = LayoutInflater.from(context).inflate(R.layout.item_store, parent, false);
        return new StoreHolder(root);
    }

    @SuppressLint({"StringFormatMatches", "StringFormatInvalid", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull StoreHolder holder, int position) {
        StorageBean bean = data.get(position);
        holder.freeMemTv.setText(context.getString(R.string.store_mem_free, bean.getFreeMemory()));
        holder.usedMemTv.setText(context.getString(R.string.store_mem_used, bean.getUsedMemory(), bean.getRatioMemory() + ""));
        holder.totalMemTv.setText(context.getString(R.string.store_mem_total, bean.getTotalMemory()));
        holder.progressBarMem.setProgress(bean.getRatioMemory());
        holder.infoMemTv.setText(bean.getMemInfo());

        holder.freeSdTv.setText(context.getString(R.string.store_sd_free, bean.getFreeStore()));
        holder.usedSdTv.setText(context.getString(R.string.store_sd_used, bean.getUsedStore(), bean.getRatioStore() + ""));
        holder.totalSdTv.setText(context.getString(R.string.store_sd_total, bean.getTotalStore()));
        holder.progressBarSd.setProgress(bean.getRatioStore());
        holder.infoSdTv.setText(bean.getRomSize() + " " + bean.getStorePath());
    }

    static class StoreHolder extends RecyclerView.ViewHolder {

        View root;
        @BindView(R.id.tv_store_mem_free)
        TextView freeMemTv;
        @BindView(R.id.tv_store_mem_used)
        TextView usedMemTv;
        @BindView(R.id.tv_store_mem_total)
        TextView totalMemTv;
        @BindView(R.id.progress_bar_store_mem)
        ProgressBar progressBarMem;
        @BindView(R.id.tv_store_mem_info)
        TextView infoMemTv;

        @BindView(R.id.tv_store_sd_free)
        TextView freeSdTv;
        @BindView(R.id.tv_store_sd_used)
        TextView usedSdTv;
        @BindView(R.id.tv_store_sd_total)
        TextView totalSdTv;
        @BindView(R.id.progress_bar_store_sd)
        ProgressBar progressBarSd;
        @BindView(R.id.tv_store_sd_path)
        TextView infoSdTv;

        public StoreHolder(View view) {
            super(view);
            root = view;
            ButterKnife.bind(this, root);
        }
    }
}
