/**
 * file: AlbumController.java
 * authors:  Gregory, Melissa, Mildness
 */
package com.example.pdmapi.Controller;

//imports
import com.example.pdmapi.Model.Album;
import com.example.pdmapi.Model.Song;
import com.example.pdmapi.Service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * description: controller that creates the api endpoint
 *              for accessing db data related to album
 */
@RestController
@RequestMapping("/api")
public class AlbumController {

    /**
     * service that provides connection from endpoint to db
     */
    @Autowired
    private AlbumService albumService;

    /**
     * endpoint for returning all albums in db
     * @return ResponseEntity OK for list of albums, even if empty
     */
    @CrossOrigin
    @GetMapping("/albums")
    public ResponseEntity<List<Album>> getAlbums() {
        return new ResponseEntity<>(albumService.getAlbums(), HttpStatus.OK);
    }

    /**
     * endpoint for returning singular album in db
     * @param id id of the album in the album table
     * @return ResponseEntity OK if the id given corresponds to an album
     *                        NOT_FOUND if it doesn't
     */
    @CrossOrigin
    @GetMapping("/albums/{id}")
    public ResponseEntity<Album> getAlbum(@PathVariable long id) {
        Album album = albumService.getAlbum(id);
        if (album != null) {
            return new ResponseEntity<>(album, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * endpoint for returning all songs in a given album
     * @param albumId of the album in the album table
     * @return ResponseEntity OK if a not null song list is found
     *                        NOT_FOUND if it doesn't find a list of songs
     */
    @CrossOrigin
    @GetMapping("/albums/{albumId}/songs")
    public ResponseEntity<List<Song>> getAlbumSongs(@PathVariable long albumId) {
        List<Song> songs = albumService.getSongsByAlbum(albumId);
        if(songs != null){
            return new ResponseEntity<>(songs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * endpoint for getting a song on an album by track number
     * @param albumId of the album in the album table
     * @param trackNumber of the specific song in the specific album
     * @return ResponseEntity OK if a song is found
     *                        NOT_FOUND if not found
     */
    @CrossOrigin
    @GetMapping("/albums/{albumId}/songs/{trackNumber}")
    public ResponseEntity<Song> getAlbumSong(@PathVariable long albumId, @PathVariable int trackNumber) {
        Song song = albumService.getSongInAlbumByTrackNumber(albumId, trackNumber);
        if(song != null){
            return new ResponseEntity<>(song, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * creates album using formatted json data
     * @param newAlbum the new album resulting from the data
     * @return ResponseEntity CREATED with the correctly formatted data
     *                        BAD_REQUEST if something fails
     */
    @CrossOrigin
    @PostMapping(value = "/albums", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> createAlbum(@RequestBody Album newAlbum) {
        int rowsAffected = albumService.createAlbum(newAlbum);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * creates album_contains_song relationship between album in song
     * @param albumId album
     * @param songId song
     * @return ResponseEntity<Integer> of the number of rows in db affected by the service request
     */
    @CrossOrigin
    @PostMapping(value = "/albums/{albumId}/songs/create/{songId}/{track}")
    public ResponseEntity<Integer> createAlbumContainsSong(@PathVariable long albumId,
                                                           @PathVariable long songId, @PathVariable int track) {
        int rowsAffected = albumService.createAlbumContainsSong(albumId, songId, track);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PutMapping(value = "/albums/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> updateAlbum(@PathVariable long id, @RequestBody Album albumDetails) {
        int rowsAffected = albumService.updateAlbum(id, albumDetails);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    //TODO FIX
    @PutMapping(value = "/albums/{albumId}/song?={songId}/?={trackNumber}")
    public ResponseEntity<Integer> updateSongTrackNumberInAlbum
            (@PathVariable long albumId, @PathVariable long songId, @PathVariable int trackNumber) {
        int rowsAffected = albumService.updateSongTrackNumberInAlbum(albumId, songId, trackNumber);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @CrossOrigin
    @DeleteMapping("/albums/{id}")
    public ResponseEntity<Integer> deleteAlbum(@PathVariable long id) {
        int rowsAffected = albumService.deleteAlbum(id);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @DeleteMapping("/albums/{albumId}/song?={songId}")
    public ResponseEntity<Integer> deleteAlbumContainsSong(@PathVariable long albumId, @PathVariable long songId) {
        int rowsAffected = albumService.deleteAlbumContainsSong(albumId, songId);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @GetMapping("/collections/{collectionId}/albums")
    public ResponseEntity<List<Album>> getCollectionAlbums(@PathVariable long collectionId)
    {
        List<Album> albums = albumService.getCollectionAlbums(collectionId);
        if(albums != null)
        {
            return new ResponseEntity<>(albums,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/albums/{album_id}/total_duration")
    public ResponseEntity<Integer> getTotalRuntimeOfAlbum(@PathVariable long album_id)
    {
        int total_duration = albumService.getTotalRuntimeOfAlbum(album_id);
        if(total_duration != -1)
        {
            return new ResponseEntity<>(total_duration,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
