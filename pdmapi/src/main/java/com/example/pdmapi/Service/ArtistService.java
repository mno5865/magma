package com.example.pdmapi.Service;


import com.example.pdmapi.Model.Artist;
import com.example.pdmapi.Repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    ArtistRepository artistRepository;

    // CREATE
    public Artist createArtist(Artist artist) {

        return artistRepository.save(artist);
    }

    // READ
    public List<Artist> getArtists() {
        return artistRepository.findAll();
    }

    public Optional<Artist> getArtist(Long artistId) {
        return artistRepository.findById(artistId);
    }

    // UPDATE
    public Artist updateArtist(Long artistId, Artist artistDetails) {
        Artist artist = artistRepository.findById(artistId).get();

        artist.setArtistID(artistId);
        artist.setName(artistDetails.getName());

        return artistRepository.save(artist);
    }

    // DELETE
    public void deleteArtist(Long userId) {
        artistRepository.deleteById(userId);
    }
}
