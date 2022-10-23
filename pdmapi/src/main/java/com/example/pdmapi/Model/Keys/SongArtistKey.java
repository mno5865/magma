package com.example.pdmapi.Model.Keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SongArtistKey implements Serializable{

    @Column(name = "song_id")
    private long songId;

    @Column(name = "artist_id")
    private long artistId;

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongArtistKey that = (SongArtistKey) o;
        return songId == that.songId && artistId == that.artistId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(songId, artistId);
    }
}
