package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.HasSong;
import com.example.pdmapi.Model.Keys.SongGenreKey;
import com.example.pdmapi.Service.HasSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HasSongController {

    @Autowired
    private HasSongService hasSongService;

    @GetMapping("/songs/{songId}/genres")
    public ResponseEntity<List<HasSong>> getSongGenres(@PathVariable long songId)
    {
        List<HasSong> hasSongs = hasSongService.getSongGenres(songId);
        return new ResponseEntity<>(hasSongs, HttpStatus.OK);
    }

    @PostMapping(value = "/songs/{songId}/genres", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HasSong> addGenreToSong(@RequestBody SongGenreKey songGenreKey)
    {
        HasSong hasSong = new HasSong();
        hasSong.setId(songGenreKey);
        return new ResponseEntity<>(hasSongService.addGenre(hasSong), HttpStatus.CREATED);
        //error checking eventually
    }

    @DeleteMapping(value = "/songs/{songId}/genres/{genreId}")
    public ResponseEntity deleteGenreFromSong(@PathVariable long songId, @PathVariable long genreId)
    {
        SongGenreKey songGenreKey = new SongGenreKey();
        songGenreKey.setSongId(songId);
        songGenreKey.setGenreId(genreId);
        hasSongService.deleteGenreFromSong(songGenreKey);
        return new ResponseEntity(HttpStatus.OK);
    }

}
