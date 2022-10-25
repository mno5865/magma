package com.example.pdmapi.Service;

import com.example.pdmapi.Model.Song;
import com.example.pdmapi.Repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    @Autowired
    SongRepository songRepository;

    // CREATE
    public Song createSong(Song song) {
        return songRepository.save(song);
    }

    // READ
    public List<Song> getSongs() {
        return songRepository.findAll();
    }

    public Optional<Song> getSong(Long songId) {
        return songRepository.findById(songId);
    }

    // UPDATE
    public Song updateSong(Long songId, Song songDetails) {
        Song song = songRepository.findById(songId).get();

        song.setSongID(songId);
        song.setTitle(songDetails.getTitle());
        song.setRuntime(songDetails.getRuntime());
        song.setReleaseDate(songDetails.getReleaseDate());

        return songRepository.save(song);
    }

    // DELETE
    public void deleteSong(Long songId) {
        songRepository.deleteById(songId);
    }
}