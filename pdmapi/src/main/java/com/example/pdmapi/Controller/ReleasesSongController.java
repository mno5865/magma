package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.ReleasesSong;
import com.example.pdmapi.Model.Keys.SongArtistKey;
import com.example.pdmapi.Service.ReleasesSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReleasesSongController {

    @Autowired
    private ReleasesSongService releasesSongService;

    @GetMapping("/artists/{artistId}/songs")
    public ResponseEntity<List<ReleasesSong>> getArtistSongs(@PathVariable long artistId)
    {
        List<ReleasesSong> releasesSongs = releasesSongService.getArtistSongs(artistId);
        return new ResponseEntity<>(releasesSongs, HttpStatus.OK);
    }

    @PostMapping(value = "/artists/{artistId}/songs", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReleasesSong> addSongToArtist(@RequestBody SongArtistKey songArtistKey)
    {
        ReleasesSong releasesSong = new ReleasesSong();
        releasesSong.setId(songArtistKey);
        return new ResponseEntity<>(releasesSongService.addSong(releasesSong), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/artists/{artistId}/songs/{songId}")
    public ResponseEntity deleteSongFromArtist(@PathVariable long artistId, @PathVariable long songId)
    {
        SongArtistKey songArtistKey = new SongArtistKey();
        songArtistKey.setArtistId(artistId);
        songArtistKey.setSongId(songId);
        releasesSongService.deleteSongFromArtist(songArtistKey);
        return new ResponseEntity(HttpStatus.OK);
    }
}
