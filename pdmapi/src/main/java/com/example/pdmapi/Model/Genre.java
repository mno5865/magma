package com.example.pdmapi.Model;

public class Genre {

    private long genreId;

    private String name;

    public Genre() {
    }

    public long getGenreId() {
        return this.genreId;
    }

    public void setGenreID(long genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }
}
