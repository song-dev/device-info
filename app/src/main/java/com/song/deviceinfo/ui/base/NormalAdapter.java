package com.song.deviceinfo.ui.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.song.deviceinfo.R;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by chensongsong on 2020/5/27.
 */
public class NormalAdapter extends BaseAdapter<Pair<String, String>, NormalAdapter.NormalHolder> {

    public NormalAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public NormalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_normal, parent, false);
        return new NormalHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull NormalHolder holder, int position) {
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

    static class NormalHolder extends RecyclerView.ViewHolder {

        View root;
        TextView keyTv;
        TextView valueTv;

        public NormalHolder(View view) {
            super(view);
            root = view;
            keyTv = view.findViewById(R.id.tv_key);
            valueTv = view.findViewById(R.id.tv_value);
        }
    }
}
