package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.Album;
import com.example.pdmapi.Model.Song;
import com.example.pdmapi.Service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @GetMapping("/albums")
    public ResponseEntity<List<Album>> getAlbums() {
        return new ResponseEntity<>(albumService.getAlbums(), HttpStatus.OK);
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<Album> getAlbum(@PathVariable long id) {
        Album album = albumService.getAlbum(id);
        if (album != null) {
            return new ResponseEntity<>(album, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/albums/{albumId}/songs")
    public ResponseEntity<List<Song>> getAlbumSongs(@PathVariable long albumId) {
        List<Song> songs = albumService.getSongsByAlbum(albumId);
        if(songs != null){
            return new ResponseEntity<>(songs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/albums/{albumId}/songs/{trackNumber}")
    public ResponseEntity<Song> getAlbumSong(@PathVariable long albumId, @PathVariable int trackNumber) {
        Song song = albumService.getSongInAlbumByTrackNumber(albumId, trackNumber);
        if(song != null){
            return new ResponseEntity<>(song, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/albums", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> createAlbum(@RequestBody Album newAlbum) {
        int rowsAffected = albumService.createAlbum(newAlbum);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/albums/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> updateAlbum(@PathVariable long id, @RequestBody Album albumDetails) {
        int rowsAffected = albumService.updateAlbum(id, albumDetails);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/albums/{id}")
    public ResponseEntity<Integer> deleteAlbum(@PathVariable long id) {
        int rowsAffected = albumService.deleteAlbum(id);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }
}
