/**
 * File: ArtistService.java
 * AritstService.java: A public class that sets and gets the attributes for an artist.
 * @author MAGMA
 */
package com.example.pdmapi.Service;

/**
 * Import Statements
 */
import com.example.pdmapi.Model.Album;
import com.example.pdmapi.Model.Artist;
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

/**
 * Service Artist that defines all properties of what an Artist should do.
 */
@Service
public class ArtistService {

    @Autowired
    DataSource dataSource;

    // CREATE

    /**
     *
     * @param artist
     * @return
     */
    public int createArtist(Artist artist) {
        String query = "INSERT INTO artist(name) VALUES ('%s')".formatted(artist.getName());
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    // READ

    /**
     *
     * @return
     */
    public List<Artist> getArtists() {
        String query = "SELECT * FROM artist";
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            List<Artist> artists = new ArrayList<>();
            while(rs.next()) {
                Artist artist = new Artist();
                artist.setArtistID(rs.getLong("artist_id"));
                artist.setName(rs.getString("name"));
                artists.add(artist);
            }
            return artists;
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     *
     * @param artistId
     * @return
     */
    public List<Song> getSongsByArtist(long artistId) {
        List<Song> songs = new ArrayList<>();
        String stmt = ("SELECT song.song_id,song.title,song.release_date,song.runtime" +
                " FROM artist_releases_song" +
                " INNER JOIN song on artist_releases_song.song_id = song.song_id" +
                " INNER JOIN artist on artist_releases_song.artist_id = artist.artist_id " +
                " WHERE artist.artist_id=%d").formatted(artistId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            while(rs.next()) {
                Song song = new Song();
                song.setSongId(rs.getLong("song_id"));
                song.setTitle(rs.getString("title"));
                song.setReleaseDate(rs.getDate("release_date"));
                song.setRuntime(rs.getLong("runtime"));
                songs.add(song);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return songs;
    }

    /**
     *
     * @param artistId
     * @return
     */
    public List<Album> getAlbumsByArtist(long artistId) {
        List<Album> albums = new ArrayList<>();
        String stmt = "SELECT album.album_id,album.title,album.release_date " +
                "FROM artist_releases_album " +
                "INNER JOIN album on artist_releases_album.album_id = album.album_id " +
                "INNER JOIN artist on artist_releases_album.artist_id = artist.artist_id " +
                "WHERE artist.artist_id=%d".formatted(artistId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            while(rs.next())
            {
                Album album = new Album();
                album.setAlbumID(rs.getLong("album_id"));
                album.setTitle(rs.getString("title"));
                album.setReleaseDate(rs.getDate("release_date"));
                albums.add(album);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }  finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return albums;
    }

    /**
     *
     * @param artistId
     * @return
     */
    public Artist getArtist(Long artistId) {
        String query = "SELECT * FROM artist WHERE artist_id=%d".formatted(artistId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            Artist artist = new Artist();
            while(rs.next()) {
                artist.setArtistID(rs.getLong("artist_id"));
                artist.setName(rs.getString("name"));
            }
            return artist;
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // UPDATE

    /**
     *
     * @param artistId
     * @param artistDetails
     * @return
     */
    public int updateArtist(Long artistId, Artist artistDetails) {
        String query = "UPDATE artist SET name='%s' WHERE artist_id=%d"
                .formatted(artistDetails.getName(), artistId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    // DELETE

    /**
     *
     * @param artistId
     * @return
     */
    public int deleteArtist(Long artistId) {
        String query = "DELETE FROM artist WHERE artist_id=%d".formatted(artistId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
    // artists_releases_album RELATIONSHIP

    // CREATE

    /**
     *
     * @param artistId
     * @param albumId
     * @return
     */
    public int createArtistReleasesAlbum(long artistId, long albumId)
    {
        String stmt = "INSERT INTO artist_releases_album (artist_id, album_id) VALUES (%d,%d)"
                .formatted(artistId,albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        } catch (Exception e)
        {
            e.printStackTrace();
        }  finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    // DELETE

    /**
     *
     * @param albumId
     * @param artistId
     * @return
     */
    public int deleteArtistReleaseAlbum(long albumId, long artistId) {
        String stmt = "DELETE FROM artist_releases_album WHERE album_id=%d AND artist_id=%d"
                .formatted(albumId,artistId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    // artists_releases_song RELATIONSHIP

    // CREATE

    /**
     *
     * @param artistId
     * @param songId
     * @return
     */
    public int createArtistReleasesSong(long artistId, long songId)
    {
        String stmt = ("INSERT INTO artist_releases_song (artist_id, song_id) VALUES (%d,%d)")
                .formatted(artistId,songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        } catch (Exception e)
        {
            e.printStackTrace();
        }  finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    // DELETE

    /**
     *
     * @param songId
     * @param artistId
     * @return
     */
    public int deleteArtistReleaseSong(long songId, long artistId) {
        String stmt = "DELETE FROM artist_releases_song WHERE song_id=%d AND artist_id=%d"
                .formatted(songId,artistId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
}