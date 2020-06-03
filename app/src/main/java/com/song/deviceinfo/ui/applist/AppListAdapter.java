package com.song.deviceinfo.ui.applist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.song.deviceinfo.R;
import com.song.deviceinfo.model.beans.ApplicationBean;
import com.song.deviceinfo.ui.base.BaseAdapter;
import com.song.deviceinfo.utils.LogUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

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
        View root = LayoutInflater.from(context).inflate(R.layout.item_applist, parent, false);
        return new AppListHolder(root);
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull AppListHolder holder, int position) {
        if (position % 2 == 0) {
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.color_white));
        } else {
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.color_E8E8E8));
        }
        ApplicationBean bean = data.get(position);
        LogUtils.d(bean.toString());
        holder.icon.setImageDrawable(bean.getIcon());
        holder.nameTv.setText(bean.getName());
        holder.packageNameTv.setText(bean.getPackageName());
        holder.versionTv.setText(bean.getVersion() + " sdk" + bean.getBuildVersion());
        holder.root.setOnClickListener((view) -> {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", bean.getPackageName(), null));
            context.startActivity(intent);
        });
    }

    static class AppListHolder extends RecyclerView.ViewHolder {

        View root;
        @BindView(R.id.tv_applist_name)
        TextView nameTv;
        @BindView(R.id.tv_applist_packagename)
        TextView packageNameTv;
        @BindView(R.id.tv_applist_version)
        TextView versionTv;
        @BindView(R.id.iv_applist_icon)
        ImageView icon;

        public AppListHolder(View view) {
            super(view);
            root = view;
            ButterKnife.bind(this, root);
        }
    }
}
