package com.example.pdmapi.Model;

import javax.persistence.*;

@Entity
@Table(name = "genre", schema = "p32001_08")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genreId")
    private long genreID;

    @Column(name = "name")
    private String name;

    public Genre() {
    }

    public Genre(long genreID, String username) {
        this.genreID = genreID;
        this.name = username;
    }

    public long getGenreID() {
        return genreID;
    }

    public void setGenreID(long genreID) {
        this.genreID = genreID;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }
}
