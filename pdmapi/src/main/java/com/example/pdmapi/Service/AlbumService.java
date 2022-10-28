/**
 * File: AlbumService.java
 * AlbumService.java: A public class that sets and gets the attributes for an album.
 * @author MAGMA
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
import java.util.Collection;
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
     *
     * @param album
     * @return
     */
    public int createAlbum(Album album) {
        //
        String st = ("INSERT INTO album(title, release_date) VALUES ('%s', '%tF')")
                .formatted(album.getTitle(), album.getReleaseDate());
        Connection conn = DataSourceUtils.getConnection(dataSource);
        //
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
     * @return
     */
    public int createAlbumContainsSong(long albumId, long songId){
        //
        String st = ("INSERT INTO album_contains_song(album_id, song_id) VALUES (%d, %d)")
                .formatted(albumId, songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //
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
     *
     * @return
     */
    public List<Album> getAlbums() {
        //
        String query = "SELECT * FROM album";
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //
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
     *
     * @param albumId
     * @return
     */
    public Album getAlbum(long albumId) {
        //
        String query = ("SELECT * FROM album WHERE album_id=%d").formatted(albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //
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
     *
     * @param albumId
     * @return
     */
    public List<Song> getSongsByAlbum(long albumId) {
        //
        List<Song> songs = new ArrayList<>();

        //
        String query = ("SELECT song.song_id, song.title, song.release_date, song.runtime "
                + "FROM album_contains_song "
                + "INNER JOIN song on album_contains_song.song_id = song.song_id "
                + "INNER JOIN album on album_contains_song.album_id = album.album_id "
                + "WHERE album.album_id=%d").formatted(albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //
        try{
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);

        //
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
     *
     * @param albumId
     * @param trackNumber
     * @return
     */
    public Song getSongInAlbumByTrackNumber(long albumId, int trackNumber) {
        //
        String query = ("SELECT song.song_id, song.title, song.release_date, song.runtime "
                + "FROM album_contains_song "
                + "INNER JOIN song on album_contains_song.song_id = song.song_id "
                + "INNER JOIN album on album_contains_song.album_id = album.album_id "
                + "WHERE album.album_id=%d AND album_contains_song.track_number=%d").formatted(albumId, trackNumber);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //
        try{
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);

            Song song = new Song();

            //
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
     *
     * @param albumId
     * @param albumDetails
     * @return
     */
    public int updateAlbum(long albumId, Album albumDetails) {
        //
        String st = ("UPDATE album SET title='%s', release_date='%tF' WHERE album_id=%d")
                .formatted(albumDetails.getTitle(), albumDetails.getReleaseDate(), albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //
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
        //
        String st = ("UPDATE album_contains_song SET track_number=%d WHERE (album_id=%d AND song_id=%d)")
                .formatted(trackNumber, albumId, songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //
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
     *
     * @param albumId
     * @return
     */
    public int deleteAlbum(long albumId) {
        //
        String st = ("DELETE FROM album WHERE album_id=%d").formatted(albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //
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
     * @return
     */
    public int deleteAlbumContainsSong(long albumId, long songId){
        //
        String st = ("DELETE FROM album_contains_song WHERE (album_id=%d AND song_id=%d)").formatted(albumId, songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //
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
     * @param collectionId
     * @return
     */
    public List<Album> getCollectionAlbums(long collectionId)
    {
        //
        String stmt = "SELECT album.album_id,album.title,album.release_date\n" +
                "FROM album,collection_holds_album\n" +
                "WHERE album.album_id=collection_holds_album.album_id\n" +
                "AND collection_holds_album.collection_id=%d".formatted(collectionId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        List<Album> albums = new ArrayList<>();

        //
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);

            //
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
     *
     * @param albumId
     * @return
     */
    public int getTotalRuntimeOfAlbum(long albumId)
    {
        int i = 0;
        //
        String stmt = "SELECT sum(song.runtime) AS total_runtime\n" +
                "FROM song, album_contains_song,album\n" +
                "WHERE album_contains_song.song_id=song.song_id\n" +
                "AND album_contains_song.album_id=album.album_id\n" +
                "AND album.album_id=%d".formatted(albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //
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