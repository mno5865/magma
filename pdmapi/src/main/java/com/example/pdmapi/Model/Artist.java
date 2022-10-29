/**
 * File: Artist.java
 * Artist.java: A public class that sets and gets the attributes for an artist.
 * @author Gregory Ojiem - gro3228
 */
package com.example.pdmapi.Model;


/**
 * Artist class that defines the properties of an Artist, matches the artist table in the database
 */
public class Artist {

    private long artistID;

    private String name;

    /**
     *  Constructor for Artist.
     */
    public Artist() {
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
}