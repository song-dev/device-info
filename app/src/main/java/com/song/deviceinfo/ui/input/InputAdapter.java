package com.song.deviceinfo.ui.input;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.song.deviceinfo.R;
import com.song.deviceinfo.model.beans.InputBean;
import com.song.deviceinfo.ui.base.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

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
        View root = LayoutInflater.from(context).inflate(R.layout.item_input, parent, false);
        return new InputHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull InputHolder holder, int position) {
        if (position % 2 == 0) {
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.color_white));
        } else {
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.color_E8E8E8));
        }
        InputBean bean = data.get(position);
        holder.nameTv.setText(bean.getName());
        holder.attributeTv.setText(bean.getAttribute());
        if (TextUtils.isEmpty(bean.getSys())) {
            holder.sysTv.setVisibility(View.GONE);
        } else {
            holder.sysTv.setVisibility(View.VISIBLE);
            holder.sysTv.setText(bean.getSys());
        }
        holder.handlersTv.setText(bean.getHandlers());
    }

    static class InputHolder extends RecyclerView.ViewHolder {

        View root;
        @BindView(R.id.tv_input_name)
        TextView nameTv;
        @BindView(R.id.tv_input_attribute)
        TextView attributeTv;
        @BindView(R.id.tv_input_sys)
        TextView sysTv;
        @BindView(R.id.tv_input_handlers)
        TextView handlersTv;

        public InputHolder(View view) {
            super(view);
            root = view;
            ButterKnife.bind(this, root);
        }
    }
}
