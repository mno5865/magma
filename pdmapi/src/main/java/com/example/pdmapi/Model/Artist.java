package com.example.pdmapi.Model;

import javax.persistence.*;

@Entity
@Table(name = "artist", schema = "p32001_08")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ArtistID")
    private long artistID;

    @Column(name = "name")
    private String name;

    public Artist() {
    }

    public Artist(String name) {
        this.name = name;
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
