package com.example.pdmapi.Service;


import com.example.pdmapi.Model.Artist;
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
public class ArtistService {

    @Autowired
    DataSource dataSource;

    // CREATE
    public int createArtist(Artist artist) {
        String query = "INSERT INTO artist(name) VALUES ('%s')".formatted(artist.getName());
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
    public List<Artist> getArtists() {
        String query = "SELECT * FROM artist";
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            List<Artist> artists = new ArrayList<>();
            while(rs.next()) {
                Artist artist = new Artist();
                artist.setArtistID(rs.getLong("artistid"));
                artist.setName(rs.getString("name"));
                artists.add(artist);
            }
            return artists;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Artist getArtist(Long artistId) {
        String query = "SELECT * FROM artist WHERE artistid=%d".formatted(artistId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            Artist artist = new Artist();
            while(rs.next()) {
                artist.setArtistID(rs.getLong("artistid"));
                artist.setName(rs.getString("name"));
            }
            return artist;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // UPDATE
    public int updateArtist(Long artistId, Artist artistDetails) {
        String query = "UPDATE artist SET name='%s' WHERE artistid=%d"
                .formatted(artistDetails.getName(), artistId);
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
    public int deleteArtist(Long artistId) {
        String query = "DELETE FROM artist WHERE artistid=%d".formatted(artistId);
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
