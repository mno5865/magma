/**
 * file: ArtistController.java
 * authors: Gregory Ojiem gro3228,
 *          Melissa Burisky mpb8984,
 *          Mildness Onyekwere mno5865
 */
package com.example.pdmapi.Controller;

//imports
import com.example.pdmapi.Model.Album;
import com.example.pdmapi.Model.Artist;
import com.example.pdmapi.Model.Song;
import com.example.pdmapi.Service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * description: controller that creates the api endpoint
 *              for accessing db data related to artist
 */
@RestController
@RequestMapping("/api")
public class ArtistController {

    /**
     * service that provides connection from endpoint to db
     */
    @Autowired
    private ArtistService artistService;


    /**
     * endpoint for returning all artists in db
     * @return ResponseEntity OK for list of albums, even if empty
     */
    @GetMapping("/artists")
    public ResponseEntity<List<Artist>> getArtists() {
        return new ResponseEntity<>(artistService.getArtists(), HttpStatus.OK);
    }

    /**
     * endpoint for returning singular artist in db
     * @param id id of the artist in the artist table
     * @return ResponseEntity OK if the id given corresponds to an artist
     *                        NOT_FOUND if it doesn't
     */
    @GetMapping("/artists/{id}")
    public ResponseEntity<Artist> getArtist(@PathVariable long id) {
        Artist artist = artistService.getArtist(id);
        if (artist != null) {
            return new ResponseEntity<>(artist, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * endpoint creates artist using formatted json data
     * @param newArtist the new artist resulting from the data
     * @return ResponseEntity CREATED with the correctly formatted data
     *                        BAD_REQUEST if something fails
     */
    @PostMapping(value = "/artists", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> createArtist(@RequestBody Artist newArtist) {
        int[] results = artistService.createArtist(newArtist);
        if (results[0] == 1 && results[1] != 0) {
            return new ResponseEntity<>(results[1], HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(results[1], HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * endpoint that allows the update of any artist details by using the given artist id
     * @param id artist id
     * @param artistDetails artist deets
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isn't one, obviously something is wrong
     *
     */
    @PutMapping(value = "/artists/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> updateArtist(@PathVariable long id, @RequestBody Artist artistDetails) {
        int rowsAffected = artistService.updateArtist(id, artistDetails);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * endpoint that deletes artist from db
     * @param id artist id
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isn't one, obviously something is wrong
     */
    @DeleteMapping("/artists/{id}")
    public ResponseEntity<Integer> deleteArtist(@PathVariable long id) {
        int rowsAffected = artistService.deleteArtist(id);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    // artists_releases_album RELATIONSHIP
    /**
     * endpoint that creates artist_releases_album relationship between artist and album
     * @param artistId artist
     * @param albumId album
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isn't one, obviously something is wrong
     */
    @PostMapping(value = "/artists/{artistId}/albums/{albumId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> createArtistReleasesAlbum(@PathVariable long artistId, @PathVariable long albumId) {
        int rowsAffected = artistService.createArtistReleasesAlbum(artistId,albumId);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected,HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * endpoint for getting the albums an artist releases
     * @param artistId of the album in the album table
     * @return ResponseEntity OK if any albums are found
     *                        NOT_FOUND if not found
     */
    @GetMapping("/artists/{artistId}/albums")
    public ResponseEntity<List<Album>> getArtistAlbums(@PathVariable long artistId) {
        List<Album> albums = artistService.getAlbumsByArtist(artistId);
        if(albums != null) {
            return new ResponseEntity<>(albums, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * endpoint that deletes artist album relationship from db
     * @param artistId artist id
     * @param albumId album id
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isn't one, obviously something is wrong
     */
    @DeleteMapping("/artists/{artistId}/albums/{albumId}")
    public ResponseEntity<Integer> deleteArtistReleaseAlbum(@PathVariable long artistId, @PathVariable long albumId) {
        int rowsAffected = artistService.deleteArtistReleaseAlbum(albumId,artistId);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    // artists_releases_song RELATIONSHIP
    /**
     * endpoint that creates artist_releases_song relationship between artist and album
     * @param artistId artist
     * @param songId song
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isn't one, obviously something is wrong
     */
    @PostMapping(value = "/artists/{artistId}/songs/{songId}")
    public ResponseEntity<Integer> createArtistReleasesSong(@PathVariable long artistId, @PathVariable long songId ) {
        int rowsAffected = artistService.createArtistReleasesSong(artistId,songId);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected,HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * endpoint for getting the songs an artist releases
     * @param artistId artist id
     * @return ResponseEntity OK if any albums are found
     *                        NOT_FOUND if not found
     */
    @GetMapping("/artists/{artistId}/songs")
    public ResponseEntity<List<Song>> getArtistSongs(@PathVariable long artistId) {
        List<Song> songs = artistService.getSongsByArtist(artistId);
        if(songs != null) {
            return new ResponseEntity<>(songs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * endpoint that deletes artist song relationship from db
     * @param artistId artist id
     * @param songId song id
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     *         if rows affected isn't one, obviously something is wrong
     */
    @DeleteMapping("/artists/{artistId}/songs/{songId}")
    public ResponseEntity<Integer> deleteArtistReleaseSong(@PathVariable long artistId, @PathVariable long songId) {
        int rowsAffected = artistService.deleteArtistReleaseSong(songId,artistId);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }
}