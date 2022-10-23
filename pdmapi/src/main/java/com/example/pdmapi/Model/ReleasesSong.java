package com.example.pdmapi.Model;

import com.example.pdmapi.Model.Keys.SongArtistKey;
import javax.persistence.*;

@Entity
public class ReleasesSong {

    @EmbeddedId
    SongArtistKey id;

    @ManyToOne
    @MapsId("songId")
    @JoinColumn(name = "song_id")
    Song song;

    @ManyToOne
    @MapsId("artistId")
    @JoinColumn(name = "artist_id")
    Artist artist;

    public SongArtistKey getId() {
        return id;
    }

    public void setId(SongArtistKey id) {
        this.id = id;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
