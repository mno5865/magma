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

    //deprecated
    @CrossOrigin
    @GetMapping("/users/{userID}/collections/{collectionName}")
    public ResponseEntity<Collection> getCollectionByTitleAndUserID(@PathVariable long userID,
                                                                  @PathVariable String collectionName) {
        collectionName = collectionName.replace('-', ' ');
        Collection collection = collectionService.getCollectionByTitleAndUserID(userID, collectionName);
        if (collection != null) {
            return new ResponseEntity<>(collection, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @GetMapping("/users/{userID}/collections/id/{collectionID}")
    public ResponseEntity<Collection> getCollectionByCollectionAndUserID(@PathVariable long userID,
                                                                    @PathVariable long collectionID) {
        Collection collection = collectionService.getCollectionByCollectionAndUserID(userID, collectionID);
        if (collection != null) {
            return new ResponseEntity<>(collection, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/collections", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> createCollection(@RequestBody Collection newCollection) {
        int[] results = collectionService.createCollection(newCollection);
        if (results[0] == 1 && results[1] != 0) {
            return new ResponseEntity<>(results[1], HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(results[1], HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<Integer> createCollectionHoldsSong(@PathVariable long collectionId, @PathVariable long songId) {
        int rowsAffected = collectionService.createCollectionHoldsSong(collectionId, songId);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @DeleteMapping("/collections/{collectionId}/songs/{songId}")
    public ResponseEntity<Integer> deleteCollectionHoldsSong(@PathVariable long collectionId, @PathVariable long songId) {
        int rowsAffected = collectionService.deleteCollectionHoldsSong(collectionId, songId);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    //CollectionHoldsAlbum RELATIONSHIP
    @CrossOrigin
    @PostMapping(value = "/collections/{collectionId}/albums/{albumId}")
    public ResponseEntity<Integer> createCollectionHoldsAlbum(@PathVariable long collectionId, @PathVariable long albumId) {
        int rowsAffected = collectionService.createCollectionHoldsAlbum(collectionId, albumId);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @DeleteMapping("/collections/{collectionId}/albums/{albumId}")
    public ResponseEntity<Integer> deleteCollectionHoldsAlbum(@PathVariable long collectionId, @PathVariable long albumId) {
        int rowsAffected = collectionService.deleteCollectionHoldsAlbum(collectionId, albumId);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/collections/{collection_id}/song_count")
    public ResponseEntity<Integer> getSongCountFromCollection(@PathVariable long collection_id)
    {
        int count = collectionService.getSongCountFromCollection(collection_id);
        if(count != -1)
        {
            return new ResponseEntity<>(count,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(count,HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/collections/{collection_id}/total_duration")
    public ResponseEntity<Integer> getTotalDurationFromCollection(@PathVariable long collection_id)
    {
        int time = collectionService.getTotalCollectionRuntime(collection_id);
        if(time != -1)
        {
            return new ResponseEntity<>(time,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(time,HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @DeleteMapping("/collections/{id}/deleteAll")
    public ResponseEntity<Integer> deleteAllCollectionRelations(@PathVariable long id)
    {
        int check = collectionService.deleteAll(id);
        if(check != -1)
        {
            return new ResponseEntity<>(check,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(check,HttpStatus.BAD_REQUEST);
        }
    }
}
