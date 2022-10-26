package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.Genre;
import com.example.pdmapi.Model.Song;
import com.example.pdmapi.Service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @CrossOrigin
    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getGenres() {
        return new ResponseEntity<>(genreService.getGenres(), HttpStatus.OK);
    }

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

    @CrossOrigin
    @PostMapping(value = "/genres", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> createGenre(@RequestBody Genre newGenre) {
        int rowsAffected = genreService.createGenre(newGenre);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/genres/{genreId}/songs/{songId}")
    public ResponseEntity createSongHasGenre(@PathVariable long genreId, @PathVariable long songId) {
        int rowsAffected = genreService.createSongHasGenre(genreId, songId);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

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

    @CrossOrigin
    @DeleteMapping("/genres/{genreId}/songs/{songId}")
    public ResponseEntity<Integer> deleteAlbumContainsSong(@PathVariable long genreId, @PathVariable long songId) {
        int rowsAffected = genreService.deleteSongHasGenre(songId, genreId);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }
}
