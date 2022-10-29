/**
 * File: AlbumService.java
 * AlbumService.java: A public class that sets and gets the attributes for an album.
 * @author Gregory Ojiem - gro3228, Melissa Burisky - mpb8984, Mildness Onyekwere - mno5865
 */
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

/**
 * Service Album that defines all properties of what an Album should do.
 */
@Service
public class AlbumService {

    @Autowired
    DataSource dataSource;

    // CREATE
    /**
     * Creates an album in the database
     * @param album an album in the database
     * @return the amount of rows affected by this insert statement, if -1 there is a problem
     */
    public int createAlbum(Album album) {
        String st = ("INSERT INTO album(title, release_date) VALUES ('%s', '%tF')")
                .formatted(album.getTitle(), album.getReleaseDate());
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(st);
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

    /**
     * Makes an album in the database with a song in album.
     * @param albumId ID of the album
     * @param songId ID of the song
     * @return the amount of rows affected by this insert statement, if -1 there is a problem
     */
    public int createAlbumContainsSong(long albumId, long songId, int track) {
        String st = ("INSERT INTO album_contains_song(album_id, song_id) VALUES (%d, %d, %d)")
                .formatted(albumId, songId, track);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(st);
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
     * Gets all the Albums in the Database.
     * @return list of Albums
     */
    public List<Album> getAlbums() {
        String query = "SELECT * FROM album";
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
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
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Gets a specific album from the database.
     * @param albumId ID of Album
     * @return the specific album the user is trying to get.
     */
    public Album getAlbum(long albumId) {
        String query = ("SELECT * FROM album WHERE album_id=%d").formatted(albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
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
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * opens
     * Gets the list of songs in a specific Album in a database.
     * @param albumId ID of the album
     * @return a list of Songs in Album
     */
    public List<Song> getSongsByAlbum(long albumId) {
        List<Song> songs = new ArrayList<>();

        String query = ("SELECT song.song_id, song.title, song.release_date, song.runtime "
                + "FROM album_contains_song "
                + "INNER JOIN song on album_contains_song.song_id = song.song_id "
                + "INNER JOIN album on album_contains_song.album_id = album.album_id "
                + "WHERE album.album_id=%d").formatted(albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try{
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            //mapped results to song object
            while(rs.next()) {
                Song song = new Song();
                song.setSongId(rs.getLong("song_id"));
                song.setTitle(rs.getString("title"));
                song.setReleaseDate(rs.getDate("release_date"));
                song.setRuntime(rs.getLong("runtime"));
                songs.add(song);
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
     * Gets the song in the specific album through searching for its tracking number.
     * closes connection
     * @param albumId ID of Album
     * @param trackNumber the tracking number for the Album
     * @return the song in Album
     */
    public Song getSongInAlbumByTrackNumber(long albumId, int trackNumber) {
        String query = ("SELECT song.song_id, song.title, song.release_date, song.runtime "
                + "FROM album_contains_song "
                + "INNER JOIN song on album_contains_song.song_id = song.song_id "
                + "INNER JOIN album on album_contains_song.album_id = album.album_id "
                + "WHERE album.album_id=%d AND album_contains_song.track_number=%d").formatted(albumId, trackNumber);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try{
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);

            Song song = new Song();
            // maps results to song object
            while(rs.next()) {
                song.setSongId(rs.getLong("song_id"));
                song.setTitle(rs.getString("title"));
                song.setReleaseDate(rs.getDate("release_date"));
                song.setRuntime(rs.getLong("runtime"));
            }
            return song;
        }  catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
     * open collection
     * Updates the Album id and details
     * closes connection
     * @param albumId ID of Album
     * @param albumDetails Details of the Album
     * @return the amount of rows affected by this insert statement, if -1 there is a problem
     */
    public int updateAlbum(long albumId, Album albumDetails) {
        String st = ("UPDATE album SET title='%s', release_date='%tF' WHERE album_id=%d")
                .formatted(albumDetails.getTitle(), albumDetails.getReleaseDate(), albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(st);
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


    /**
     * open connection
     * Updates a song based on track number
     * closed connection
     * @param albumId ID of Album
     * @param songId ID of song
     * @param trackNumber track number
     * @return the amount of rows affected by this insert statement, if -1 there is a problem
     */
    public int updateSongTrackNumberInAlbum(long albumId, long songId, int trackNumber) {
        String st = ("UPDATE album_contains_song SET track_number=%d WHERE (album_id=%d AND song_id=%d)")
                .formatted(trackNumber, albumId, songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(st);
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

    // DELETE

    /**
     * opens connection
     * Deletes album within the database.
     * closed connection
     * @param albumId ID of an album
     * @return stmt.executeUpdate(st) or -1
     */
    public int deleteAlbum(long albumId) {
        String st = ("DELETE FROM album WHERE album_id=%d").formatted(albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(st);
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

    /**
     * open connection
     * Deletes Album that has a specific song
     * closed connection
     * @param albumId ID of album
     * @param songId ID of song
     * @return stmt.executeUpdate(st) or -1
     */
    public int deleteAlbumContainsSong(long albumId, long songId) {
        String st = ("DELETE FROM album_contains_song WHERE (album_id=%d AND song_id=%d)").formatted(albumId, songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(st);
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

    /**
     * opens connection
     * Gets a list of Collection of Albums
     * closed connection
     * @param collectionId ID of collection
     * @return list of a collection of albums
     */
    public List<Album> getCollectionAlbums(long collectionId) {
        String stmt = "SELECT album.album_id,album.title,album.release_date\n" +
                "FROM album,collection_holds_album\n" +
                "WHERE album.album_id=collection_holds_album.album_id\n" +
                "AND collection_holds_album.collection_id=%d".formatted(collectionId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        List<Album> albums = new ArrayList<>();

        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);

            // maps results to to album object
            while(rs.next()) {
                Album album = new Album();
                album.setAlbumID(rs.getLong("album_id"));
                album.setTitle(rs.getString("title"));
                album.setReleaseDate(rs.getDate("release_date"));
                albums.add(album);
            }
            return albums;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
     * gets total runtime for the Album
     * closes connection
     * @param albumId ID of Album
     * @return i or -1 if query failed
     */
    public int getTotalRuntimeOfAlbum(long albumId) {
        int i = 0;
        String stmt = "SELECT sum(song.runtime) AS total_runtime\n" +
                "FROM song, album_contains_song,album\n" +
                "WHERE album_contains_song.song_id=song.song_id\n" +
                "AND album_contains_song.album_id=album.album_id\n" +
                "AND album.album_id=%d".formatted(albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            while(rs.next()) {
                i = rs.getInt("total_runtime");
            }
            return i;
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
}