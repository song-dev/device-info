package com.song.deviceinfo.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.song.deviceinfo.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chensongsong on 2020/5/26.
 */
public class AboutFragment extends Fragment {

    private AboutViewModel aboutViewModel;
    private Unbinder bind;
    @BindView(R.id.tv_about_version)
    TextView versionTv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        bind = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aboutViewModel =
                ViewModelProviders.of(this).get(AboutViewModel.class);
        aboutViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                versionTv.setText(s);
            }
        });
        aboutViewModel.setData(getContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

}
