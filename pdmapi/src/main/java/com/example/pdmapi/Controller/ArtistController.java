package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.Artist;
import com.example.pdmapi.Model.Song;
import com.example.pdmapi.Service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @CrossOrigin
    @GetMapping("/artists")
    public ResponseEntity<List<Artist>> getArtists() {
        return new ResponseEntity<>(artistService.getArtists(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/artists/{id}")
    public ResponseEntity<Artist> getArtist(@PathVariable long id) {
        Artist artist = artistService.getArtist(id);
        if (artist != null) {
            return new ResponseEntity<>(artist, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @GetMapping("/artists/{artistId}/songs")
    public ResponseEntity<List<Song>> getArtistSongs(@PathVariable long artistId)
    {
        List<Song> songs = artistService.getSongsByArtist(artistId);
        if(songs != null)
        {
            return new ResponseEntity<>(songs, HttpStatus.OK);
        } else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/artists/{artistId}/songs/{songId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> createArtistReleasesSong(@PathVariable long artistId, @PathVariable long songId )
    {
        int rowsAffected = artistService.createArtistReleasesSong(artistId,songId);
        if(rowsAffected == 1)
        {
            return new ResponseEntity<>(rowsAffected,HttpStatus.CREATED);
        }
        else
        {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/artists", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> createArtist(@RequestBody Artist newArtist) {
        int rowsAffected = artistService.createArtist(newArtist);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PutMapping(value = "/artists/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> updateArtist(@PathVariable long id, @RequestBody Artist artistDetails) {
        int rowsAffected = artistService.updateArtist(id, artistDetails);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @DeleteMapping("/artists/{id}")
    public ResponseEntity<Integer> deleteArtist(@PathVariable long id) {
        int rowsAffected = artistService.deleteArtist(id);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }
    
    @CrossOrigin
    @DeleteMapping("/artists/{artistId}/songs/{songId}")
    public ResponseEntity<Integer> deleteArtistReleaseSong(@PathVariable long artistId, @PathVariable long songId)
    {
        int rowsAffected = artistService.deleteArtistReleaseSong(songId,artistId);
        if(rowsAffected == 1)
        {
            return new ResponseEntity<>(rowsAffected,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }
}