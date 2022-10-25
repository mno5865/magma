package com.example.pdmapi.Model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class Song {

    private long songId;

    private String title;

    private Time runtime;

    private Date releaseDate;

    public Song() {
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Time getRuntime() {
        return runtime;
    }

    public void setRuntime(Time runtime) {
        this.runtime = runtime;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date release_date) {
        this.releaseDate = release_date;
    }
}
