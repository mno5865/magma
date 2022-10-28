package com.example.pdmapi.Model;

public class SongInView {

    long songId;
    String songTitle;
    String artistName;
    String albumTitle;
    long runtime;
    long listenCount;


    public SongInView() {
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public long getRuntime() {
        return runtime;
    }

    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }

    public long getListenCount() {
        return listenCount;
    }

    public void setListenCount(long listenCount) {
        this.listenCount = listenCount;
    }

}
