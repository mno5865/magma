package com.example.pdmapi.Service;

import com.example.pdmapi.Model.Genre;
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

@Service
public class GenreService {
    final
    DataSource dataSource;

    public GenreService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // CREATE
    public int createGenre(Genre genre) {
        String stmt = "INSERT INTO genre(name) VALUES ('%s')".formatted(genre.getName());
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int createSongHasGenre(long songId, long genreId){
        String st = ("INSERT INTO song_has_genre(song_id, genre_id) WHERE (song_id=%d AND genre_id=%d)")
                .formatted(songId, genreId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(st);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // READ
    public Genre getGenre(Long genreID) {
        String stmt = "SELECT * FROM genre WHERE genre_id=%d".formatted(genreID);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            Genre genre = new Genre();
            while(rs.next()) {
                genre.setGenreID(rs.getLong("genre_id"));
                genre.setName(rs.getString("name"));
            }
            return genre;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Genre> getGenres() {
        String stmt = "SELECT * FROM genre";
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            List<Genre> genres = new ArrayList<>();
            while(rs.next()) {
                Genre genre = new Genre();
                genre.setGenreID(rs.getLong("genre_id"));
                genre.setName(rs.getString("name"));
                genres.add(genre);
            }
            return genres;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Song> getSongsByGenre(long genreId) {
        List<Song> songs = new ArrayList<>();

        String query = ("SELECT song.song_id, song.title, song.release_date, song.runtime "
                + "FROM song_has_genre "
                + "INNER JOIN song on song_has_genre.song_id = song.song_id "
                + "INNER JOIN genre on song_has_genre.genre_id = genre.genre_id "
                + "WHERE genre.genre_id=%d").formatted(genreId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                Song song = new Song();
                song.setSongId(rs.getLong("song_id"));
                song.setTitle(rs.getString("title"));
                song.setReleaseDate(rs.getDate("release_date"));
                song.setRuntime(rs.getTime("runtime"));
                songs.add(song);
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    // UPDATE
    public int updateGenre(Long genreId, Genre genreDetails) {
        String stmt = "UPDATE genre SET name='%s' WHERE genre_id=%d".formatted(genreDetails.getName(), genreId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // DELETE
    public int deleteGenre(Long genreId) {
        String stmt = "DELETE FROM genre WHERE genre_id=%d".formatted(genreId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int deleteSongHasGenre(long songId, long genreId){
        String st = ("DELETE FROM song_has_genre WHERE (song_id=%d AND genre_id=%d)")
                .formatted(songId, genreId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(st);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}