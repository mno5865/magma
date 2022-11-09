/**
 * File: Artist.java
 * Artist.java: A public class that sets and gets the attributes for an artist.
 * @author Gregory Ojiem - gro3228, Ananya Misra - am4063
 */
package com.example.pdmapi.Model;


/**
 * Artist class that defines the properties of an Artist, matches the artist table in the database
 */
public class Artist implements Comparable<Artist> {

    private long artistID;

    /**
     * Gets the Collection count.
     * @return the amount of collections for the artist.
     */
    public int getCollectionCount() {
        return collectionCount;
    }

    /**
     * Sets the Collection count.
     * @param collectionCount the amount of collections for the artist.
     */
    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    /**
     * Gets the play count.
     * @return the amounto f times an artist was played.
     */
    public int getPlayCount() {
        return playCount;
    }

    /**
     * Sets the amount of times an artist is played.
     * @param playCount number of times something is played
     */
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

    /**
     * Constructor for Artist
     * @param name name of the artist.
     * @param artistID the id for the artist
     */
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

    /**
     * Compares the different Artists
     * @param o the object to be compared.
     * @return the difference
     */
    @Override
    public int compareTo(Artist o) {
        int diff = o.getCollectionCount() - this.getCollectionCount();
        if(diff == 0){
            diff = name.compareTo(name);
        }
        return diff;
    }
}