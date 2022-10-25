package com.example.pdmapi.Model;

import com.example.pdmapi.Model.Keys.AlbumGenreKey;

import javax.persistence.*;

@Entity
public class AlbumGenre {
    @EmbeddedId
    private AlbumGenreKey id;

    @ManyToOne
    @MapsId("albumId")
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToOne
    @MapsId("genreId")
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public AlbumGenreKey getId() {
        return id;
    }

    public void setId(AlbumGenreKey id) {
        this.id = id;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
