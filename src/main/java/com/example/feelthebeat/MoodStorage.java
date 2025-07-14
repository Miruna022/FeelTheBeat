package com.example.feelthebeat;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MoodStorage {
    private static final String PREFS_NAME = "mood_history_prefs";
    private static final String KEY_HISTORY = "history_list";

    private SharedPreferences prefs;
    private Gson gson;

    public MoodStorage(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public List<MoodHistoryEntry> loadHistory() {
        String json = prefs.getString(KEY_HISTORY, null);
        if (json == null) return new ArrayList<>();
        Type listType = new TypeToken<List<MoodHistoryEntry>>() {}.getType();
        return gson.fromJson(json, listType);
    }

    public void saveHistory(List<MoodHistoryEntry> history) {
        String json = gson.toJson(history);
        prefs.edit().putString(KEY_HISTORY, json).apply();
    }

    public void addEntry(MoodHistoryEntry entry) {
        List<MoodHistoryEntry> history = loadHistory();
        history.add(entry);
        saveHistory(history);
    }

    public List<MoodHistoryEntry> loadHistoryForDay() {
        long now = System.currentTimeMillis();
        long startOfDay = getStartOfDayMillis(now);
        return loadHistoryFiltered(startOfDay, now);
    }

    public List<MoodHistoryEntry> loadHistoryForWeek() {
        long now = System.currentTimeMillis();
        long startOfWeek = getStartOfWeekMillis(now);
        return loadHistoryFiltered(startOfWeek, now);
    }

    public List<MoodHistoryEntry> loadHistoryForMonth() {
        long now = System.currentTimeMillis();
        long startOfMonth = getStartOfMonthMillis(now);
        return loadHistoryFiltered(startOfMonth, now);
    }

    private List<MoodHistoryEntry> loadHistoryFiltered(long startMillis, long endMillis) {
        List<MoodHistoryEntry> allEntries = loadHistory();
        List<MoodHistoryEntry> filtered = new ArrayList<>();
        for (MoodHistoryEntry entry : allEntries) {
            long entryTime = entry.getTimestamp();
            if (entryTime >= startMillis && entryTime <= endMillis) {
                filtered.add(entry);
            }
        }
        return filtered;
    }

    // Helper methods to get start of day/week/month in milliseconds
    private long getStartOfDayMillis(long timeMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeMillis);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    private long getStartOfWeekMillis(long timeMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeMillis);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    private long getStartOfMonthMillis(long timeMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeMillis);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
}

