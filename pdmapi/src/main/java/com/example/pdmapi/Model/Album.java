/**
 * File: Album.java
 * Album.java: A public class that sets and gets the attributes for an album.
 * @author Gregory Ojiem - gro3228
 */
package com.example.pdmapi.Model;


/**
 * Import Statements.
 */
import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Class Album that defines all properties of an Album.
 */
public class Album {

    private long albumId;

    private String title;

    private Date release_date;

    /**
     * Constructor for Album.
     */
    public Album() {
    }

    /**
     * Takes album and sets the ID.
     * @param albumId id for the album.
     */
    public void setAlbumID(long albumId) {
        this.albumId = albumId;
    }

    /**
     * Gets the id for Album.
     * @return the id for the album.
     */
    public long getAlbumID() {
        return this.albumId;
    }

    /**
     * Gets the title of the album.
     * @return title of the album.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Takes an instance and sets the title for the album.
     * @param title title of the album.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the date for when the album will release.
     * @return the album release date.
     */
    public Date getReleaseDate() {
        return release_date;
    }

    /**
     * Sets the date for when the album will release.
     * @param release_date the album release date.
     */
    public void setReleaseDate(Date release_date) {
        this.release_date = release_date;
    }
}