package com.example.pdmapi.Model;

import javax.persistence.*;

@Entity
@Table(name = "song", schema = "p32001_08")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private long songId;

    @Column(name = "title")
    private String title;

    @Column(name = "run_time")
    private String runtime;

    @Column(name = "release_date")
    private String release_date;

    public Song() {
    }


    public void setSongID(long songId) {
        this.songId = songId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
