package com.song.deviceinfo.ui.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.deviceinfo.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by chensongsong on 2020/5/26.
 */
public abstract class NormalFragment<T> extends Fragment {

    protected NormalViewModel viewModel;
    private NormalAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    protected static Handler mainHandler = new Handler(Looper.getMainLooper());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                ViewModelProviders.of(this).get(NormalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_normal, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        swipeRefreshLayout = ((SwipeRefreshLayout) root.findViewById(R.id.srl));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = crateAdapter();
        recyclerView.setAdapter(adapter);
        viewModel.getRecyclerView().observe(getViewLifecycleOwner(), new Observer<List<T>>() {
            @Override
            public void onChanged(List<T> pairs) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        adapter.updateData((List) viewModel.getRecyclerView().getValue());
                    }
                });
            }
        });
        setSwipeRefreshLayout();
        return root;
    }

    private void setSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                refreshData();
            }
        });
    }

    protected abstract NormalAdapter crateAdapter();

    protected abstract void refreshData();

}
