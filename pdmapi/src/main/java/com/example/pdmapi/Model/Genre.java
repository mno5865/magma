package com.example.pdmapi.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genre", schema = "p32001_08")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private long genreId;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "genres")
    private List<Album> albums;

    public Genre() {
    }


    public void setGenreID(long genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }
}
