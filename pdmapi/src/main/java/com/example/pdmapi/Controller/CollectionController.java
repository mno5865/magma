package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.Collection;
import com.example.pdmapi.Service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @CrossOrigin
    @GetMapping("/collections")
    public ResponseEntity<List<Collection>> getCollections() {
        return new ResponseEntity<>(collectionService.getCollections(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/collections/{id}")
    public ResponseEntity<Collection> getCollection(@PathVariable long id) {
        Collection collection = collectionService.getCollection(id);
        if (collection != null) {
            return new ResponseEntity<>(collection, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/collections", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> createCollection(@RequestBody Collection newCollection) {
        int rowsAffected = collectionService.createCollection(newCollection);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PutMapping(value = "/collections/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> updateCollection(@PathVariable long id, @RequestBody Collection collectionDetails) {
        int rowsAffected = collectionService.updateCollection(id, collectionDetails);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @DeleteMapping("/collections/{id}")
    public ResponseEntity<Integer> deleteCollection(@PathVariable long id) {
        int rowsAffected = collectionService.deleteCollection(id);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    //CollectionHoldsSong RELATIONSHIP
    @CrossOrigin
    @PostMapping(value = "/collections/{collectionId}/songs/{songId}")
    public ResponseEntity<Integer> createCollection(@PathVariable long collectionId, @PathVariable long songId) {
        int rowsAffected = collectionService.createCollectionHoldsSong(collectionId, songId);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @DeleteMapping("/collections/{collectionId}/songs/{songId}")
    public ResponseEntity<Integer> deleteCollection(@PathVariable long collectionId, @PathVariable long songId) {
        int rowsAffected = collectionService.deleteCollectionHoldsSong(collectionId, songId);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }
}
