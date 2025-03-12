package com.song.deviceinfo.ui.oneline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.song.deviceinfo.R;
import com.song.deviceinfo.databinding.ItemOneLineBinding;
import com.song.deviceinfo.ui.base.BaseAdapter;

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
        return new CodecHolder(ItemOneLineBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CodecHolder holder, int position) {
        if (position % 2 == 0) {
            holder.binding.getRoot().setBackgroundColor(context.getResources().getColor(R.color.color_white));
        } else {
            holder.binding.getRoot().setBackgroundColor(context.getResources().getColor(R.color.color_E8E8E8));
        }
        String bean = data.get(position);
        holder.binding.tvOneLine.setText(bean);
    }

    static class CodecHolder extends RecyclerView.ViewHolder {

        ItemOneLineBinding binding;

        public CodecHolder(ItemOneLineBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
