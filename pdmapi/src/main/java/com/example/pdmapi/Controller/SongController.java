package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.Song;
import com.example.pdmapi.Model.SongInView;
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

    @CrossOrigin
    @GetMapping("/collections/{collectionId}/songs")
    public ResponseEntity<List<Song>> getCollectionsSongs(@PathVariable long collectionId)
    {
        List<Song> songs = songService.getCollectionSongs(collectionId);
        if(songs != null)
        {
            return new ResponseEntity<>(songs,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // song_view
    @CrossOrigin
    @GetMapping("/songs/bytitle/{title}/{select}/{sort}")
    public ResponseEntity<List<SongInView>> getSongsByTitle(@PathVariable String title,@PathVariable int select,
                                                            @PathVariable String sort) {
        // (1- song name), (2 - artist name), (3 - genre), (4 - release date) for select
        // ASC or DESC for sort
        title = title.replace('-', ' ');
        List<SongInView> songs = songService.getSongsByTitle(title,select,sort);
        if(songs == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(songs, HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping("/songs/byartist/{name}/{select}/{sort}")
    public ResponseEntity<List<SongInView>> getSongsByArtist(@PathVariable String name,@PathVariable int select,
                                                             @PathVariable String sort) {
        name = name.replace('-', ' ');
        List<SongInView> songs = songService.getSongsByArtist(name,select,sort);
        if(songs == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(songs, HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping("/songs/byalbum/{title}")
    public ResponseEntity<List<SongInView>> getSongsByAlbum(@PathVariable String title) {
        title = title.replace('-', ' ');
        List<SongInView> songs = songService.getSongsByAlbum(title);
        if(songs == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(songs, HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping("/songs/bygenre/{genre}")
    public ResponseEntity<List<SongInView>> getSongsByGenre(@PathVariable String genre) {
        genre = genre.replace('-', ' ');
        List<SongInView> songs = songService.getSongsByGenre(genre);
        if(songs == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(songs, HttpStatus.OK);
        }
    }
}