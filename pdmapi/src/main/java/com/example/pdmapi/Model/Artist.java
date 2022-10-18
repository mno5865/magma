package com.example.pdmapi.Model;

import javax.persistence.*;

@Entity
@Table(name = "Artist")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ArtistID")
    private long artistID;
}
