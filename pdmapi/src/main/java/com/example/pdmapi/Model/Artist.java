package com.example.pdmapi.Model;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "artist", schema = "p32001_08")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "artistid")
    private long artistID;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "ReleasesSong",
            joinColumns  = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private List<Song> artist_songs;

    @ManyToMany
    @JoinTable(
            name = "ReleasesAlbum",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id"))
    private List<Album> artist_albums;

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
