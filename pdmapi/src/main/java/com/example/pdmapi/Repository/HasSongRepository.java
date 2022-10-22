package com.example.pdmapi.Repository;

import com.example.pdmapi.Model.AlbumContainsSong;
import com.example.pdmapi.Model.HasSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HasSongRepository extends JpaRepository<HasSong, Long> {
}
