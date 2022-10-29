/**
 * File: AlbumService.java
 * AlbumService.java: A public class that sets and gets the attributes for an album.
 * @author Gregory Ojiem - gro3228, Melissa Burisky - mpb8984, Mildness Onyekwere - mno5865
 */
package com.example.pdmapi.Service;

/**
 * Import Statements
 */
import com.example.pdmapi.Model.Album;
import com.example.pdmapi.Model.Song;
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

    final
    DataSource dataSource;

    /**
     * Constructor for Album Service.
     * @param dataSource connects users to the data for albums.
     */
    public AlbumService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // CREATE

    /**
     * Creates an album in the database
     * @param album an album in the database
     * @return -1
     */
    public int createAlbum(Album album) {
        // String that prints a message to insert an album
        String st = ("INSERT INTO album(title, release_date) VALUES ('%s', '%tF')")
                .formatted(album.getTitle(), album.getReleaseDate());
        Connection conn = DataSourceUtils.getConnection(dataSource);
        // Try Catch Error Checking if an album can be created
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
     * @return -1
     */
    public int createAlbumContainsSong(long albumId, long songId, int track) {
        // String that prints a message to insert a song in album
        String st = ("INSERT INTO album_contains_song(album_id, song_id) VALUES (%d, %d, %d)")
                .formatted(albumId, songId, track);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        // Try Catch Error Checking if song can be contained in album
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
        //// String that Selects an Album
        String query = "SELECT * FROM album";
        Connection conn = DataSourceUtils.getConnection(dataSource);

        // Try Catch Error Checking album can be obtained
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            List<Album> albums = new ArrayList<>();

            // If the Album does not exist create the album and then add it to the list of albums
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
     * Gets a specfic album from the database.
     * @param albumId ID of Album
     * @return the specific album the user is trying to get.
     */
    public Album getAlbum(long albumId) {
        // String that prints a message to get the album from a list of albums
        String query = ("SELECT * FROM album WHERE album_id=%d").formatted(albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        // Try Catch Error Checking if song can be contained in album
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            Album album = new Album();

            //Sets an albums title,ID and release date
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
     * Gets the list of songs in a specfic Album in a database.
     * @param albumId ID of the album
     * @return a list of Songs in Album
     */
    public List<Song> getSongsByAlbum(long albumId) {
        //Creates a list of songs
        List<Song> songs = new ArrayList<>();

        //String that prints a message to get songs in an album
        String query = ("SELECT song.song_id, song.title, song.release_date, song.runtime "
                + "FROM album_contains_song "
                + "INNER JOIN song on album_contains_song.song_id = song.song_id "
                + "INNER JOIN album on album_contains_song.album_id = album.album_id "
                + "WHERE album.album_id=%d").formatted(albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //Try Catch Error Checking if song in album exists
        try{
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);

        //If song does not exist in album it creates the song and adds it
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
     * Gets the song in the specfic album through searching for its tracking number.
     * @param albumId ID of Album
     * @param trackNumber the tracking number for the Album
     * @return the song in  Album
     */
    public Song getSongInAlbumByTrackNumber(long albumId, int trackNumber) {
        //
        String query = ("SELECT song.song_id, song.title, song.release_date, song.runtime "
                + "FROM album_contains_song "
                + "INNER JOIN song on album_contains_song.song_id = song.song_id "
                + "INNER JOIN album on album_contains_song.album_id = album.album_id "
                + "WHERE album.album_id=%d AND album_contains_song.track_number=%d").formatted(albumId, trackNumber);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //Try Catch Error Checking if song can be searched by its tracking number
        try{
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);

            Song song = new Song();

            //Gets the song ID,Title,ReleaseDate and Runtime
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
     * Updates the Album id and details
     * @param albumId ID of Album
     * @param albumDetails Details of the Album
     * @return -1
     */
    public int updateAlbum(long albumId, Album albumDetails) {
        //String that prints a message to update the album id and details
        String st = ("UPDATE album SET title='%s', release_date='%tF' WHERE album_id=%d")
                .formatted(albumDetails.getTitle(), albumDetails.getReleaseDate(), albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //Try Catch Error Checking if album can be updated
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
     *
     * @param albumId
     * @param songId
     * @param trackNumber
     * @return
     */
    public int updateSongTrackNumberInAlbum(long albumId, long songId, int trackNumber) {
        //String that prints a message to update a song by tracking number
        String st = ("UPDATE album_contains_song SET track_number=%d WHERE (album_id=%d AND song_id=%d)")
                .formatted(trackNumber, albumId, songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //Try Catch Error Checking if song can be updated by tracking number
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
     * Deletes album within the database.
     * @param albumId ID of an album
     * @return -1
     */
    public int deleteAlbum(long albumId) {
        // String that prints a message to insert a song in albumdelete album
        String st = ("DELETE FROM album WHERE album_id=%d").formatted(albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        // Try Catch Error Checking if album can be deleted
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
     * Deletes Album that has a specific song
     * @param albumId ID of album
     * @param songId ID of song
     * @return -1
     */
    public int deleteAlbumContainsSong(long albumId, long songId){
        //String that prints a message to delete an album with a specific song
        String st = ("DELETE FROM album_contains_song WHERE (album_id=%d AND song_id=%d)").formatted(albumId, songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        // Try Catch Error Checking if album can be deleted based on the song the user does not want
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
     * Gets a list of Collection of Albums
     * @param collectionId ID of collection
     * @return list of a collection of albums
     */
    public List<Album> getCollectionAlbums(long collectionId)
    {
        // String that prints a message to get a collection of songs
        String stmt = "SELECT album.album_id,album.title,album.release_date\n" +
                "FROM album,collection_holds_album\n" +
                "WHERE album.album_id=collection_holds_album.album_id\n" +
                "AND collection_holds_album.collection_id=%d".formatted(collectionId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        List<Album> albums = new ArrayList<>();

        // Try Catch Error Checking if albums can be recieved
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);

            //If album does not exist - creates Album and then sets its ID, Title, ReleaseDate and album
            while(rs.next())
            {
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
     * Recieves total rutime for the Album
     * @param albumId ID of Album
     * @return -1
     */
    public int getTotalRuntimeOfAlbum(long albumId)
    {
        int i = 0;

        // String that prints a message to get total runtime for the album
        String stmt = "SELECT sum(song.runtime) AS total_runtime\n" +
                "FROM song, album_contains_song,album\n" +
                "WHERE album_contains_song.song_id=song.song_id\n" +
                "AND album_contains_song.album_id=album.album_id\n" +
                "AND album.album_id=%d".formatted(albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        // Try Catch Error Checking if total run time exists
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            while(rs.next())
            {
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