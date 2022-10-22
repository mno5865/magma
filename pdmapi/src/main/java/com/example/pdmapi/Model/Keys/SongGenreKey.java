package com.example.pdmapi.Model.Keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SongGenreKey implements Serializable{

    @Column(name = "song_id")
    private long songId;

    @Column(name = "genre_id")
    private long genreId;

    public long getSongId()
    {
        return songId;
    }

    public void setSongId(long songId)
    {
        this.songId = songId;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongGenreKey that = (SongGenreKey) o;
        return songId == that.songId && genreId == that.genreId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(songId, genreId);
    }
}