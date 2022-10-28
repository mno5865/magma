package com.example.pdmapi.Service;

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

@Service
public class SongService {

    @Autowired
    DataSource dataSource;

    // CREATE
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
    public List<SongInView> getSongsByTitle(String songTitle) {
        List<SongInView> songs = new ArrayList<>();
        songTitle = "%" + songTitle + "%";
        String q = "refresh materialized view song_view";
        String query = ("select * from song_view s where upper(s.song_title) like upper('%s')").formatted(songTitle);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(q);
            ResultSet rs = stmt.executeQuery(query);
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

    public List<SongInView> getSongsByArtist(String artistName) {
        List<SongInView> songs = new ArrayList<>();
        artistName = "%" + artistName + "%";
        String q = "refresh materialized view song_view";
        String query = ("select * from song_view s where upper(s.artist_name) like upper('%s')").formatted(artistName);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(q);
            ResultSet rs = stmt.executeQuery(query);
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

    public List<SongInView> getSongsByAlbum(String albumTitle) {
        List<SongInView> songs = new ArrayList<>();
        albumTitle = "%" + albumTitle + "%";
        String q = "refresh materialized view song_view";
        String query = ("select * from song_view s where upper(s.album_title) like upper('%s')").formatted(albumTitle);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(q);
            ResultSet rs = stmt.executeQuery(query);
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

    public List<SongInView> getSongsByGenre(String genre) {
        List<SongInView> songs = new ArrayList<>();
        genre = "%" + genre + "%";
        String query= "select * from song_view_with_genre s where upper(s.genre) like upper('%s')".formatted(genre);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
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