package com.example.pdmapi.Service;

import com.example.pdmapi.Model.ReleasesSong;
import com.example.pdmapi.Model.Song;
import com.example.pdmapi.Model.Artist;
import com.example.pdmapi.Model.Keys.SongArtistKey;
import com.example.pdmapi.Repository.SongRepository;
import com.example.pdmapi.Repository.ArtistRepository;
import com.example.pdmapi.Repository.ReleasesSongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReleasesSongService {

    @Autowired
    SongRepository songRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    ReleasesSongRepository releasesSongRepository;

    public ReleasesSong addSong(ReleasesSong releasesSong)
    {
        Song song = songRepository.findById(releasesSong.getId().getSongId()).get();
        Artist artist = artistRepository.findById(releasesSong.getId().getArtistId()).get();
        releasesSong.setSong(song);
        releasesSong.setArtist(artist);
        return releasesSongRepository.save(releasesSong);
    }

    public void deleteSongFromArtist(SongArtistKey songArtistKey)
    {
        ReleasesSong releasesSong = new ReleasesSong();
        Song song = songRepository.findById(songArtistKey.getSongId()).get();
        Artist artist = artistRepository.findById(songArtistKey.getArtistId()).get();
        releasesSong.setId(songArtistKey);
        releasesSong.setSong(song);
        releasesSong.setArtist(artist);
        releasesSongRepository.delete(releasesSong);
    }

    public List<ReleasesSong> getArtistSongs(long artistId)
    {
        List<ReleasesSong> releasesSongs = releasesSongRepository.findAll();
        List<ReleasesSong> foundSongs = new ArrayList<>();
        for(int i = 0; i < releasesSongs.size(); i++)
        {
            if(releasesSongs.get(i).getId().getArtistId() == artistId)
            {
                foundSongs.add(releasesSongs.get(i));
            }
        }
        return foundSongs;
    }
}
