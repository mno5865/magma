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
}
