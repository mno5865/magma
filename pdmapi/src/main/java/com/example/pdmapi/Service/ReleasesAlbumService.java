package com.example.pdmapi.Service;

import com.example.pdmapi.Model.ReleasesAlbum;
import com.example.pdmapi.Model.Album;
import com.example.pdmapi.Model.Artist;
import com.example.pdmapi.Model.Keys.AlbumArtistKey;
import com.example.pdmapi.Repository.AlbumRepository;
import com.example.pdmapi.Repository.ArtistRepository;
import com.example.pdmapi.Repository.ReleasesAlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReleasesAlbumService {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    ReleasesAlbumRepository releasesAlbumRepository;

    public ReleasesAlbum addAlbum(ReleasesAlbum releasesAlbum)
    {
        Album album = albumRepository.findById(releasesAlbum.getId().getAlbumId()).get();
        Artist artist = artistRepository.findById(releasesAlbum.getId().getArtistId()).get();
        releasesAlbum.setAlbum(album);
        releasesAlbum.setArtist(artist);
        return releasesAlbumRepository.save(releasesAlbum);
    }

    public void deleteAlbumFromArtist(AlbumArtistKey albumArtistKey)
    {
        ReleasesAlbum releasesAlbum = new ReleasesAlbum();
        Album album = albumRepository.findById(albumArtistKey.getAlbumId()).get();
        Artist artist = artistRepository.findById(albumArtistKey.getArtistId()).get();
        releasesAlbum.setId(albumArtistKey);
        releasesAlbum.setAlbum(album);
        releasesAlbum.setArtist(artist);
        releasesAlbumRepository.delete(releasesAlbum);
    }

    public List<ReleasesAlbum> getArtistAlbums(long artistId)
    {
        List<ReleasesAlbum> releasesAlbums = releasesAlbumRepository.findAll();
        List<ReleasesAlbum> foundAlbums = new ArrayList<>();
        for(int i = 0; i < releasesAlbums.size(); i++)
        {
            if(releasesAlbums.get(i).getId().getArtistId() == artistId)
            {
                foundAlbums.add(releasesAlbums.get(i));
            }
        }
        return foundAlbums;
    }
}
