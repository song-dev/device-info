package com.song.deviceinfo.ui.net;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.song.deviceinfo.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by chensongsong on 2020/5/25.
 */
public class NetAdapter extends RecyclerView.Adapter<NetAdapter.HomeHolder> {

    private List<Pair<String, String>> data = null;
    private Context context;

    public NetAdapter(Context context) {
        this.context = context;
    }

    public void updateData(List<Pair<String, String>> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_normal, parent, false);
        return new HomeHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int position) {
        if (position % 2 == 0) {
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.color_white));
        } else {
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.color_E8E8E8));
        }
        Pair<String, String> pair = data.get(position);
        String key = pair.first;
        String value = pair.second;
        holder.keyTv.setText(key);
        holder.valueTv.setText(value);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class HomeHolder extends RecyclerView.ViewHolder {

        View root;
        TextView keyTv;
        TextView valueTv;

        public HomeHolder(View view) {
            super(view);
            root = view;
            keyTv = view.findViewById(R.id.tv_key);
            valueTv = view.findViewById(R.id.tv_value);
        }

    }
}
