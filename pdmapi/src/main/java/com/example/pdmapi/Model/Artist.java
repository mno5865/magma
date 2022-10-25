package com.example.pdmapi.Model;

public class Artist {

    private long artistID;

    private String name;

    public Artist() {
    }

    public long getArtistID() {
        return artistID;
    }
    public void setArtistID(long artistID) {
        this.artistID = artistID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
