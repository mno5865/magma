package com.example.pdmapi.Model;

import com.example.pdmapi.Model.Keys.AlbumArtistKey;
import javax.persistence.*;

@Entity
public class ReleasesAlbum {

    @EmbeddedId
    AlbumArtistKey id;

    @ManyToOne
    @MapsId("albumId")
    @JoinColumn(name = "album_id")
    Album album;

    @ManyToOne
    @MapsId("artistId")
    @JoinColumn(name = "artist_id")
    Artist artist;

    public AlbumArtistKey getId() {
        return id;
    }

    public void setId(AlbumArtistKey id) {
        this.id = id;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
