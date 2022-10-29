/**
 * File: SongService.java
 * SongService.java: A public class that sets and gets the attributes for songservice.
 * @author MAGMA
 */
package com.example.pdmapi.Service;

/**
 * Import Statements
 */
import com.example.pdmapi.Model.Song;
import com.example.pdmapi.Model.SongInView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Song that defines all properties of what Song should do.
 */
@Service
public class SongService {

    @Autowired
    DataSource dataSource;

    // CREATE

    /**
     *
     * @param song
     * @return
     */
    public int createSong(Song song) {
        String stmt = "INSERT INTO song (title,runtime, release_date) VALUES ('%s',%d,'%tF')".formatted(song.getTitle(),song.getRuntime(),song.getReleaseDate());
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

    // READ

    /**
     *
     * @return
     */
    public List<Song> getSongs() {
        String stmt = "SELECT * FROM song";
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            List<Song> songs = new ArrayList<>();
            while(rs.next()) {
                Song song = new Song();
                song.setSongId(rs.getLong("song_id"));
                song.setTitle(rs.getString("title"));
                song.setRuntime(rs.getLong("runtime"));
                song.setReleaseDate(rs.getDate("release_date"));
                songs.add(song);
            }
            return songs;
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
     * @param songId
     * @return
     */
    public Song getSong(Long songId) {
        String stmt = "SELECT * FROM song WHERE song_id=%d".formatted(songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            Song song = new Song();
            while(rs.next()) {
                song.setSongId(rs.getLong("song_id"));
                song.setTitle(rs.getString("title"));
                song.setRuntime(rs.getLong("runtime"));
                song.setReleaseDate(rs.getDate("release_date"));
            }
            return song;
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

    // UPDATE

    /**
     *
     * @param songId
     * @param songDetails
     * @return
     */
    public int updateSong(Long songId, Song songDetails) {
        String stmt = "UPDATE song SET title='%s',runtime=%d,release_date='%tF' WHERE song_id=%d"
                .formatted(songDetails.getTitle(),songDetails.getRuntime(),songDetails.getReleaseDate(), songId);
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
     *
     * @param songId
     * @return
     */
    public int deleteSong(Long songId) {
        String stmt = "DELETE FROM song WHERE song_id=%d".formatted(songId);
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

    /**
     *
     * @param collectionId
     * @return
     */
    public List<Song> getCollectionSongs(long collectionId) {
        String stmt = "SELECT song.song_id,song.title,song.runtime,song.release_date\n" +
                "FROM song,collection_holds_song\n" +
                "WHERE song.song_id=collection_holds_song.song_id\n" +
                "AND collection_holds_song.collection_id=%d".formatted(collectionId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        List<Song> songs = new ArrayList<>();
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            while (rs.next()) {
                Song song = new Song();
                song.setSongId(rs.getLong("song_id"));
                song.setTitle(rs.getString("title"));
                song.setRuntime(rs.getLong("runtime"));
                song.setReleaseDate(rs.getDate("release_date"));
                songs.add(song);
            }
            return songs;
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

    // song_view

    /**
     *
     * @param songTitle
     * @param select
     * @param sort
     * @return
     */
    public List<SongInView> getSongsByTitle(String songTitle, int select,String sort) {
        List<SongInView> songs = new ArrayList<>();
        songTitle = "%" + songTitle + "%";
        String q = "refresh materialized view song_view";
        String p = "refresh view song_view_with_genre";
        //String query = ("select * from song_view s where upper(s.song_title) like upper('%s') ")
        //        .formatted(songTitle);
        String query1 = "select s.song_title,s.artist_name,s.album_title,s.runtime,s.listen_count " +
                "from song_view s left join song_view_with_genre sg on s.song_title=sg.song_title where upper(s.song_title) like upper('%s') order by case when %d = 1 then s.song_title END ".formatted(songTitle,select)
                +sort+", case when %d = 2 then s.artist_name END ".formatted(select)
                +sort+", case when %d = 3 then sg.genre END ".formatted(select)
                +sort+", case when %d = 4 then s.release_date END ".formatted(select)+sort+" ";
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(q);
            ResultSet rs = stmt.executeQuery(query1);
            while(rs.next()){
                SongInView song = new SongInView();
                song.setSongTitle(rs.getString("song_title"));
                song.setArtistName(rs.getString("artist_name"));
                song.setAlbumTitle(rs.getString("album_title"));
                song.setRuntime(rs.getLong("runtime"));
                song.setListenCount(rs.getLong("listen_count"));
                songs.add(song);
            }
        } catch (Exception e) {
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
     * @param artistName
     * @param select
     * @param sort
     * @return
     */
    public List<SongInView> getSongsByArtist(String artistName, int select,String sort) {
        List<SongInView> songs = new ArrayList<>();
        artistName = "%" + artistName + "%";
        String q = "refresh materialized view song_view";
        //String query = ("select * from song_view s where upper(s.artist_name) like upper('%s')").formatted(artistName);
        String query1 = "select s.song_title,s.artist_name,s.album_title,s.runtime,s.listen_count " +
                "from song_view s left join song_view_with_genre sg on s.song_title=sg.song_title where upper(s.artist_name) like upper('%s') order by case when %d = 1 then s.song_title END ".formatted(artistName,select)
                +sort+", case when %d = 2 then s.artist_name END ".formatted(select)
                +sort+", case when %d = 3 then sg.genre END ".formatted(select)
                +sort+", case when %d = 4 then s.release_date END ".formatted(select)+sort+" ";
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(q);
            ResultSet rs = stmt.executeQuery(query1);
            while(rs.next()){
                SongInView song = new SongInView();
                song.setSongTitle(rs.getString("song_title"));
                song.setArtistName(rs.getString("artist_name"));
                song.setAlbumTitle(rs.getString("album_title"));
                song.setRuntime(rs.getLong("runtime"));
                song.setListenCount(rs.getLong("listen_count"));
                songs.add(song);
            }
        } catch (Exception e) {
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
     * @param albumTitle
     * @param select
     * @param sort
     * @return
     */
    public List<SongInView> getSongsByAlbum(String albumTitle, int select,String sort) {
        List<SongInView> songs = new ArrayList<>();
        albumTitle = "%" + albumTitle + "%";
        String q = "refresh materialized view song_view";
        //String query = ("select * from song_view s where upper(s.album_title) like upper('%s')").formatted(albumTitle);
        String query1 = "select s.song_title,s.artist_name,s.album_title,s.runtime,s.listen_count " +
                "from song_view s left join song_view_with_genre sg on s.song_title=sg.song_title where upper(s.album_title) like upper('%s') order by case when %d = 1 then s.song_title END ".formatted(albumTitle,select)
                +sort+", case when %d = 2 then s.artist_name END ".formatted(select)
                +sort+", case when %d = 3 then sg.genre END ".formatted(select)
                +sort+", case when %d = 4 then s.release_date END ".formatted(select)+sort+" ";
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(q);
            ResultSet rs = stmt.executeQuery(query1);
            while(rs.next()){
                SongInView song = new SongInView();
                song.setSongTitle(rs.getString("song_title"));
                song.setArtistName(rs.getString("artist_name"));
                song.setAlbumTitle(rs.getString("album_title"));
                song.setRuntime(rs.getLong("runtime"));
                song.setListenCount(rs.getLong("listen_count"));
                songs.add(song);
            }
        } catch (Exception e) {
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

    //HI SCOTT AND JEREMY

    /**
     *
     * @param genre
     * @param select
     * @param sort
     * @return
     */
    public List<SongInView> getSongsByGenre(String genre, int select,String sort) {
        List<SongInView> songs = new ArrayList<>();
        genre = "%" + genre + "%";
        //String query= "select * from song_view_with_genre s where upper(s.genre) like upper('%s')".formatted(genre);
        String query1 = "select s.song_title,s.artist_name,s.album_title,s.runtime,s.listen_count " +
                "from song_view s left join song_view_with_genre sg on s.song_title=sg.song_title where upper(sg.genre) like upper('%s') order by case when %d = 1 then s.song_title END ".formatted(genre,select)
                +sort+", case when %d = 2 then s.artist_name END ".formatted(select)
                +sort+", case when %d = 3 then sg.genre END ".formatted(select)
                +sort+", case when %d = 4 then s.release_date END ".formatted(select)+sort+" ";
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query1);
            while(rs.next()){
                SongInView song = new SongInView();
                song.setSongTitle(rs.getString("song_title"));
                song.setArtistName(rs.getString("artist_name"));
                song.setAlbumTitle(rs.getString("album_title"));
                song.setRuntime(rs.getLong("runtime"));
                song.setListenCount(rs.getLong("listen_count"));
                songs.add(song);
            }
        } catch (Exception e) {
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
}