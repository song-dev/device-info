package com.song.deviceinfo.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.deviceinfo.R;
import com.song.deviceinfo.utils.ThreadPoolUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by chensongsong on 2020/5/25.
 */
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private HomeAdapter homeAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static Handler mainHandler = new Handler(Looper.getMainLooper());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        swipeRefreshLayout = ((SwipeRefreshLayout) root.findViewById(R.id.srl));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        homeAdapter = new HomeAdapter(getContext());
        recyclerView.setAdapter(homeAdapter);
        homeViewModel.getRecyclerView().observe(getViewLifecycleOwner(), new Observer<List<Pair<String, String>>>() {
            @Override
            public void onChanged(List<Pair<String, String>> pairs) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        homeAdapter.updateData(homeViewModel.getRecyclerView().getValue());
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

    private void refreshData() {
        ThreadPoolUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                final List<Pair<String, String>> list = homeViewModel.getNetWorkInfo(getContext());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        homeViewModel.setValue(list);
                    }
                });
            }
        });
    }
}
