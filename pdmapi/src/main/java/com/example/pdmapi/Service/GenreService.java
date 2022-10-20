package com.example.pdmapi.Service;

import com.example.pdmapi.Model.Genre;
import com.example.pdmapi.Repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    @Autowired
    GenreRepository genreRepository;

    // CREATE
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    // READ
    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    public Optional<Genre> getGenre(Long genreId) {
        return genreRepository.findById(genreId);
    }

    // UPDATE
    public Genre updateGenre(Long genreId, Genre genreDetails) {
        Genre genre = genreRepository.findById(genreId).get();

        genre.setGenreID(genreId);
        genre.setName(genreDetails.getName());

        return genreRepository.save(genre);
    }

    // DELETE
    public void deleteGenre(Long genreId) {
        genreRepository.deleteById(genreId);
    }
}