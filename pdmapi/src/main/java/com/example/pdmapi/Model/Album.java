package com.example.pdmapi.Model;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

public class Album {

    private long albumId;

    private String title;

    private Date release_date;

    public Album() {
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(Date release_date) {
        this.release_date = release_date;
    }
}
