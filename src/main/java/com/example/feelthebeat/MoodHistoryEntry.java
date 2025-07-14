package com.example.feelthebeat;

public class MoodHistoryEntry {
    private long timestamp;
    private String mood;
    private String playlistName;

    public MoodHistoryEntry(long timestamp, String mood, String playlistName) {
        this.timestamp = timestamp;
        this.mood = mood;
        this.playlistName = playlistName;
    }

    public long getTimestamp(){ return timestamp; }
    public String getMood(){ return mood; }
    public String getPlaylistName(){ return playlistName; }
}
