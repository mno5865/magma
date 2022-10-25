package com.example.pdmapi.Model;

public class Genre {

    private long genreId;

    private String name;

    @ManyToMany(mappedBy = "genres")
    private List<Album> albums;

    public Genre() {
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
