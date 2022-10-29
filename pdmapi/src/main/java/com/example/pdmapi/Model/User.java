/**
 * File: User.java
 * User.java: A public class that sets and gets the attributes for a user class.
 * @author Mildness Onyekwere - mno5865
 */
package com.example.pdmapi.Model;

//import org.checkerframework.common.aliasing.qual.Unique;

/**
 * Import Statements
 */
import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * User class that defines the properties of an User matching with the user table in the database
 */
public class User {

    private long userID;

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private Date creationDate;

    private Timestamp accessDate;

    /**
     * Constructor for User.
     */
    public User() {
    }

    /**
     * Takes user and gets the ID.
     * @return id for the user.
     */
    public long getUserID() {
        return this.userID;
    }

    /**
     * Takes user and sets the ID.
     * @param userID id for the user.
     */
    public void setUserID(long userID) {
        this.userID = userID;
    }

    /**
     * Gets the username of the user.
     * @return username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     * @param username username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * @param password the password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the email of the user.
     * @return the email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     * @param email the email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the first name of the user.
     * @return the first name for the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Takes an instance and sets the name for the user.
     * @param firstName the first name for the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of user.
     * @return the last name for the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Takes an instance and sets the last name for the user.
     * @param lastName the last name for the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the creation date for the user.
     * @return the creation date for the user.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Takes an instance and sets the creation date for the user.
     * @param creationDate the creation date for the user.
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Get the access date for the user.
     * @return the access date for the user.
     */
    public Timestamp getAccessDate() {
        return accessDate;
    }

    /**
     * Takes an instance and sets the access date for the user.
     * @param accessDate the access date for the user.
     */
    public void setAccessDate(Timestamp accessDate) {
        this.accessDate = accessDate;
    }
}