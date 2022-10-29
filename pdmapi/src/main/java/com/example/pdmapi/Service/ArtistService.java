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
     * Artist is being created in the database
     * @param artist artist being created
     * @return -1
     */
    public int createArtist(Artist artist) {
        String query = "INSERT INTO artist(name) VALUES ('%s')".formatted(artist.getName());
        Connection conn = DataSourceUtils.getConnection(dataSource);

        // Try Catch Error Checking if artist can get created
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
     * Gets a list of artists
     * @return list of artists
     */
    public List<Artist> getArtists() {
        String query = "SELECT * FROM artist";
        Connection conn = DataSourceUtils.getConnection(dataSource);

        // Try Catch Error Checking if artists are in database
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            List<Artist> artists = new ArrayList<>();

            // Creates a new aritst and adds its details
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
     * Get Songs by a specific aritst
     * @param artistId ID of artist
     * @return list of songs
     */
    public List<Song> getSongsByArtist(long artistId) {
        List<Song> songs = new ArrayList<>();
        // String that prints a message to get a song by an artist
        String stmt = ("SELECT song.song_id,song.title,song.release_date,song.runtime" +
                " FROM artist_releases_song" +
                " INNER JOIN song on artist_releases_song.song_id = song.song_id" +
                " INNER JOIN artist on artist_releases_song.artist_id = artist.artist_id " +
                " WHERE artist.artist_id=%d").formatted(artistId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //Try Catch Error Checking if song can exist for Artist
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            // Creates a song, and adds its details of id, title, releasedate, runtime and then adds songs to a list.
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
     * Gtes Albums by a specfic artist
     * @param artistId ID of the artist
     * @return list of albums
     */
    public List<Album> getAlbumsByArtist(long artistId) {
        List<Album> albums = new ArrayList<>();
        //String that prints a message to get albums by the artist
        String stmt = "SELECT album.album_id,album.title,album.release_date " +
                "FROM artist_releases_album " +
                "INNER JOIN album on artist_releases_album.album_id = album.album_id " +
                "INNER JOIN artist on artist_releases_album.artist_id = artist.artist_id " +
                "WHERE artist.artist_id=%d".formatted(artistId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //Try Catch Error Checking if list of albums exist for an artist
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
     * Gtes an artist from the database
     * @param artistId ID of an artist
     * @return artist or null
     */
    public Artist getArtist(Long artistId) {
        String query = "SELECT * FROM artist WHERE artist_id=%d".formatted(artistId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //Try Catch Error Checking if an artist exists
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
     * @return -1
     */
    public int updateArtist(Long artistId, Artist artistDetails) {
        // String that prints a message to update the artist
        String query = "UPDATE artist SET name='%s' WHERE artist_id=%d"
                .formatted(artistDetails.getName(), artistId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        //Try Catch Error Checking if an artist exists to update it
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
     * Deletes an aritst from the database
     * @param artistId ID of artist
     * @return -1
     */
    public int deleteArtist(Long artistId) {
        //String that prints a message to delete an artist
        String query = "DELETE FROM artist WHERE artist_id=%d".formatted(artistId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //Try Catch Error Checking if artists exists to be deleted
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
     * Creates an Artist who releases an Album
     * @param artistId ID of artist
     * @param albumId ID of an album
     * @return statement.executeUpdate(stmt) or -1
     */
    public int createArtistReleasesAlbum(long artistId, long albumId)
    {
        //String that prints a message to create an artists new album
        String stmt = "INSERT INTO artist_releases_album (artist_id, album_id) VALUES (%d,%d)"
                .formatted(artistId,albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //Try Catch Error Checking if artist can release an album
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
     * Deletes an Artist who releases an album
     * @param albumId ID of album
     * @param artistId ID of artist
     * @return  statement.executeUpdate(stmt) or -1
     */
    public int deleteArtistReleaseAlbum(long albumId, long artistId) {
        //String that prints a message to delete artist from album release
        String stmt = "DELETE FROM artist_releases_album WHERE album_id=%d AND artist_id=%d"
                .formatted(albumId,artistId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //Try Catch Error Checking if Artist should be deleted if they release album
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
     * Creates an artist who releases a new song
     * @param artistId ID of artist
     * @param songId ID of song
     * @return statement.executeUpdate(stmt) or -1
     */
    public int createArtistReleasesSong(long artistId, long songId)
    {
        //String that prints a message to create artist
        String stmt = ("INSERT INTO artist_releases_song (artist_id, song_id) VALUES (%d,%d)")
                .formatted(artistId,songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //Try Catch Error Checking if artist can be created
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
     * Deletes an artist who releases a song
     * @param songId ID of song
     * @param artistId ID of artist
     * @return statement.executeUpdate(stmt) or -1
     */
    public int deleteArtistReleaseSong(long songId, long artistId) {
        //String that prints a message to delete an artist
        String stmt = "DELETE FROM artist_releases_song WHERE song_id=%d AND artist_id=%d"
                .formatted(songId,artistId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //Try Catch Error Checking if an artist can be deleted
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