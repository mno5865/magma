package com.example.pdmapi.Model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "song", schema = "p32001_08")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private long songId;

    @Column(name = "title")
    private String title;

    @Column(name = "runtime")
    private Time runtime;

    @Column(name = "release_date")
    private Date releaseDate;

    @ManyToMany(mappedBy = "songs")
    private List<Album> albums;

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
