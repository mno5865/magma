/**
 * File: SongInView.java
 * SongInView.java: A public class that sets and gets the attributes for a song in the view class.
 * @author Mildness Onyekwere - mno5865
 */
package com.example.pdmapi.Model;
import java.sql.Date;

/**
 * SongInView class that defines the properties of an SongInView matching with the materialized
 * view song_view in the database
 */
public class SongInView {

    private long songId;
    private long albumId;
    private long artistId;
    private String songTitle;
    private String artistName;
    private String albumTitle;
    private Date releaseDate;
    private long runtime;
    private long listenCount;


    /**
     * Constructor for SongInView.
     */
    public SongInView() {
    }

    /**
     * Gets the song id of the SongInView
     * @return The song id
     */
    public long getSongId() {
        return songId;
    }

    /**
     * Sets the song id of the SongInView
     * @param songId The song id
     */
    public void setSongId(long songId) {
        this.songId = songId;
    }

    /**
     * Gets the album id of the SongInView
     * @return The album id
     */
    public long getAlbumId() {
        return albumId;
    }

    /**
     * Sets the album id of the SongInView
     * @param albumId The album id
     */
    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    /**
     * Gets the artist id of the SongInView
     * @return The artist id
     */
    public long getArtistId() {
        return artistId;
    }

    /**
     * Sets the artist id of the SongInView
     * @param artistId The artist id
     */
    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    /**
     * Gets the song title of the SongInView
     * @return The song title
     */
    public String getSongTitle() {
        return songTitle;
    }

    /**
     * Sets the song title of the SongInView
     * @param songTitle The song title
     */
    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    /**
     * Gets the artist name of the SongInView
     * @return The artist name
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * Sets the artist name of the SongInView
     * @param artistName The artist name
     */
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    /**
     * Gets the album title of the SongInView
     * @return The album title
     */
    public String getAlbumTitle() {
        return albumTitle;
    }

    /**
     * Sets the album title of the SongInView
     * @param albumTitle The album title
     */
    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    /**
     * Gets the runtime of the SongInView
     * @return The runtime
     */
    public long getRuntime() {
        return runtime;
    }

    /**
     * Sets the runtime of the SongInView
     * @param runtime The runtime
     */
    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }

    /**
     * Gets the listen count of the SongInView
     * @return The listen count
     */
    public long getListenCount() {
        return listenCount;
    }

    /**
     * Sets the listen count of the SongInView
     * @param listenCount The listen count
     */
    public void setListenCount(long listenCount) {
        this.listenCount = listenCount;
    }

    /**
     * Gets the release date of the SongInView
     * @return The release date
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the release date of the SongInView
     * @param releaseDate The release date
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
