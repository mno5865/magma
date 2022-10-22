package com.example.pdmapi.Repository;

import com.example.pdmapi.Model.AlbumContainsSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumContainsSongRepository extends JpaRepository<AlbumContainsSong, Long> {

}
