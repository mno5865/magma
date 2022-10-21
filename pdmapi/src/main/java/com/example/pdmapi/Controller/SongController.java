package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.Song;
import com.example.pdmapi.Service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SongController {
    @Autowired
    private SongService songService;

    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getSongs() {
        return new ResponseEntity<>(songService.getSongs(), HttpStatus.OK);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getSong(@PathVariable long id) {
        Optional<Song> song = songService.getSong(id);
        if (song.isPresent()) {
            return new ResponseEntity<>(song.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/songs", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Song> createSong(@RequestBody Song newSong) {
        Song song = songService.createSong(newSong);

        if (song == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(song, HttpStatus.CREATED);
        }
    }

    @PutMapping(value = "/songs/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Song> updateSong(@PathVariable long id, @RequestBody Song songDetails) {
        Optional<Song> song = songService.getSong(id);
        if (song.isPresent()) {
            return new ResponseEntity<>(songService.updateSong(id, songDetails), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity deleteSong(@PathVariable long id) {
        songService.deleteSong(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
