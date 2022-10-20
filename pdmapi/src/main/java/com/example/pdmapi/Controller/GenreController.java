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

    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getGenres() {
        return new ResponseEntity<>(genreService.getGenres(), HttpStatus.OK);
    }

    @GetMapping("/genres/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable long id) {
        Optional<Genre> genre = genreService.getGenre(id);
        if (genre.isPresent()) {
            return new ResponseEntity<>(genre.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/genres", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Genre> createGenre(@RequestBody Genre newGenre) {
        Genre genre = genreService.createGenre(newGenre);

        if (genre == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(genre, HttpStatus.CREATED);
        }
    }

    @PutMapping(value = "/genres/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Genre> updateGenre(@PathVariable long id, @RequestBody Genre genreDetails) {
        Optional<Genre> genre = genreService.getGenre(id);
        if (genre.isPresent()) {
            return new ResponseEntity<>(genreService.updateGenre(id, genreDetails), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/genres/{id}")
    public ResponseEntity deleteGenre(@PathVariable long id) {
        genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
