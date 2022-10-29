/**
 * File: Collection.java
 * Collection.java: A public class that sets and gets the attributes for a collection.
 * @author Gregory Ojiem - gro3228
 */
package com.example.pdmapi.Model;

/**
 * Import Statements
 */
import javax.persistence.*;

/**
 * Class Collection that defines all properties of an Collection.
 */
public class Collection {

    private long collectionId;

    private String title;

    /**
     * Constructor for Collection.
     */
    public Collection() {
    }

    /**
     * Takes collection and sets the ID.
     * @param collectionId id for the collection.
     */
    public void setCollectionID(long collectionId) {
        this.collectionId = collectionId;
    }

    /**
     * Gets the id for Collection.
     * @return the id for the collection.
     */
    public long getCollectionID() {
        return this.collectionId;
    }

    /**
     * Gets the title of the collection.
     * @return title of the collection.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Takes an instance and sets the title for the Collection.
     * @param title title of the collection.
     */
    public void setTitle(String title) {
        this.title = title;
    }
}