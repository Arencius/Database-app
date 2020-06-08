package com.example.database_app;
import java.util.Random;

public class Song {
    // fields corresponding to data about the song
    private String title, artist, album, duration;
    private int views;

    public Song(String title, String artist, String album, String duration, int views) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.views = views;
    }

    public String getTitle() {
        return title;
    }
    public String getArtist() {
        return artist;
    }
    public String getAlbum() {
        return album;
    }
    public String getDuration() {
        return duration;
    }
    public int getViews() { return views; }
}
