package com.example.pdmapi.Model;


import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "user", schema = "p32001_08")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private long userID;

    @Unique
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Unique
    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "access_date")
    private Date accessDate;

    public User() {
    }

    public User(@Unique String username, String password,
                @Unique String email, String firstName,
                String lastName, Date creationDate, Date accessDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creationDate = creationDate;
        this.accessDate = accessDate;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }
}
