package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.Collection;
import com.example.pdmapi.Service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @GetMapping("/collections")
    public ResponseEntity<List<Collection>> getCollections() {
        return new ResponseEntity<>(collectionService.getCollections(), HttpStatus.OK);
    }

    @GetMapping("/collections/{id}")
    public ResponseEntity<Collection> getCollection(@PathVariable long id) {
        Collection collection = collectionService.getCollection(id);
        if (collection != null) {
            return new ResponseEntity<>(collection, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/collections", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection> createCollection(@RequestBody Collection newCollection) {
        int rowsAffected = collectionService.createCollection(newCollection);
        if (rowsAffected == -1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
    @PutMapping(value = "/collections/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection> updateCollection(@PathVariable long id, @RequestBody Collection collectionDetails) {
        int rowsAffected = collectionService.updateCollection(id, collectionDetails);
        if (rowsAffected != -1) {
            return new ResponseEntity<>( HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/collections/{id}")
    public ResponseEntity deleteCollection(@PathVariable long id) {
        int rowsAffected = collectionService.deleteCollection(id);
        if (rowsAffected != -1) {
            return new ResponseEntity<>( HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
