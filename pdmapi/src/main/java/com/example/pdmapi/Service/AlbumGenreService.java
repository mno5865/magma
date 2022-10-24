package com.example.pdmapi.Service;

import com.example.pdmapi.Model.Album;
import com.example.pdmapi.Model.AlbumGenre;
import com.example.pdmapi.Model.Keys.AlbumGenreKey;
import com.example.pdmapi.Model.Genre;
import com.example.pdmapi.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumGenreService {
    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    AlbumGenreRepository albumGenreRepository;

    public AlbumGenre addGenre(AlbumGenre albumGenre) {
        Album album = albumRepository.findById(albumGenre.getId().getAlbumId()).get();
        Genre genre = genreRepository.findById(albumGenre.getId().getGenreId()).get();
        albumGenre.setAlbum(album);
        albumGenre.setGenre(genre);
        return albumGenreRepository.save(albumGenre);
    }

    public void deleteGenreFromAlbum(AlbumGenreKey albumGenreKey) {
        AlbumGenre albumGenre = new AlbumGenre();
        Album album = albumRepository.findById(albumGenreKey.getAlbumId()).get();
        Genre genre = genreRepository.findById(albumGenreKey.getGenreId()).get();
        albumGenre.setId(albumGenreKey);
        albumGenre.setAlbum(album);
        albumGenre.setGenre(genre);
        albumGenreRepository.delete(albumGenre);
    }


    public List<AlbumGenre> getAlbumGenres(long albumId) { //FIX THIS LATER AND USE AN SQL STATEMENT INSTEAD THIS IS REAAL BAD
        List<AlbumGenre> albumGenres = albumGenreRepository.findAll();
        List<AlbumGenre> foundGenres = new ArrayList<>();
        for (int i = 0; i < albumGenres.size(); i++) {
            if (albumGenres.get(i).getId().getAlbumId() == albumId) {
                foundGenres.add(albumGenres.get(i));
            }
        }
        return foundGenres;
    }
}
