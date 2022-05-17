package com.example.mediaplayer;

public class User {
    private String name;
    private String artist;
    private String time;

    public User(String name, String artist, String time){
        this.name = name;
        this.artist = artist;
        this.time = time;

    }

    public String getName(){
        return name;
    }

    public String getArtist(){
        return artist;
    }

    public String getTime(){
        return time;
    }
}
