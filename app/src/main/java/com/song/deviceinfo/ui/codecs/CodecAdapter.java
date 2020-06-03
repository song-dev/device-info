package com.song.deviceinfo.ui.codecs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.song.deviceinfo.R;
import com.song.deviceinfo.ui.base.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chensongsong on 2020/6/3.
 */
public class CodecAdapter extends BaseAdapter<Pair<String, String>, CodecAdapter.CodecHolder> {

    public CodecAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public CodecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_codecs, parent, false);
        return new CodecHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CodecHolder holder, int position) {
        if (position % 2 == 0) {
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.color_white));
        } else {
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.color_E8E8E8));
        }
        Pair<String, String> bean = data.get(position);
        holder.nameTv.setText(bean.first);
        holder.typeTv.setText(bean.second);
    }

    static class CodecHolder extends RecyclerView.ViewHolder {

        View root;
        @BindView(R.id.tv_codecs_name)
        TextView nameTv;
        @BindView(R.id.tv_codecs_type)
        TextView typeTv;

        public CodecHolder(View view) {
            super(view);
            root = view;
            ButterKnife.bind(this, root);
        }
    }
}
