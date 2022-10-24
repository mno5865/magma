package com.example.pdmapi.Model.Keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SongAlbumKey implements Serializable {
    @Column(name = "song_id")
    private long songId;

    @Column(name = "album_id")
    private long albumId;


    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongAlbumKey other = (SongAlbumKey) o;
        return songId == other.songId && albumId == other.albumId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(songId, albumId);
    }
}
