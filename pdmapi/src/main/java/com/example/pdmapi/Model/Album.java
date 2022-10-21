package com.example.pdmapi.Model;

import javax.persistence.*;

@Entity
@Table(name = "album", schema = "p32001_08")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private long albumId;

    @Column(name = "title")
    private String title;

    @Column(name = "release_date")
    private String release_date;

    public Album() {
    }


    public void setAlbumID(long albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
