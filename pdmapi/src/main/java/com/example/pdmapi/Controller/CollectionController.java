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
        Optional<Collection> collection = collectionService.getCollection(id);
        if (collection.isPresent()) {
            return new ResponseEntity<>(collection.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/collections", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection> createCollection(@RequestBody Collection newCollection) {
        Collection collection = collectionService.createCollection(newCollection);

        if (collection == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(collection, HttpStatus.CREATED);
        }
    }
    @PutMapping(value = "/collections/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection> updateCollection(@PathVariable long id, @RequestBody Collection collectionDetails) {
        Optional<Collection> collection = collectionService.getCollection(id);
        if (collection.isPresent()) {
            return new ResponseEntity<>(collectionService.updateCollection(id, collectionDetails), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/collections/{id}")
    public ResponseEntity deleteCollection(@PathVariable long id) {
        collectionService.deleteCollection(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
