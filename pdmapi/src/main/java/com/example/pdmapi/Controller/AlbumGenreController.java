package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.AlbumGenre;
import com.example.pdmapi.Model.Keys.AlbumGenreKey;
import com.example.pdmapi.Service.AlbumGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AlbumGenreController {

    @Autowired
    private AlbumGenreService albumContainsGenreService;

    @GetMapping("/albums/{albumId}/genres")
    public ResponseEntity<List<AlbumGenre>> getAlbumGenres(@PathVariable long albumId) {
        List<AlbumGenre> albumContainsGenres = albumContainsGenreService.getAlbumGenres(albumId);
        return new ResponseEntity<>(albumContainsGenres, HttpStatus.OK);
    }

    @PostMapping(value = "/albums/{albumId}/genres", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlbumGenre> addGenreToAlbum(@RequestBody AlbumGenreKey albumGenreKey) {
        AlbumGenre albumContainsGenre = new AlbumGenre();
        albumContainsGenre.setId(albumGenreKey);
        return new ResponseEntity<>(albumContainsGenreService.addGenre(albumContainsGenre), HttpStatus.CREATED);
        //add errors
        //if (albumContainsGenre == null) {
        //    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        //} else {
        //    return new ResponseEntity<>(albumContainsGenre, HttpStatus.CREATED);
        //}
    }

    @DeleteMapping(value = "/albums/{albumId}/genres/{songId}")
    public ResponseEntity deleteGenreFromAlbum(@PathVariable long albumId, @PathVariable long songId) {
        AlbumGenreKey albumGenreKey = new AlbumGenreKey();
        albumGenreKey.setAlbumId(albumId);
        albumGenreKey.setGenreId(songId);
        albumContainsGenreService.deleteGenreFromAlbum(albumGenreKey);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
