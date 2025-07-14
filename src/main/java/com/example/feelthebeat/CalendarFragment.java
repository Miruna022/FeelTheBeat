package com.example.feelthebeat;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarFragment extends Fragment {
    private Map<CalendarDay, List<MoodHistoryEntry>> moodsByDate = new HashMap<>();
    private MoodAdapter adapter;

    public CalendarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        MaterialCalendarView calendarView = view.findViewById(R.id.calendarView);
        RecyclerView recyclerView = view.findViewById(R.id.infoText);

        adapter = new MoodAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        MoodStorage storage = new MoodStorage(requireContext());
        List<MoodHistoryEntry> history = storage.loadHistory();

        // Group moods by date
        for (MoodHistoryEntry entry : history) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(entry.getTimestamp());
            CalendarDay day = CalendarDay.from(cal);
            if (!moodsByDate.containsKey(day)){
                moodsByDate.put(day, new ArrayList<>());
            }
            moodsByDate.get(day).add(entry);

        }


        Map<String, Integer> moodColors = new HashMap<>();
        moodColors.put("Happy", Color.parseColor("#FFD700"));     // Gold
        moodColors.put("Sad", Color.parseColor("#4682B4"));       // SteelBlue
        moodColors.put("Angry", Color.parseColor("#DC143C"));     // Crimson
        moodColors.put("Relaxed", Color.parseColor("#98FB98"));   // PaleGreen
        moodColors.put("Tired", Color.parseColor("#A9A9A9"));     // DarkGray
        moodColors.put("Excited", Color.parseColor("#FF69B4"));   // HotPink
        moodColors.put("Anxious", Color.parseColor("#FFA500"));   // Orange
        moodColors.put("Lonely", Color.parseColor("#8A2BE2"));    // BlueViolet


        for (Map.Entry<CalendarDay, List<MoodHistoryEntry>> moodEntry : moodsByDate.entrySet()) {
            List<MoodHistoryEntry> entries = moodEntry.getValue();
            if (!entries.isEmpty()) {
                // Count mood frequencies
                Map<String, Integer> moodCount = new HashMap<>();
                for (MoodHistoryEntry entry : entries) {
                    String mood = entry.getMood();
                    moodCount.put(mood, moodCount.getOrDefault(mood, 0) + 1);
                }

                // Find most frequent mood(s)
                int maxCount = 0;
                List<String> mostFrequentMoods = new ArrayList<>();
                for (Map.Entry<String, Integer> countEntry : moodCount.entrySet()) {
                    int count = countEntry.getValue();
                    if (count > maxCount) {
                        maxCount = count;
                        mostFrequentMoods.clear();
                        mostFrequentMoods.add(countEntry.getKey());
                    } else if (count == maxCount) {
                        mostFrequentMoods.add(countEntry.getKey());
                    }
                }

                // Resolve tie
                String chosenMood;
                if (mostFrequentMoods.size() == 1) {
                    chosenMood = mostFrequentMoods.get(0);
                } else {
                    chosenMood = entries.get(entries.size() - 1).getMood();
                }

                int color = moodColors.getOrDefault(chosenMood, Color.LTGRAY);
                calendarView.addDecorator(new MoodDecorator(moodEntry.getKey(), color));
            }
        }


        calendarView.setOnDateChangedListener(((widget, date, selected) -> {
            List<MoodHistoryEntry> moods = moodsByDate.getOrDefault(date, new ArrayList<>());
            adapter.updateData(moods);
        }));

        return view;
    }

    // Custom decorator class
    public static class MoodDecorator implements DayViewDecorator {
        private final CalendarDay date;
        private final int color;

        public MoodDecorator(CalendarDay date, int color) {
            this.date = date;
            this.color = color;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(15, color));
        }
    }
}