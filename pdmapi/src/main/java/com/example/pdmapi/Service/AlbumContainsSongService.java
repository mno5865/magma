package com.example.pdmapi.Service;

import com.example.pdmapi.Model.Album;
import com.example.pdmapi.Model.AlbumContainsSong;
import com.example.pdmapi.Model.Keys.SongAlbumKey;
import com.example.pdmapi.Model.Song;
import com.example.pdmapi.Repository.AlbumContainsSongRepository;
import com.example.pdmapi.Repository.AlbumRepository;
import com.example.pdmapi.Repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumContainsSongService {
    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    SongRepository songRepository;

    @Autowired
    AlbumContainsSongRepository albumContainsSongRepository;

    public AlbumContainsSong addSong(AlbumContainsSong albumContainsSong) {
        Album album = albumRepository.findById(albumContainsSong.getId().getAlbumId()).get();
        Song song = songRepository.findById(albumContainsSong.getId().getSongId()).get();
        albumContainsSong.setAlbum(album);
        albumContainsSong.setSong(song);
        return albumContainsSongRepository.save(albumContainsSong);
    }

    public void deleteSongFromAlbum(SongAlbumKey songAlbumKey) {
        AlbumContainsSong albumContainsSong = new AlbumContainsSong();
        Album album = albumRepository.findById(songAlbumKey.getAlbumId()).get();
        Song song = songRepository.findById(songAlbumKey.getSongId()).get();
        albumContainsSong.setId(songAlbumKey);
        albumContainsSong.setAlbum(album);
        albumContainsSong.setSong(song);
        albumContainsSongRepository.delete(albumContainsSong);
    }


    public List<AlbumContainsSong> getAlbumSongs(long albumId) { //FIX THIS LATER AND USE AN SQL STATEMENT INSTEAD THIS IS REAAL BAD
        List<AlbumContainsSong> albumContainsSongs = albumContainsSongRepository.findAll();
        List<AlbumContainsSong> foundSongs = new ArrayList<>();
        for (int i = 0; i < albumContainsSongs.size(); i++) {
            if (albumContainsSongs.get(i).getId().getAlbumId() == albumId) {
                foundSongs.add(albumContainsSongs.get(i));
            }
        }
        return foundSongs;
    }
}
