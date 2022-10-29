/**
 * File: ArtistService.java
 * AritstService.java: A public class that sets and gets the attributes for an artist.
 * @author Gregory Ojiem - gro3228, Melissa Burisky - mpb8984, Mildness Onyekwere - mno5865
 */
package com.example.pdmapi.Service;

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

    /** field injection of datasource from ssh connection */
    @Autowired
    DataSource dataSource;

    // CREATE

    /**
     * Artist is being created in the database
     * @param artist artist being created
     * @return the amount of rows affected by this insert statement, if -1 there is a problem
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
     * opens connection
     * Gets a list of artists from db
     * closes connection
     * @return list of artists
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
     * opens connection
     * Get Songs by a specific artist
     * closes connection
     * @param artistId ID of artist
     * @return list of songs
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
            //maps results to song object
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
     * open connection
     * Gets Albums by a specific artist
     * closes connection
     * @param artistId ID of the artist
     * @return list of albums
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
        } catch (Exception e) {
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
     * opens connection
     * Gets an artist from the database
     * closes connection
     * @param artistId ID of an artist
     * @return artist or null
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
     * Updates an artist in the database
     * @param artistId ID of artist
     * @param artistDetails details of an artist
     * @return the amount of rows affected by this insert statement, if -1 there is a problem
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
     * opens connection
     * Deletes an artist from the database
     * closes connection
     * @param artistId ID of artist
     * @return the amount of rows affected by this insert statement, if -1 there is a problem
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

    // CREATE

    /**
     * opens connection
     * Creates an Artist who releases an Album
     * closes connection
     * @param artistId ID of artist
     * @param albumId ID of an album
     * @return the amount of rows affected by this insert statement, if -1 there is a problem
     */
    public int createArtistReleasesAlbum(long artistId, long albumId) {
        String stmt = "INSERT INTO artist_releases_album (artist_id, album_id) VALUES (%d,%d)"
                .formatted(artistId,albumId);
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

    // DELETE

    /**
     * opens connection
     * Deletes an Artist who releases an album
     * closes connection
     * @param albumId ID of album
     * @param artistId ID of artist
     * @return the amount of rows affected by this insert statement, if -1 there is a problem
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
     * open connection
     * Creates an artist who releases a new song
     * closed connection
     * @param artistId ID of artist
     * @param songId ID of song
     * @return the amount of rows affected by this insert statement, if -1 there is a problem
     */
    public int createArtistReleasesSong(long artistId, long songId) {
        String stmt = ("INSERT INTO artist_releases_song (artist_id, song_id) VALUES (%d,%d)")
                .formatted(artistId,songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        } catch (Exception e) {
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

    // DELETE

    /**
     * Deletes an artist who releases a song
     * @param songId ID of song
     * @param artistId ID of artist
     * @return statement.executeUpdate(stmt) or -1
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