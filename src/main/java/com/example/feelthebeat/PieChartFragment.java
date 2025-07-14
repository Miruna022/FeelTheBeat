package com.example.feelthebeat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieChartFragment extends Fragment {
    private PieChart pieChart;
    private LinearLayout col1, col2;
    private RadioGroup timeframeToggle;
    private MoodStorage storage;

    private Map<String, Integer> moodColors;

    public PieChartFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);

        pieChart = view.findViewById(R.id.pie_chart);
        col1 = view.findViewById(R.id.legend_column_1);
        col2 = view.findViewById(R.id.legend_column_2);
        timeframeToggle = view.findViewById(R.id.timeframe_toggle);
        storage = new MoodStorage(requireContext());

        moodColors = new HashMap<>();
        moodColors.put("Happy", Color.parseColor("#FFD700"));     // Gold
        moodColors.put("Sad", Color.parseColor("#4682B4"));       // SteelBlue
        moodColors.put("Angry", Color.parseColor("#DC143C"));     // Crimson
        moodColors.put("Relaxed", Color.parseColor("#98FB98"));   // PaleGreen
        moodColors.put("Tired", Color.parseColor("#A9A9A9"));     // DarkGray
        moodColors.put("Excited", Color.parseColor("#FF69B4"));   // HotPink
        moodColors.put("Anxious", Color.parseColor("#FFA500"));   // Orange
        moodColors.put("Lonely", Color.parseColor("#8A2BE2"));    // BlueViolet

        updatePieChart("daily");

        timeframeToggle.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_daily:
                    updatePieChart("daily");
                    break;
                case R.id.rb_weekly:
                    updatePieChart("weekly");
                    break;
                case R.id.rb_monthly:
                    updatePieChart("monthly");
                    break;
            }
        });

        return view;
    }

    private void updatePieChart(String timeframe) {
        col1.removeAllViews();
        col2.removeAllViews();

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        List<MoodHistoryEntry> filteredList;

        switch (timeframe) {
            case "weekly":
                filteredList = storage.loadHistoryForWeek();
                break;
            case "monthly":
                filteredList = storage.loadHistoryForMonth();
                break;
            case "daily":
            default:
                filteredList = storage.loadHistoryForDay();
                break;
        }

        Map<String, Integer> moodCounts = new HashMap<>();
        for (MoodHistoryEntry entry : filteredList) {
            String mood = entry.getMood();
            moodCounts.put(mood, moodCounts.getOrDefault(mood, 0) + 1);
        }

        List<String> moodList = new ArrayList<>(moodCounts.keySet());

        for (int i = 0; i < moodList.size(); i++) {
            String mood = moodList.get(i);
            int count = moodCounts.get(mood);
            pieEntries.add(new PieEntry(count, mood));
            colors.add(moodColors.getOrDefault(mood, Color.GRAY));

            // Create legend item
            LinearLayout itemLayout = new LinearLayout(requireContext());
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);
            itemLayout.setPadding(0, 8, 0, 8);

            View colorDot = new View(requireContext());
            LinearLayout.LayoutParams dotParams = new LinearLayout.LayoutParams(30, 30);
            dotParams.setMargins(0, 0, 16, 0);
            colorDot.setLayoutParams(dotParams);
            colorDot.setBackgroundColor(moodColors.getOrDefault(mood, Color.GRAY));

            TextView label = new TextView(requireContext());
            label.setText(mood);
            label.setTextColor(Color.WHITE);
            label.setTextSize(16f);

            itemLayout.addView(colorDot);
            itemLayout.addView(label);


            if (i % 2 == 0) {
                col1.addView(itemLayout);
            } else {
                col2.addView(itemLayout);
            }
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Mood");
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(16f);
        pieData.setValueTextColor(Color.BLACK);

        pieChart.setData(pieData);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.parseColor("#044d30"));
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateXY(1000, 1000);
        pieChart.invalidate(); // refresh chart
    }
}
