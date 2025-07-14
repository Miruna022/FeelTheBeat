package com.example.feelthebeat;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;


import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private Context context;
    private List<Playlist> playlists;

    public PlaylistAdapter(Context context, List<Playlist> playlists){
        this.context = context;
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_playlist, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);
        holder.playlistName.setText(playlist.name);
        RequestOptions opts = RequestOptions.bitmapTransform(new RoundedCorners(20));
        Glide.with(context).load(playlist.image).apply(opts).into(holder.playlistImage);

        holder.openSpotifyButton.setOnClickListener(v -> openSpotify(playlist.spotifyUrl));
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    private void openSpotify(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setPackage("com.spotify.music");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {      // no app → open in browser
            Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(web);
        }
    }

    static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        TextView playlistName;
        ImageView playlistImage;
        ImageButton openSpotifyButton;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            playlistName = itemView.findViewById(R.id.playlistName);
            playlistImage = itemView.findViewById(R.id.playlistImage);
            openSpotifyButton = itemView.findViewById(R.id.openSpotifyButton);
        }
    }
}
