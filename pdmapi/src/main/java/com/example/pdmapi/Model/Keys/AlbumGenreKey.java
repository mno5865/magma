package com.example.pdmapi.Model.Keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AlbumGenreKey implements Serializable {
    @Column(name = "album_id")
    private long albumId;

    @Column(name = "genre_id")
    private long genreId;

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumGenreKey that = (AlbumGenreKey) o;
        return albumId == that.albumId && genreId == that.genreId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumId, genreId);
    }
}
