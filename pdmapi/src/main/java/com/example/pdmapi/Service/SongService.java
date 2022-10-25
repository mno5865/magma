package com.example.pdmapi.Service;

import com.example.pdmapi.Model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class SongService {

    @Autowired
    DataSource dataSource;

    // CREATE
    public int createSong(Song song) {
        String stmt = "INSERT INTO song (title,runtime, release_date) VALUES ('%s','%tT','%tF')".formatted(song.getTitle(),song.getRuntime(),song.getReleaseDate());
        try
        {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;

    }

    // READ
    public List<Song> getSongs() {
        String stmt = "SELECT * FROM song";
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            List<Song> songs = new ArrayList();
            while(rs.next())
            {
                Song song = new Song();
                song.setSongID(rs.getLong("song_id"));
                song.setTitle(rs.getString("title"));
                song.setRuntime(rs.getTime("runtime"));
                song.setReleaseDate(rs.getDate("release_date"));
                songs.add(song);
            }
            return songs;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Song getSong(Long songId) {
        String stmt = "SELECT * FROM song WHERE song_id=%d".formatted(songId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            Song song = new Song();
            while(rs.next())
            {
                song.setSongID(rs.getLong("song_id"));
                song.setTitle(rs.getString("title"));
                song.setRuntime(rs.getTime("runtime"));
                song.setReleaseDate(rs.getDate("release_date"));
            }
            return song;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE
    public int updateSong(Long songId, Song songDetails) {
        String stmt = "UPDATE song SET title='%s',runtime='%tT',release_date='%tF' WHERE song_id=%d"
                .formatted(songDetails.getTitle(),songDetails.getRuntime(),songDetails.getReleaseDate(),songId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // DELETE
    public int deleteSong(Long songId) {
        String stmt = "DELETE FROM song WHERE song_id=%d".formatted(songId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}