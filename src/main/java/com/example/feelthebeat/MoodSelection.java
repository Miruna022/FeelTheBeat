package com.example.feelthebeat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MoodSelection extends AppCompatActivity {
    private LinearLayout happy, sad, angry, relaxed, tired, excited, anxious, lonely;
    private Button btnContinue;
    private LinearLayout selectedMood = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.mood_selection);

        happy = findViewById(R.id.moodHappy);
        sad = findViewById(R.id.moodSad);
        angry = findViewById(R.id.moodAngry);
        relaxed = findViewById(R.id.moodRelaxed);
        tired = findViewById(R.id.moodTired);
        excited = findViewById(R.id.moodExcited);
        anxious = findViewById(R.id.moodAnxious);
        lonely = findViewById(R.id.moodLonely);
        btnContinue = findViewById(R.id.continueButton);
        btnContinue.setEnabled(false);

        setMoodClickListener(happy);
        setMoodClickListener(sad);
        setMoodClickListener(angry);
        setMoodClickListener(relaxed);
        setMoodClickListener(tired);
        setMoodClickListener(excited);
        setMoodClickListener(anxious);
        setMoodClickListener(lonely);

        Button history = findViewById(R.id.btnHistory);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MoodSelection.this, History.class);
                startActivity(intent2);
            }
        });


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedMood != null){
                    String mood = getMoodName(selectedMood);
                    MoodDataHolder.getInstance().setMood(mood);
                    MoodHistoryEntry entry = new MoodHistoryEntry(System.currentTimeMillis(), mood, null);
                    MoodStorage storage = new MoodStorage(MoodSelection.this);
                    storage.addEntry(entry);
                    //Pass mood to playlist screen
                    Intent intent = new Intent(MoodSelection.this, Playlists.class);
                    intent.putExtra("SelectedMood", mood);
                    startActivity(intent);
                }
            }
        });
    }

    private void setMoodClickListener(LinearLayout mood){
        mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectMood(mood);
            }
        });
    }

    private void selectMood(LinearLayout mood){
        if (selectedMood != null){
            selectedMood.setBackgroundResource(R.drawable.rounded_mood);
        }
        selectedMood = mood;
        selectedMood.setBackgroundResource(R.drawable.rounded_mood_selected);
        btnContinue.setEnabled(true);
    }

    private String getMoodName(LinearLayout moodLayout) {
        switch(moodLayout.getId()) {
            case R.id.moodHappy: return "Happy";
            case R.id.moodSad: return "Sad";
            case R.id.moodAngry: return "Angry";
            case R.id.moodRelaxed: return "Relaxed";
            case R.id.moodTired: return "Tired";
            case R.id.moodExcited: return "Excited";
            case R.id.moodAnxious: return "Anxious";
            case R.id.moodLonely: return "Lonely";
            default: return "Unknown";
        }
    }
}
