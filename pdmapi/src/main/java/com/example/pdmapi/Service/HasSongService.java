package com.example.pdmapi.Service;

import com.example.pdmapi.Model.AlbumContainsSong;
import com.example.pdmapi.Model.HasSong;
import com.example.pdmapi.Model.Song;
import com.example.pdmapi.Model.Genre;
import com.example.pdmapi.Model.Keys.SongGenreKey;
import com.example.pdmapi.Repository.SongRepository;
import com.example.pdmapi.Repository.GenreRepository;
import com.example.pdmapi.Repository.HasSongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HasSongService {

    @Autowired
    SongRepository songRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    HasSongRepository hasSongRepository;

    public HasSong addGenre(HasSong hasSong)
    {
        Genre genre = genreRepository.findById(hasSong.getId().getGenreId()).get();
        Song song = songRepository.findById(hasSong.getId().getSongId()).get();
        hasSong.setGenre(genre);
        hasSong.setSong(song);
        return hasSongRepository.save(hasSong);
    }

    public void deleteGenreFromSong(SongGenreKey songGenreKey)
    {
        HasSong hasSong = new HasSong();
        Genre genre = genreRepository.findById(songGenreKey.getGenreId()).get();
        Song song = songRepository.findById(songGenreKey.getSongId()).get();
        hasSong.setId(songGenreKey);
        hasSong.setGenre(genre);
        hasSong.setSong(song);
        hasSongRepository.delete(hasSong);
    }

    public List<HasSong> getSongGenres(long songId)
    {
        List<HasSong> hasSongs = hasSongRepository.findAll();
        List<HasSong> foundGenres = new ArrayList<>();
        for(int i = 0; i < hasSongs.size(); i++)
        {
            if(hasSongs.get(i).getId().getSongId() == songId)
            {
                foundGenres.add(hasSongs.get(i));
            }
        }
        return foundGenres;
    }
}
