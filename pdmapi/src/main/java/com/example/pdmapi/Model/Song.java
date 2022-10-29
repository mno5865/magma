/**
 * File: Song.java
 * Song.java: A public class that sets and gets the attributes for a song.
 * @author Gregory Ojiem - gro3228
 */
package com.example.pdmapi.Model;

/**
 * Import Statements
 */
import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 *  Class Song that defines all properties of songs.
 */
public class Song {

    private long songId;

    private String title;

    private long runtime;

    private Date releaseDate;

    /**
     * Constructor for Song.
     */
    public Song() {
    }

    /**
     * Takes song and gets the ID.
     * @return  id for the song.
     */
    public long getSongId() {
        return songId;
    }

    /**
     * Takes song and sets the ID.
     * @param songId id for the song.
     */
    public void setSongId(long songId) {
        this.songId = songId;
    }

    /**
     * Gets the title of the song.
     * @return title of the song.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Takes an instance and sets the title for the song.
     * @param title title of a song.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the runtime for the song.
     * @return runtime of the song.
     */
    public long getRuntime() {
        return runtime;
    }

    /**
     * Sets the runtime for the song.
     * @param runtime runtime of the song.
     */
    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }

    /**
     * Gets the date for when the song will release.
     * @return the song's release date.
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the date for when the song will release.
     * @param release_date the song's release date.
     */
    public void setReleaseDate(Date release_date) {
        this.releaseDate = release_date;
    }
}