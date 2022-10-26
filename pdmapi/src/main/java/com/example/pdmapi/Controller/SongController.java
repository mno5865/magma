package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.Song;
import com.example.pdmapi.Service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SongController {
    @Autowired
    private SongService songService;

    @CrossOrigin
    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getSongs() {
        List<Song> songs = songService.getSongs();
        if(songs == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(songService.getSongs(), HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getSong(@PathVariable long id) {
        Song song = songService.getSong(id);
        if (song != null) {
            return new ResponseEntity<>(song, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/songs", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Song> createSong(@RequestBody Song newSong) {
        int rowsAffected = songService.createSong(newSong);
        if (rowsAffected == -1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @CrossOrigin
    @PutMapping(value = "/songs/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> updateSong(@PathVariable long id, @RequestBody Song songDetails) {
        int rowsAffected = songService.updateSong(id, songDetails);
        if (rowsAffected != -1) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @DeleteMapping("/songs/{id}")
    public ResponseEntity<Integer> deleteSong(@PathVariable long id) {
        int rowsAffected = songService.deleteSong(id);
        if(rowsAffected != -1) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
