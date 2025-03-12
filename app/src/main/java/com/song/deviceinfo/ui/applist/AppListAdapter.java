package com.song.deviceinfo.ui.applist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.song.deviceinfo.R;
import com.song.deviceinfo.databinding.ItemApplistBinding;
import com.song.deviceinfo.model.beans.ApplicationBean;
import com.song.deviceinfo.ui.base.BaseAdapter;

/**
 * Created by chensongsong on 2020/6/3.
 */
public class AppListAdapter extends BaseAdapter<ApplicationBean, AppListAdapter.AppListHolder> {

    public AppListAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public AppListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppListHolder(ItemApplistBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull AppListHolder holder, int position) {
        if (position % 2 == 0) {
            holder.binding.getRoot().setBackgroundColor(context.getResources().getColor(R.color.color_white));
        } else {
            holder.binding.getRoot().setBackgroundColor(context.getResources().getColor(R.color.color_E8E8E8));
        }
        ApplicationBean bean = data.get(position);
        holder.binding.ivApplistIcon.setImageDrawable(bean.getIcon());
        holder.binding.tvApplistName.setText(bean.getName());
        holder.binding.tvApplistPackagename.setText(bean.getPackageName());
        holder.binding.tvApplistVersion.setText(bean.getVersion() + " sdk" + bean.getBuildVersion());
        holder.binding.getRoot().setOnClickListener((view) -> {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", bean.getPackageName(), null));
            context.startActivity(intent);
        });
    }

    static class AppListHolder extends RecyclerView.ViewHolder {
        ItemApplistBinding binding;

        public AppListHolder(ItemApplistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
