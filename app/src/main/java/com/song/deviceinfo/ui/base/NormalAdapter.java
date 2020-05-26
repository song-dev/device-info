package com.song.deviceinfo.ui.base;

import android.content.Context;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by chensongsong on 2020/5/25.
 */
public abstract class NormalAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<T> data = null;
    private Context context;

    public NormalAdapter(Context context) {
        this.context = context;
    }

    public void updateData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

}
