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

    long songId;
    long albumId;
    long artistId;
    String songTitle;
    String artistName;
    String albumTitle;
    Date releaseDate;
    long runtime;
    long listenCount;


    /**
     * Constructor for SongInView.
     */
    public SongInView() {
    }

    /**
     *
     * @return
     */
    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public String getSongTitle() {
        return songTitle;
    }

    /**
     *
     * @param songTitle
     */
    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    /**
     *
     * @return
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     *
     * @param artistName
     */
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    /**
     *
     * @return
     */
    public String getAlbumTitle() {
        return albumTitle;
    }

    /**
     *
     * @param albumTitle
     */
    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    /**
     *
     * @return
     */
    public long getRuntime() {
        return runtime;
    }

    /**
     *
     * @param runtime
     */
    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }

    /**
     *
     * @return
     */
    public long getListenCount() {
        return listenCount;
    }

    /**
     *
     * @param listenCount
     */
    public void setListenCount(long listenCount) {
        this.listenCount = listenCount;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
