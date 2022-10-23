package com.example.pdmapi.Model.Keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AlbumArtistKey implements Serializable{

    @Column(name = "album_id")
    private long albumId;

    @Column(name = "artist_id")
    private long artistId;

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
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
        AlbumArtistKey that = (AlbumArtistKey) o;
        return albumId == that.albumId && artistId == that.artistId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumId, artistId);
    }
}
