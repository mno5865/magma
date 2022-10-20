package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.Artist;
import com.example.pdmapi.Service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @GetMapping("/artists")
    public ResponseEntity<List<Artist>> getArtists() {
        return new ResponseEntity<>(artistService.getArtists(), HttpStatus.OK);
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity<Artist> getArtist(@PathVariable long id) {
        Optional<Artist> artist = artistService.getArtist(id);
        if (artist.isPresent()) {
            return new ResponseEntity<>(artist.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/artists", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artist> createArtist(@RequestBody Artist newArtist) {
        Artist artist = artistService.createArtist(newArtist);

        if (artist == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(artist, HttpStatus.CREATED);
        }
    }

    @PutMapping(value = "/artists/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artist> updateArtist(@PathVariable long id, @RequestBody Artist artistDetails) {
        Optional<Artist> artist = artistService.getArtist(id);
        if (artist.isPresent()) {
            return new ResponseEntity<>(artistService.updateArtist(id, artistDetails), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/artists/{id}")
    public ResponseEntity deleteArtist(@PathVariable long id) {
        artistService.deleteArtist(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}