/**
 * file: SongController.java
 * authors: Gregory Ojiem gro3228,
 *          Melissa Burisky mpb8984,
 *          Mildness Onyekwere mno5865
 */
package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.Song;
import com.example.pdmapi.Model.SongInView;
import com.example.pdmapi.Service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * description: controller that creates the api
 * endpoint for accessing db data related to genre
 */
@RestController
@RequestMapping("/api")
public class SongController {

    /**
     * service that provides connection from endpoint to db
     */
    @Autowired
    private SongService songService;

    /**
     * endpoint for returning all songs in db
     * @return ResponseEntity OK for list of songs, even if empty
     */
    @CrossOrigin
    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getSongs() {
        List<Song> songs = songService.getSongs();
        if(songs == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(songService.getSongs(), HttpStatus.OK);
        }
    }

    /**
     * endpoint for returning singular song in db
     * @param id id of the artist in the artist table
     * @return ResponseEntity OK if the id given corresponds to a song
     *                        NOT_FOUND if it doesn't
     */
    @CrossOrigin
    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getSong(@PathVariable long id) {
        Song song = songService.getSong(id);
        if (song != null) {
            return new ResponseEntity<>(song, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * endpoint creates song using formatted json data
     * @param newSong the new artist resulting from the data
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *      *         if rows affected isnt one, obviously something is wrong
     */
    @CrossOrigin
    @PostMapping(value = "/songs", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Song> createSong(@RequestBody Song newSong) {
        int rowsAffected = songService.createSong(newSong);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    /**
     * endpoint that allows the update of any song details by using the given song id
     * @param id genre id
     * @param songDetails song deets
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isnt one, obviously something is wrong
     */
    @CrossOrigin
    @PutMapping(value = "/songs/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> updateSong(@PathVariable long id, @RequestBody Song songDetails) {
        int rowsAffected = songService.updateSong(id, songDetails);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * endpoint deletes song using formatted json data
     * @param id
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *          if rows affected isnt one, obviously something is wrong
     */
    @CrossOrigin
    @DeleteMapping("/songs/{id}")
    public ResponseEntity<Integer> deleteSong(@PathVariable long id) {
        int rowsAffected = songService.deleteSong(id);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * get all the songs in a collection given collection id
     * @param collectionId
     * @return ResponseEntity OK os a list of songs is not null
     *                          BAD_REQUEST otherwise
     */
    @CrossOrigin
    @GetMapping("/collections/{collectionId}/songs")
    public ResponseEntity<List<Song>> getCollectionsSongs(@PathVariable long collectionId) {
        List<Song> songs = songService.getCollectionSongs(collectionId);
        if(songs != null) {
            return new ResponseEntity<>(songs,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // song_view
    /**
     * endpoint that gets a specific song view by title if title of song contains the string
     * @param title
     * @param select
     * @param sort
     * @return ResponseEntity of the songs in a view
     */
    @CrossOrigin
    @GetMapping("/songs/bytitle/{title}/{select}/{sort}")
    public ResponseEntity<List<SongInView>> getSongsByTitle(@PathVariable String title,@PathVariable int select,
                                                            @PathVariable String sort) {
        // (1- song name), (2 - artist name), (3 - genre), (4 - release date) for select
        // ASC or DESC for sort
        title = title.replace('-', ' ');
        List<SongInView> songs = songService.getSongsByTitle(title,select,sort);
        if(songs == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(songs, HttpStatus.OK);
        }
    }


    /**
     * endpoint that gets a specific song view by artist if artist of song contains the string
     * @param name
     * @param select
     * @param sort
     * @return ResponseEntity of the songs in a view
     */
    @CrossOrigin
    @GetMapping("/songs/byartist/{name}/{select}/{sort}")
    public ResponseEntity<List<SongInView>> getSongsByArtist(@PathVariable String name, @PathVariable int select,
                                                             @PathVariable String sort) {
        name = name.replace('-', ' ');
        List<SongInView> songs = songService.getSongsByArtist(name,select,sort);
        if(songs == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(songs, HttpStatus.OK);
        }
    }


    /**
     * endpoint that gets a specific song view by album title if title of song contains the string
     * @param title
     * @param select
     * @param sort
     * @return ResponseEntity of the songs in a view
     */
    @CrossOrigin
    @GetMapping("/songs/byalbum/{title}/{select}/{sort}")
    public ResponseEntity<List<SongInView>> getSongsByAlbum(@PathVariable String title, @PathVariable int select,
                                                            @PathVariable String sort) {
        title = title.replace('-', ' ');
        List<SongInView> songs = songService.getSongsByAlbum(title,select,sort);
        if(songs == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(songs, HttpStatus.OK);
        }
    }

    /**
     * endpoint that gets a specific song view by genre if genre of song contains the string
     * @param genre
     * @param select
     * @param sort
     * @return ResponseEntity of the songs in a view
     */
    @CrossOrigin
    @GetMapping("/songs/bygenre/{genre}/{select}/{sort}")
    public ResponseEntity<List<SongInView>> getSongsByGenre(@PathVariable String genre,@PathVariable int select,
                                                            @PathVariable String sort) {
        genre = genre.replace('-', ' ');
        List<SongInView> songs = songService.getSongsByGenre(genre,select,sort);
        if(songs == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(songs, HttpStatus.OK);
        }
    }

    /**
     * endpoint that gets the 50 songs with the most listens in the past 30 days
     * @return ResponseEntity containing the list of top 50 songs
     */
    @CrossOrigin
    @GetMapping("/songs/top-50")
    public ResponseEntity<List<SongInView>> getTop50Songs() {
        List<SongInView> songs = songService.getTop50Songs();
        if (songs == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(songs, HttpStatus.OK);
        }
    }
}