/**
 * file: GenreController.java
 * authors: Gregory Ojiem gro3228,
 *          Melissa Burisky mpb8984,
 *          Mildness Onyekwere mno5865,
 *          Adrian Burgos awb8593
 */

package com.example.pdmapi.Controller;

//imports
import com.example.pdmapi.Model.Genre;
import com.example.pdmapi.Model.Song;
import com.example.pdmapi.Model.SongInView;
import com.example.pdmapi.Service.GenreService;
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
public class GenreController {

    /**
     * service that provides connection from endpoint to db
     */
    @Autowired
    private GenreService genreService;

    /**
     * endpoint for returning all genres in db
     * @return ResponseEntity OK for list of genres, even if empty
     */
    @CrossOrigin
    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getGenres() {
        return new ResponseEntity<>(genreService.getGenres(), HttpStatus.OK);
    }

    /**
     * endpoint for returning singular artist in db
     * @param id id of the artist in the artist table
     * @return ResponseEntity OK if the id given corresponds to an artist
     *                        NOT_FOUND if it doesn't
     */
    @CrossOrigin
    @GetMapping("/genres/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable long id) {
        Genre genre = genreService.getGenre(id);
        if (genre != null) {
            return new ResponseEntity<>(genre, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * endpoint for getting the songs that have a genre
     * @param genreId of the genre in the genre table
     * @return ResponseEntity OK if songs list is not null
     *                        NOT_FOUND if not found
     */
    @CrossOrigin
    @GetMapping("/genres/{genreId}/songs")
    public ResponseEntity<List<Song>> getSongsByGenre(@PathVariable long genreId) {
        List<Song> songs = genreService.getSongsByGenre(genreId);
        if(songs != null){
            return new ResponseEntity<>(songs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * endpoint for getting the albums that have a genre
     * @param genreId of the genre in the genre table
     * @return ResponseEntity OK if albums list is not null
     *                        NOT_FOUND if not found
     */
    @CrossOrigin
    @GetMapping("/genres/{genreId}/albums")
    public ResponseEntity<List<Song>> getAlbumsByGenre(@PathVariable long genreId) {
        List<Song> songs = genreService.getSongsByGenre(genreId);
        if(songs != null){
            return new ResponseEntity<>(songs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * endpoint creates genre using formatted json data
     * @param newGenre the new artist resulting from the data
     * @return ResponseEntity CREATED with the correctly formatted data
     *                        BAD_REQUEST if something fails
     */
    @CrossOrigin
    @PostMapping(value = "/genres", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> createGenre(@RequestBody Genre newGenre) {
        int[] results = genreService.createGenre(newGenre);
        if (results[0] == 1 && results[1] != 0) {
            return new ResponseEntity<>(results[1], HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(results[1], HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * endpoint that creates song has genre relationship between song and genre
     * @param genreId genre
     * @param songId song
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isn't one, obviously something is wrong
     */
    @CrossOrigin
    @PostMapping(value = "/genres/{genreId}/songs/{songId}")
    public ResponseEntity<Integer> createSongHasGenre(@PathVariable long genreId, @PathVariable long songId) {
        int rowsAffected = genreService.createSongHasGenre(genreId, songId);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * endpoint that creates album has genre relationship between album and genre
     * @param genreId genre
     * @param albumId song
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isn't one, obviously something is wrong
     */
    @CrossOrigin
    @PostMapping(value = "/genres/{genreId}/albums/{albumId}")
    public ResponseEntity<Integer> createAlbumHasGenre(@PathVariable long genreId, @PathVariable long albumId) {
        int rowsAffected = genreService.createAlbumHasGenre(genreId, albumId);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * endpoint that allows the update of any genre details by using the given genre id
     * @param id genre id
     * @param genreDetails genre deets
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isn't one, obviously something is wrong
     */
    @CrossOrigin
    @PutMapping(value = "/genres/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> updateGenre(@PathVariable long id, @RequestBody Genre genreDetails) {
        int rowsAffected = genreService.updateGenre(id, genreDetails);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * endpoint that deletes genre from db
     * @param id genre id
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isn't one, obviously something is wrong
     */
    @CrossOrigin
    @DeleteMapping("/genres/{id}")
    public ResponseEntity<Integer> deleteGenre(@PathVariable long id) {
        int rowsAffected = genreService.deleteGenre(id);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * endpoint that deletes song has genre relationship between song and genre
     * @param genreId genre
     * @param songId song
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isn't one, obviously something is wrong
     */
    @CrossOrigin
    @DeleteMapping("/genres/{genreId}/songs/{songId}")
    public ResponseEntity<Integer> deleteSongHasGenre(@PathVariable long genreId, @PathVariable long songId) {
        int rowsAffected = genreService.deleteSongHasGenre(songId, genreId);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * endpoint that deletes has genre relationship between album and genre
     * @param genreId genre
     * @param albumId song
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isn't one, obviously something is wrong
     */
    @CrossOrigin
    @DeleteMapping("/genres/{genreId}/albums/{albumId}")
    public ResponseEntity<Integer> deleteAlbumHasGenre(@PathVariable long genreId, @PathVariable long albumId) {
        int rowsAffected = genreService.deleteAlbumHasGenre(albumId, genreId);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * endpoint that gets the 5 genres that were played since the first of the month
     * @return ResponseEntity containing the list of top 5 genres
     */
    @CrossOrigin
    @GetMapping("/genres/top-5")
    public ResponseEntity<List<Genre>> getTop5Genres() {
        List<Genre> genres = genreService.getTop5Genres();
        if (genres == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(genres, HttpStatus.OK);
        }
    }
}
