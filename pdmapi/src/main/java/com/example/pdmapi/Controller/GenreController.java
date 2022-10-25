package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.Genre;
import com.example.pdmapi.Service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping("/genres/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable long id) {
        Genre genre = genreService.getGenre(id);
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getGenres() {
        return new ResponseEntity<>(genreService.getGenres(), HttpStatus.OK);
    }

    @PostMapping(value = "/genres", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createGenre(@RequestBody Genre newGenre) {
        int rowsAffected = genreService.createGenre(newGenre);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/genres/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateGenre(@PathVariable long id, @RequestBody Genre genreDetails) {
        int rowsAffected = genreService.updateGenre(id, genreDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/genres/{id}")
    public ResponseEntity deleteGenre(@PathVariable long id) {
        int rowsAffected = genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
