package com.example.feelthebeat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.MoodViewHolder> {

    private List<MoodHistoryEntry> moodList;

    public MoodAdapter(List<MoodHistoryEntry> moodList) {
        this.moodList = moodList;
    }

    public void updateData(List<MoodHistoryEntry> newList) {
        moodList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_mood, parent, false);
        return new MoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoodViewHolder holder, int position) {
        MoodHistoryEntry entry = moodList.get(position);
        holder.moodText.setText(entry.getMood());

        String time = android.text.format.DateFormat.format("hh:mm a", entry.getTimestamp()).toString();
        holder.timeText.setText(time);
    }

    @Override
    public int getItemCount() {
        return moodList.size();
    }

    static class MoodViewHolder extends RecyclerView.ViewHolder {
        TextView moodText, timeText;

        MoodViewHolder(View itemView) {
            super(itemView);
            moodText = itemView.findViewById(R.id.moodText);
            timeText = itemView.findViewById(R.id.timeText);
        }
    }
}

