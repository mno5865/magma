package com.example.pdmapi.Model;

import com.example.pdmapi.Model.Keys.SongAlbumKey;
//import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;

@Entity
public class AlbumContainsSong {
    @EmbeddedId
    private SongAlbumKey id;

    @ManyToOne
    @MapsId("songId")
    @JoinColumn(name = "song_id")
    private Song song;

    @ManyToOne
    @MapsId("albumId")
    @JoinColumn(name = "album_id")
    private Album album;

   // @Unique
    @Column(insertable = false, updatable = false, columnDefinition="serial")
    private int trackNumber;

    public SongAlbumKey getId() {
        return id;
    }

    public void setId(SongAlbumKey id) {
        this.id = id;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }
}
