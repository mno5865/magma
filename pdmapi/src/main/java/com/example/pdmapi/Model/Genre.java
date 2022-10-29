/**
 * File: Genre.java
 * Genre.java: A public class that sets and gets the attributes for a genre.
 * @author Mildness Onyekwere - mno5865
 */
package com.example.pdmapi.Model;

/**
 * Genre class that defines the properties of an Genre matching with the genre table in the database
 */
public class Genre {

    private long genreId;

    private String name;

    /**
     * Constructor for Genre.
     */
    public Genre() {
    }

    /**
     * Takes genre and gets the ID.
     * @return id for the genre.
     */
    public long getGenreId() {
        return this.genreId;
    }

    /**
     * Takes genre and sets the ID.
     * @param genreId id for the genre.
     */
    public void setGenreID(long genreId) {
        this.genreId = genreId;
    }

    /**
     * Gets the name of the genre.
     * @return name of the genre.
     */
    public String getName() {
        return name;
    }

    /**
     * Creates an instance and sets the username for the genre.
     * @param username username for genre.
     */
    public void setName(String username) {
        this.name = username;
    }
}