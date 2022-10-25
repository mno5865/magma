package com.example.pdmapi.Service;

import com.example.pdmapi.Model.Album;
import com.example.pdmapi.Model.AlbumContainsSong;
import com.example.pdmapi.Model.Keys.SongAlbumKey;
import com.example.pdmapi.Model.Song;
import com.example.pdmapi.Repository.AlbumContainsSongRepository;
import com.example.pdmapi.Repository.AlbumRepository;
import com.example.pdmapi.Repository.SongRepository;
//import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.pdmapi.Service.SongService;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    @Autowired
    AlbumRepository albumRepository;

    // CREATE
    public Album createAlbum(Album album) {
        return albumRepository.save(album);
    }

    // READ
    public List<Album> getAlbums() {
        return albumRepository.findAll();
    }

    public Optional<Album> getAlbum(Long albumId) {
        return albumRepository.findById(albumId);
    }

    // UPDATE
    public Album updateAlbum(Long albumId, Album albumDetails) {
        Album album = albumRepository.findById(albumId).get();

        album.setAlbumID(albumId);
        album.setTitle(albumDetails.getTitle());
        album.setReleaseDate(albumDetails.getReleaseDate());

        return albumRepository.save(album);
    }

    // DELETE
    public void deleteAlbum(Long albumId) {
        albumRepository.deleteById(albumId);
    }
}