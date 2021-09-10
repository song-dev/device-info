package com.song.deviceinfo.ui.oneline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.song.deviceinfo.R;
import com.song.deviceinfo.ui.base.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chensongsong on 2021/9/10.
 */
public class OneLineAdapter extends BaseAdapter<String, OneLineAdapter.CodecHolder> {

    public OneLineAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public CodecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_one_line, parent, false);
        return new CodecHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CodecHolder holder, int position) {
        if (position % 2 == 0) {
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.color_white));
        } else {
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.color_E8E8E8));
        }
        String bean = data.get(position);
        holder.nameTv.setText(bean);
    }

    static class CodecHolder extends RecyclerView.ViewHolder {

        View root;
        @BindView(R.id.tv_one_line)
        TextView nameTv;

        public CodecHolder(View view) {
            super(view);
            root = view;
            ButterKnife.bind(this, root);
        }
    }
}
