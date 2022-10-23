package com.example.pdmapi.Repository;

import com.example.pdmapi.Model.ReleasesAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ReleasesAlbumRepository extends JpaRepository<ReleasesAlbum, Long> {
}
