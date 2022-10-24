package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.AlbumContainsSong;
import com.example.pdmapi.Model.Keys.SongAlbumKey;
import com.example.pdmapi.Service.AlbumContainsSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AlbumContainsSongController {

    @Autowired
    private AlbumContainsSongService albumContainsSongService;

    @GetMapping("/albums/{albumId}/songs")
    public ResponseEntity<List<AlbumContainsSong>> getAlbumSongs(@PathVariable long albumId) {
        List<AlbumContainsSong> albumContainsSongs = albumContainsSongService.getAlbumSongs(albumId);
        return new ResponseEntity<>(albumContainsSongs, HttpStatus.OK);
    }

    @PostMapping(value = "/albums/{albumId}/songs", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlbumContainsSong> addSongToAlbum(@RequestBody SongAlbumKey songAlbumKey) {
        AlbumContainsSong albumContainsSong = new AlbumContainsSong();
        albumContainsSong.setId(songAlbumKey);
        return new ResponseEntity<>(albumContainsSongService.addSong(albumContainsSong), HttpStatus.CREATED);
        //add errors
        //if (albumContainsSong == null) {
        //    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        //} else {
        //    return new ResponseEntity<>(albumContainsSong, HttpStatus.CREATED);
        //}
    }

    @DeleteMapping(value = "/albums/{albumId}/songs/{songId}")
    public ResponseEntity deleteSongFromAlbum(@PathVariable long albumId, @PathVariable long songId) {
        SongAlbumKey songAlbumKey = new SongAlbumKey();
        songAlbumKey.setAlbumId(albumId);
        songAlbumKey.setSongId(songId);
        albumContainsSongService.deleteSongFromAlbum(songAlbumKey);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
