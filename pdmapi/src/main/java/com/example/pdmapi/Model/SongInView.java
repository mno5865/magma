/**
 * File: SongInView.java
 * SongInView.java: A public class that sets and gets the attributes for a song in the view class.
 * @author MAGMA
 */
package com.example.pdmapi.Model;

/**
 * Class SongInView that defines all properties of the view.
 */
public class SongInView {
    String songTitle;
    String artistName;
    String albumTitle;
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
}