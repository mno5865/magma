package com.example.pdmapi.Model;

import com.example.pdmapi.Model.Keys.SongGenreKey;
import javax.persistence.*;

@Entity
public class HasSong {
    @EmbeddedId
    SongGenreKey id;

    @ManyToOne
    @MapsId("songId")
    @JoinColumn(name = "song_id")
    Song song;

    @ManyToOne
    @MapsId("genreId")
    @JoinColumn(name = "genre_id")
    Genre genre;

    public SongGenreKey getId() {
        return id;
    }

    public void setId(SongGenreKey id) {
        this.id = id;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
