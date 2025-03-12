package com.song.deviceinfo.ui.codecs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.song.deviceinfo.R;
import com.song.deviceinfo.databinding.ItemCodecsBinding;
import com.song.deviceinfo.ui.base.BaseAdapter;

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
        return new CodecHolder(ItemCodecsBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CodecHolder holder, int position) {
        if (position % 2 == 0) {
            holder.binding.getRoot().setBackgroundColor(context.getResources().getColor(R.color.color_white));
        } else {
            holder.binding.getRoot().setBackgroundColor(context.getResources().getColor(R.color.color_E8E8E8));
        }
        Pair<String, String> bean = data.get(position);
        holder.binding.tvCodecsName.setText(bean.first);
        holder.binding.tvCodecsType.setText(bean.second);
    }

    static class CodecHolder extends RecyclerView.ViewHolder {

        ItemCodecsBinding binding;

        public CodecHolder(ItemCodecsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
