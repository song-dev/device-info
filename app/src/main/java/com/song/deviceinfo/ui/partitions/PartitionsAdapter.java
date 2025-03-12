package com.song.deviceinfo.ui.partitions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.song.deviceinfo.R;
import com.song.deviceinfo.databinding.ItemPartitionsBinding;
import com.song.deviceinfo.model.beans.PartitionsBean;
import com.song.deviceinfo.ui.base.BaseAdapter;

/**
 * Created by chensongsong on 2020/5/29.
 */
public class PartitionsAdapter extends BaseAdapter<PartitionsBean, PartitionsAdapter.PartitionsHolder> {

    public PartitionsAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public PartitionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PartitionsHolder(ItemPartitionsBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(@NonNull PartitionsHolder holder, int position) {
        if (position % 2 == 0) {
            holder.binding.getRoot().setBackgroundColor(context.getResources().getColor(R.color.color_white));
        } else {
            holder.binding.getRoot().setBackgroundColor(context.getResources().getColor(R.color.color_E8E8E8));
        }
        PartitionsBean bean = data.get(position);
        holder.binding.tvPartitionsPath.setText(bean.getPath());
        holder.binding.tvPartitionsMount.setText(bean.getMount());
        holder.binding.tvPartitionsFs.setText(String.format(context.getString(R.string.partitions_fs_mod, bean.getFs(), bean.getMod())));
        if (TextUtils.isEmpty(bean.getSize())) {
            holder.binding.tvPartitionsUsed.setVisibility(View.GONE);
            holder.binding.progressBarPartitionsRatio.setVisibility(View.GONE);
        } else {
            holder.binding.tvPartitionsUsed.setVisibility(View.VISIBLE);
            holder.binding.progressBarPartitionsRatio.setVisibility(View.VISIBLE);
            holder.binding.tvPartitionsUsed.setText(String.format(context.getString(R.string.partitions_used, bean.getUsed(), bean.getSize())));
            holder.binding.progressBarPartitionsRatio.setProgress(bean.getRatio());
        }
    }

    static class PartitionsHolder extends RecyclerView.ViewHolder {

        ItemPartitionsBinding binding;

        public PartitionsHolder(ItemPartitionsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
