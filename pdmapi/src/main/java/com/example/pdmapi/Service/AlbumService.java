package com.example.pdmapi.Service;

import com.example.pdmapi.Model.Album;
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
        String query = "INSERT INTO album(title) VALUES ('%s')".formatted(album.getTitle());
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

    public Album getAlbum(Long albumId) {
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

            }
            return album;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // UPDATE
    public int updateAlbum(Long albumId, Album albumDetails) {
        String query = "UPDATE album SET name='%s' WHERE album_id=%d"
                .formatted(albumDetails.getTitle(), albumId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // DELETE
    public int deleteAlbum(Long albumId) {
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