package com.github.codeforgreen.itrash.ui.report_garbage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.codeforgreen.itrash.databinding.FragmentHomeBinding;
import com.github.codeforgreen.itrash.databinding.FragmentRepotGarbageBinding;
import com.github.codeforgreen.itrash.ui.home.HomeViewModel;

public class ReportGarbageFragment extends Fragment {
    private ReportGarbageViewModel reportGarbageViewModel;
    private FragmentRepotGarbageBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportGarbageViewModel =
                new ViewModelProvider(this).get(ReportGarbageViewModel.class);

        binding = FragmentRepotGarbageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textReportGarbage;
        reportGarbageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
