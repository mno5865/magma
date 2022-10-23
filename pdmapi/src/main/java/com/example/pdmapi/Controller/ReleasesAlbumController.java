package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.ReleasesAlbum;
import com.example.pdmapi.Model.Keys.AlbumArtistKey;
import com.example.pdmapi.Service.ReleasesAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReleasesAlbumController {

    @Autowired
    private ReleasesAlbumService releasesAlbumService;

    @GetMapping("/artists/{artistId}/albums")
    public ResponseEntity<List<ReleasesAlbum>> getArtistAlbums(@PathVariable long artistId)
    {
        List<ReleasesAlbum> releasesAlbums = releasesAlbumService.getArtistAlbums(artistId);
        return new ResponseEntity<>(releasesAlbums, HttpStatus.OK);
    }

    @PostMapping(value = "/artists/{artistId}/albums", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReleasesAlbum> addAlbumToArtist(@RequestBody AlbumArtistKey albumArtistKey)
    {
        ReleasesAlbum releasesAlbum = new ReleasesAlbum();
        releasesAlbum.setId(albumArtistKey);
        return new ResponseEntity<>(releasesAlbumService.addAlbum(releasesAlbum), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/artists/{artistId}/albums/{albumId}")
    public ResponseEntity deleteAlbumFromArtist(@PathVariable long artistId, @PathVariable long albumId)
    {
        AlbumArtistKey albumArtistKey = new AlbumArtistKey();
        albumArtistKey.setArtistId(artistId);
        albumArtistKey.setAlbumId(albumId);
        releasesAlbumService.deleteAlbumFromArtist(albumArtistKey);
        return new ResponseEntity(HttpStatus.OK);
    }
}
