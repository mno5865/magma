/**
 * File: Artist.java
 * Artist.java: A public class that sets and gets the attributes for an artist.
 * @author Gregory Ojiem - gro3228
 */
package com.example.pdmapi.Model;


/**
 * Artist class that defines the properties of an Artist, matches the artist table in the database
 */
public class Artist implements Comparable<Artist> {

    private long artistID;

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    private String name;

    private int collectionCount;
    private int playCount;

    /**
     *  Constructor for Artist.
     */
    public Artist() {
    }

    public Artist(String name , long artistID){
        this.artistID = artistID;
        this.name = name;
    }


    /**
     * Gets the id for Artist.
     * @return the id for the artist.
     */
    public long getArtistID()
    {
        return artistID;
    }

    /**
     * Takes artist and sets the ID.
     * @param artistID id for the artist.
     */
    public void setArtistID(long artistID){
        this.artistID = artistID;
    }

    /**
     * Gets the name of the artist.
     * @return name of the artist.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for the Artist.
     * @param name name of the artist.
     */
    public void setName(String name) {
        this.name = name;
    }

    public int rankBasedOnPlayAndCollections(){
        return collectionCount+playCount;
    }


    @Override
    public int compareTo(Artist o) {
        int diff = o.getCollectionCount() - this.getCollectionCount();
        if(diff == 0){
            diff = name.compareTo(name);
        }
        return diff;
    }
}