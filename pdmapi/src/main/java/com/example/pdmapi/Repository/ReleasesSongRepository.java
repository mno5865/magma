package com.example.pdmapi.Repository;

import com.example.pdmapi.Model.ReleasesSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ReleasesSongRepository extends JpaRepository<ReleasesSong, Long> {
}
