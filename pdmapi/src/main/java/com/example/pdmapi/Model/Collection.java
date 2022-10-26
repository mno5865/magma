package com.example.pdmapi.Model;

import javax.persistence.*;

@Entity
@Table(name = "collection", schema = "p32001_08")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collection_id")
    private long collectionId;

    @Column(name = "title")
    private String title;

    public Collection() {
    }


    public void setCollectionID(long collectionId) {
        this.collectionId = collectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
