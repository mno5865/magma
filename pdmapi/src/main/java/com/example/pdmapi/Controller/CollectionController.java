/**
 * file: CollectionController.java
 * authors: Gregory Ojiem gro3228,
 *          Melissa Burisky mpb8984,
 *          Mildness Onyekwere mno5865
 */
package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.Collection;
import com.example.pdmapi.Service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * description: controller that creates the api endpoint for accessing db data related to collection
 */
@RestController
@RequestMapping("/api")
public class CollectionController {

    /**
     * service that provides connection from endpoint to db
     */
    @Autowired
    private CollectionService collectionService;

    /**
     * endpoint for returning all collections in db
     * @return ResponseEntity OK for list of albums, even if empty
     */
    @CrossOrigin
    @GetMapping("/collections")
    public ResponseEntity<List<Collection>> getCollections() {
        return new ResponseEntity<>(collectionService.getCollections(), HttpStatus.OK);
    }

    /**
     * endpoint for returning singular collection in db
     * @param id id of the collection in the collection table
     * @return ResponseEntity OK if the id given corresponds to an collection
     *                        NOT_FOUND if it doesn't
     */
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

    /**
     * endpoint creates collection using formatted json data
     * @param newCollection the new album resulting from the data
     * @return ResponseEntity CREATED with the correctly formatted data
     *                        BAD_REQUEST if something fails
     */
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

    /**
     * endpoint that allows the update of collection details by using the given collection id
     * @param id collection id
     * @param collectionDetails collection deets
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isn't one, obviously something is wrong
     */
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

    /**
     * endpoint that deletes collection from db
     * @param id album id
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isn't one, obviously something is wrong
     */
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
    /**
     * endpoint that creates collection song relationship between collection in song
     * @param collectionId collection
     * @param songId song
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isn't one, obviously something is wrong
     */
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

    /**
     * endpoint that deletes collection song relationship from db given collection and song ids
     * @param collectionId collection id
     * @param songId song id
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isn't one, obviously something is wrong
     */
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
    /**
     * endpoint that creates collection album relationship between collection in album
     * @param collectionId collection
     * @param albumId album
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isn't one, obviously something is wrong
     */
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

    /**
     * endpoint that deletes collection album relationship from db given collection and album ids
     * @param collectionId collection id
     * @param albumId album id
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isn't one, obviously something is wrong
     */
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

    /**
     * endpoint that gets the song count of a collection
     * @param collection_id collection id
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *          if count is -1 obviously something is wrong
     */
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


    /**
     * endpoint that gets the total runtime of a collection
     * @param collection_id collection id
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *          if total_duration is -1, obviously something is wrong
     */
    @CrossOrigin
    @GetMapping(value = "/collections/{collection_id}/total_duration")
    public ResponseEntity<Integer> getTotalDurationFromCollection(@PathVariable long collection_id)
    {
        int time = collectionService.getTotalCollectionRuntime(collection_id);
        if(time != -1) {
            return new ResponseEntity<>(time,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(time,HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * endpoint that deletes all songs in a collection from db
     * @param id collection id
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if check is -1 something is wrong
     */
    @CrossOrigin
    @DeleteMapping("/collections/{id}/deleteAll")
    public ResponseEntity<Integer> deleteAllCollectionRelations(@PathVariable long id) {
        int check = collectionService.deleteAllSongs(id);
        if(check != -1) {
            return new ResponseEntity<>(check,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(check,HttpStatus.BAD_REQUEST);
        }
    }
}
