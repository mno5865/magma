package com.example.pdmapi.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "album", schema = "p32001_08")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private long albumId;

    @Column(name = "title")
    private String title;

    @Column(name = "release_date")
    private String release_date;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "AlbumContainsSong",
            joinColumns = @JoinColumn(name = "song_id", referencedColumnName = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id", referencedColumnName = "song_id"))
    private List<Song> songs;

    @ManyToMany(mappedBy = "artist_albums")
    List<Artist> artist_albums;

    public Album() {
    }


    public void setAlbumID(long albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }
}
