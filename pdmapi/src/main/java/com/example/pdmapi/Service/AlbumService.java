package com.example.pdmapi.Service;

import com.example.pdmapi.Model.Album;
import com.example.pdmapi.Model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumService {

    @Autowired
    DataSource dataSource;

    // CREATE
    public int createAlbum(Album album) {
        String query = "INSERT INTO album(title, release_date) VALUES ('%s', '%tF')"
                .formatted(album.getTitle(), album.getReleaseDate());
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // READ
    public List<Album> getAlbums() {
        String query = "SELECT * FROM album";
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            List<Album> albums = new ArrayList<>();
            while(rs.next()) {
                Album album = new Album();
                album.setAlbumID(rs.getLong("album_id"));
                album.setTitle(rs.getString("title"));
                album.setReleaseDate(rs.getDate("release_date"));
                albums.add(album);
            }
            return albums;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Album getAlbum(long albumId) {
        String query = "SELECT * FROM album WHERE album_id=%d".formatted(albumId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            Album album = new Album();
            while(rs.next()) {
                album.setAlbumID(rs.getLong("album_id"));
                album.setTitle(rs.getString("title"));
                album.setReleaseDate(rs.getDate("release_date"));
            }
            return album;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Song> getSongsByAlbum(long albumId){
        List<Song> songs = new ArrayList<>();

        String query = ("SELECT song.song_id, song.title, song.release-date, song.runtime "
                + "FROM album_contains_song "
                + "INNER JOIN song on album_contains_song.song_id = song.song_id "
                + "INNER JOIN album on album_contains_song.album_id = album.album_id "
                + "WHERE album_id='%d").formatted(albumId);
        try{
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                Song song = new Song();
                song.setSongId(rs.getLong("song_id"));
                song.setTitle(rs.getString("title"));
                song.setReleaseDate(rs.getDate("release-date"));
                song.setRuntime(rs.getTime("runtime"));
                songs.add(song);
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    // UPDATE
    public int updateAlbum(Long albumId, Album albumDetails) {
        String query = "UPDATE album SET title='%s' release_date='%tF' WHERE album_id=%d"
                .formatted(albumDetails.getTitle(), albumDetails.getReleaseDate(), albumId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // DELETE
    public int deleteAlbum(long albumId) {
        String query = "DELETE FROM album WHERE album_id=%d".formatted(albumId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}