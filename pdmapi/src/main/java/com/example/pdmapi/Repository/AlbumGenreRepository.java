package com.example.pdmapi.Repository;

import com.example.pdmapi.Model.AlbumGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumGenreRepository extends JpaRepository<AlbumGenre, Long> {

}
