package com.song.deviceinfo.ui.input;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.song.deviceinfo.R;
import com.song.deviceinfo.databinding.ItemInputBinding;
import com.song.deviceinfo.model.beans.InputBean;
import com.song.deviceinfo.ui.base.BaseAdapter;

/**
 * Created by chensongsong on 2020/6/4.
 */
public class InputAdapter extends BaseAdapter<InputBean, InputAdapter.InputHolder> {

    public InputAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public InputHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInputBinding binding = ItemInputBinding.inflate(LayoutInflater.from(context), parent, false);
        return new InputHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InputHolder holder, int position) {
        if (position % 2 == 0) {
            holder.binding.getRoot().setBackgroundColor(context.getResources().getColor(R.color.color_white));
        } else {
            holder.binding.getRoot().setBackgroundColor(context.getResources().getColor(R.color.color_E8E8E8));
        }
        InputBean bean = data.get(position);
        holder.binding.tvInputName.setText(bean.getName());
        holder.binding.tvInputAttribute.setText(bean.getAttribute());
        if (TextUtils.isEmpty(bean.getSys())) {
            holder.binding.tvInputSys.setVisibility(View.GONE);
        } else {
            holder.binding.tvInputSys.setVisibility(View.VISIBLE);
            holder.binding.tvInputSys.setText(bean.getSys());
        }
        holder.binding.tvInputHandlers.setText(bean.getHandlers());
    }

    static class InputHolder extends RecyclerView.ViewHolder {
        ItemInputBinding binding;

        public InputHolder(ItemInputBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
