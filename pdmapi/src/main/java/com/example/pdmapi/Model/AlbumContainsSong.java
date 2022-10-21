package com.example.pdmapi.Model;

import com.example.pdmapi.Model.Keys.SongAlbumKey;

import javax.persistence.*;

@Entity
public class AlbumContainsSong {
    @EmbeddedId
    SongAlbumKey id;

    @ManyToOne
    @MapsId("songId")
    @JoinColumn(name = "song_id")
    Song song;

    @ManyToOne
    @MapsId("albumId")
    @JoinColumn(name = "album_id")
    Album album;

    int trackNumber;
}
