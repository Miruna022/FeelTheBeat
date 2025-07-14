package com.example.feelthebeat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Playlists extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.playlist_screen);

        String selectedMood = getIntent().getStringExtra("SelectedMood");
        if (selectedMood == null){
            selectedMood = MoodDataHolder.getInstance().getMood();
        }

        ImageButton btnReturn = findViewById(R.id.btnBack);
        Button history = findViewById(R.id.btnHistory);
        RecyclerView recyclerView = findViewById(R.id.playlistRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Playlist> playlists = getPlaylistsForMood(selectedMood);
        PlaylistAdapter adapter = new PlaylistAdapter(this, playlists);
        recyclerView.setAdapter(adapter);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Playlists.this, History.class);
                startActivity(intent2);
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private List<Playlist> getPlaylistsForMood(String mood){
        switch (mood) {
            case "Happy":
                return Arrays.asList(
                        new Playlist("Sunshine vibes", R.drawable.happy1, "spotify:playlist:37i9dQZF1EIgG2NEOhqsD7"),
                        new Playlist("Feel Good Grooves", R.drawable.happy2, "https://open.spotify.com/playlist/3dnXGas4EgEYxlphPZT6zF"),
                        new Playlist("Smile & Dance", R.drawable.happy3, "https://open.spotify.com/playlist/37i9dQZF1EIeGJYLQzhCpL"),
                        new Playlist("Pure Joy", R.drawable.happy4, "https://open.spotify.com/playlist/7INcD4lmarWTQiDVodjVt4"),
                        new Playlist("Upbeat & Alive", R.drawable.happy5, "https://open.spotify.com/playlist/37i9dQZF1EIeEZPgsd7pko"),
                        new Playlist("Golden Hour Energy", R.drawable.happy6, "https://open.spotify.com/playlist/37i9dQZF1EVJSvZp5AOML2")
                );
            case "Sad":
                return Arrays.asList(
                        new Playlist("Rainy Day", R.drawable.sad1 , "https://open.spotify.com/playlist/37i9dQZF1DWVrtsSlLKzro"),
                        new Playlist("Melancholy Moments", R.drawable.sad2 , "https://open.spotify.com/playlist/37i9dQZF1EIeODNDegVpao"),
                        new Playlist("Softly broken", R.drawable.sad3, "https://open.spotify.com/playlist/37i9dQZF1EVKuMoAJjoTIw"),
                        new Playlist("Blue Notes",R.drawable.sad4 , "https://open.spotify.com/playlist/37i9dQZF1DWZrc3lwvImLj"),
                        new Playlist("Sad But Beautiful",R.drawable.sad5 , "https://open.spotify.com/playlist/37i9dQZF1EIe8iVhSfv6fg"),
                        new Playlist("Tearjerker Tunes", R.drawable.sad6 , "https://open.spotify.com/playlist/2iZ8LB8ulqCp001Kaco0bY")
                );
            case "Angry":
                return Arrays.asList(
                        new Playlist("Unleash The Fury", R.drawable.anger1 , "https://open.spotify.com/playlist/37i9dQZF1EIhuCNl2WSFYd"),
                        new Playlist("Break Stuff Beats", R.drawable.anger2 , "https://open.spotify.com/playlist/37i9dQZF1EIgNZCaOGb0Mi"),
                        new Playlist("Rage Mode", R.drawable.anger3 , "https://open.spotify.com/playlist/37i9dQZF1EIh2g9BXwCTIZ"),
                        new Playlist("Explosive Energy", R.drawable.anger4 , "https://open.spotify.com/playlist/0esDZ4aONv9JX8cY7mAOuo"),
                        new Playlist("Loud Beats", R.drawable.anger5 , "https://open.spotify.com/playlist/37i9dQZF1EIgXpBj3GZb7p"),
                        new Playlist("Fury Fuel", R.drawable.anger6 , "https://open.spotify.com/playlist/0mZPzZlZaDCtlEUcrJWpwA")
                );
            case "Relaxed":
                return Arrays.asList(
                        new Playlist("Chill Vibes", R.drawable.relax1 , "https://open.spotify.com/playlist/37i9dQZF1DX4WYpdgoIcn6"),
                        new Playlist("Relax & Unwind", R.drawable.relax2 , "https://open.spotify.com/playlist/37i9dQZF1DWUvZBXGjNCU4"),
                        new Playlist("Gentle Waves", R.drawable.relax3 , "https://open.spotify.com/playlist/7JabddFr3Q6JPsND4v9Swf"),
                        new Playlist("Slow Sunday", R.drawable.relax4 , "https://open.spotify.com/playlist/37i9dQZF1EIeDsGY3D7QC6"),
                        new Playlist("Peaceful Escapes", R.drawable.relax5 , "https://open.spotify.com/playlist/6IKQrtMc4c00YzONcUt7QH"),
                        new Playlist("Calm & Collected", R.drawable.relax6 , "https://open.spotify.com/playlist/37i9dQZF1EIf4OaZ1XTJYw")
                );
            case "Tired":
                return Arrays.asList(
                        new Playlist("Sleepy Rhythms", R.drawable.tired1 , "https://open.spotify.com/playlist/37i9dQZF1EIcA5FXgsbctr"),
                        new Playlist("Night Silence", R.drawable.tired2 , "https://open.spotify.com/playlist/7GZwptPT0TLJjzv7gKNEFg"),
                        new Playlist("Exhausted Echoes", R.drawable.tired3 , "https://open.spotify.com/playlist/37i9dQZF1EIfqMuHDHBmcS"),
                        new Playlist("Soft Fade", R.drawable.tired4 , "https://open.spotify.com/playlist/34Xv1hxN6wZ2i47QBQmRT9"),
                        new Playlist("Good Night Sleep", R.drawable.tired5 , "https://open.spotify.com/playlist/37i9dQZF1DXa1rZf8gLhyz"),
                        new Playlist("Dreamy Doses", R.drawable.tired6 , "https://open.spotify.com/playlist/3dDX5sOtPRUkMXPExe8RbH")
                );
            case "Excited":
                return Arrays.asList(
                        new Playlist("Hype Train", R.drawable.excited1 , "https://open.spotify.com/playlist/37i9dQZF1EIh4zcdX2LJPS"),
                        new Playlist("Get Pumped!", R.drawable.excited2 , "https://open.spotify.com/playlist/37i9dQZF1EIcVD7Tg8a0MY"),
                        new Playlist("Adrenaline Rush", R.drawable.excited3 , "https://open.spotify.com/playlist/37i9dQZF1EIgqwY0koJJp6"),
                        new Playlist("Turn Up Now", R.drawable.excited4 , "https://open.spotify.com/playlist/37i9dQZF1EIeYvPpy9CZHr"),
                        new Playlist("Feel The Fire", R.drawable.excited5 , "https://open.spotify.com/playlist/37i9dQZF1EIhmPdvDDpdgZ"),
                        new Playlist("Electric Pulse", R.drawable.excited6 , "https://open.spotify.com/playlist/37i9dQZF1EIh4MW6LJNGMl")
                );
            case "Anxious":
                return Arrays.asList(
                        new Playlist("Breathe & Unwind", R.drawable.anxiety1 , "https://open.spotify.com/playlist/3l6b0zuXjgyPxLK6PIAqED"),
                        new Playlist("Anxiety Off", R.drawable.anxiety2 , "https://open.spotify.com/playlist/6nXGAVivEY4v08A1Xhi2fu"),
                        new Playlist("Soothing Storms", R.drawable.anxiety3 , "https://open.spotify.com/playlist/34tdVhisrNZcnevvcj7kUY"),
                        new Playlist("Safe Space", R.drawable.anxiety4 , "https://open.spotify.com/playlist/50I4qktsIoaMoeTZKzWPcd"),
                        new Playlist("Heart Calm Beats", R.drawable.anxiety5 , "https://open.spotify.com/playlist/37i9dQZF1DWSRc3WJklgBs"),
                        new Playlist("Ease The Mind", R.drawable.anxiety6 , "https://open.spotify.com/playlist/5BPdFbVNOKkcGrfpjlHzSs")
                );
            case "Lonely":
                return Arrays.asList(
                        new Playlist("Alone, Not Lonely", R.drawable.lonely1 , "https://open.spotify.com/playlist/1ZwQl7dqs8mifRiOW5b1aW"),
                        new Playlist("Echose In Silence", R.drawable.lonely2 , "https://open.spotify.com/playlist/37i9dQZF1EIg6gLNLe52Bd"),
                        new Playlist("Quiet Company", R.drawable.lonely3 , "https://open.spotify.com/playlist/37i9dQZF1EIhrfJdOCmxe4"),
                        new Playlist("One Is A Vibe", R.drawable.lonely4 , "https://open.spotify.com/playlist/7bbuao2x5GKGc1btO6AZeS"),
                        new Playlist("Solitary Sounds", R.drawable.lonely5 , "https://open.spotify.com/playlist/3vR4UTDqzBe0Z5IXTOzJpn"),
                        new Playlist("Comfort In Isolation", R.drawable.lonely6 , "https://open.spotify.com/playlist/1zM9ftIoG5uFcbzbUqoAb9")
                );
            default:
                return new ArrayList<>();
        }
    };

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        setIntent(intent);;
    }

}
