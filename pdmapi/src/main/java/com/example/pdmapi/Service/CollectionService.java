package com.example.pdmapi.Service;

import com.example.pdmapi.Model.Collection;
import com.example.pdmapi.Repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {

    @Autowired
    CollectionRepository collectionRepository;

    // CREATE
    public Collection createCollection(Collection collection) {
        return collectionRepository.save(collection);
    }

    // READ
    public List<Collection> getCollections() {
        return collectionRepository.findAll();
    }

    public Optional<Collection> getCollection(Long collectionId) {
        return collectionRepository.findById(collectionId);
    }

    // UPDATE
    public Collection updateCollection(Long collectionId, Collection collectionDetails) {
        Collection collection = collectionRepository.findById(collectionId).get();

        collection.setCollectionID(collectionId);
        collection.setTitle(collectionDetails.getTitle());

        return collectionRepository.save(collection);
    }

    // DELETE
    public void deleteCollection(Long collectionId) {
        collectionRepository.deleteById(collectionId);
    }
}