package com.github.codeforgreen.itrash.ui.calendar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.codeforgreen.itrash.R;
import com.github.codeforgreen.itrash.databinding.FragmentCalendarBinding;
import com.github.codeforgreen.itrash.tasks.GetUserRegion;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences preferences = getActivity().getSharedPreferences("iTrash", MODE_PRIVATE);
        String token = preferences.getString("token", "");
        try {
            new GetUserRegion((AppCompatActivity) this.getActivity(), token).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MaterialCalendarView calendarView = binding.calendarView;
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        calendarView.setSelectionColor(getResources().getColor(R.color.green));


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
