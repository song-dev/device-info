package com.song.deviceinfo.ui.partitions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.song.deviceinfo.R;
import com.song.deviceinfo.model.beans.PartitionsBean;
import com.song.deviceinfo.ui.base.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

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
        View root = LayoutInflater.from(context).inflate(R.layout.item_partitions, parent, false);
        return new PartitionsHolder(root);
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(@NonNull PartitionsHolder holder, int position) {
        if (position % 2 == 0) {
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.color_white));
        } else {
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.color_E8E8E8));
        }
        PartitionsBean bean = data.get(position);
        holder.pathTv.setText(bean.getPath());
        holder.mountTv.setText(bean.getMount());
        holder.fsTv.setText(String.format(context.getString(R.string.partitions_fs_mod, bean.getFs(), bean.getMod())));
        if (TextUtils.isEmpty(bean.getSize())) {
            holder.usedTv.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.GONE);
        } else {
            holder.usedTv.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.usedTv.setText(String.format(context.getString(R.string.partitions_used, bean.getUsed(), bean.getSize())));
            holder.progressBar.setProgress(bean.getRatio());
        }
    }

    static class PartitionsHolder extends RecyclerView.ViewHolder {

        View root;
        @BindView(R.id.tv_partitions_path)
        TextView pathTv;
        @BindView(R.id.tv_partitions_mount)
        TextView mountTv;
        @BindView(R.id.tv_partitions_fs)
        TextView fsTv;
        @BindView(R.id.progress_bar_partitions_ratio)
        ProgressBar progressBar;
        @BindView(R.id.tv_partitions_used)
        TextView usedTv;

        public PartitionsHolder(View view) {
            super(view);
            root = view;
            ButterKnife.bind(this, root);
        }
    }
}
