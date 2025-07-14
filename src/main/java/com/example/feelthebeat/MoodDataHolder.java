package com.example.feelthebeat;

public class MoodDataHolder {
    private static MoodDataHolder instance;
    private String mood;

    private MoodDataHolder() {}

    public static MoodDataHolder getInstance() {
        if (instance == null) {
            instance = new MoodDataHolder();
        }
        return instance;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }
}
