package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.Album;
import com.example.pdmapi.Service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        Optional<Album> album = albumService.getAlbum(id);
        if (album.isPresent()) {
            return new ResponseEntity<>(album.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/albums", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Album> createAlbum(@RequestBody Album newAlbum) {
        Album album = albumService.createAlbum(newAlbum);

        if (album == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(album, HttpStatus.CREATED);
        }
    }

    @PutMapping(value = "/albums/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Album> updateAlbum(@PathVariable long id, @RequestBody Album albumDetails) {
        Optional<Album> album = albumService.getAlbum(id);
        if (album.isPresent()) {
            return new ResponseEntity<>(albumService.updateAlbum(id, albumDetails), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/albums/{id}")
    public ResponseEntity deleteAlbum(@PathVariable long id) {
        albumService.deleteAlbum(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
