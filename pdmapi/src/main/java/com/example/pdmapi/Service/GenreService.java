/**
 * file: GenreService.java
 * authors: Gregory Ojiem gro3228,
 *          Melissa Burisky mpb8984,
 *          Mildness Onyekwere mno5865
 */
package com.example.pdmapi.Service;

//imports
import com.example.pdmapi.Model.Album;
import com.example.pdmapi.Model.Genre;
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
 * description: sets up connection to genre related db tables
 *              and performs queries/updates/deletes
 */
@Service
public class GenreService {

    @Autowired
    DataSource dataSource;

    // CREATE

    /**
     * opens connection
     * updates table by executing insert statement to add a
     * genre to the table.
     * closes connection
     * @param genre model that db info is tied to
     * @return return the genre object
     */
    public int createGenre(Genre genre) {
        String stmt = "INSERT INTO genre(name) VALUES ('%s')".formatted(genre.getName());
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
     * opens connection
     * updates table by executing insert statement to add a
     * song_has_genre relationship.
     * closes connection
     * @param genreId refers to the genre in the genre table
     * @param songId refers to the song in the song table
     * @return the amount of rows affected by this insert statement
     */
    public int createSongHasGenre(long genreId, long songId) {
        String st = ("INSERT INTO song_has_genre (genre_id, song_id) VALUES (%d, %d)").formatted(genreId, songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(st);
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

    /**
     * opens connection
     * updates table by executing insert statement to add a
     * album_has_genre relationship.
     * closes connection
     * @param genreId refers to the genre in the genre table
     * @param albumId refers to the album in the album table
     * @return the amount of rows affected by this insert statement
     */
    public int createAlbumHasGenre(long genreId, long albumId) {
        String st = ("INSERT INTO album_has_genre (genre_id, album_id) VALUES (%d, %d)").formatted(genreId, albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(st);
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

    // READ
    /**
     * opens connection
     * queries and returns specific genre result from genre table
     * closes connection
     * @param genreId refers to the genre in the genre table
     * @return the amount of rows affected by this insert statement
     */
    public Genre getGenre(long genreId) {
        String stmt = "SELECT * FROM genre WHERE genre_id=%d".formatted(genreId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            Genre genre = new Genre();
            //maps the result to the object
            while(rs.next()) {
                genre.setGenreID(rs.getLong("genre_id"));
                genre.setName(rs.getString("name"));
            }
            return genre;
        } catch (Exception e) {
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
     * queries and returns all genre results from genre table
     * closes connection
     * @return the amount of rows affected by this insert statement
     */
    public List<Genre> getGenres() {
        String stmt = "SELECT * FROM genre";
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            List<Genre> genres = new ArrayList<>();
            //maps the results to the object
            while(rs.next()) {
                Genre genre = new Genre();
                genre.setGenreID(rs.getLong("genre_id"));
                genre.setName(rs.getString("name"));
                genres.add(genre);
            }
            return genres;
        } catch (Exception e) {
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
     * queries and returns song results from song table
     * given genre substring
     * closes connection
     * @param genreId refers to the genre in the genre table
     * @return the amount of rows affected by this insert statement
     */
    public List<Song> getSongsByGenre(long genreId) {
        List<Song> songs = new ArrayList<>();
        Connection conn = DataSourceUtils.getConnection(dataSource);
        String query = ("SELECT song.song_id, song.title, song.release_date, song.runtime "
                + "FROM song_has_genre "
                + "INNER JOIN song on song_has_genre.song_id = song.song_id "
                + "INNER JOIN genre on song_has_genre.genre_id = genre.genre_id "
                + "WHERE genre.genre_id=%d").formatted(genreId);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            //maps the results to the object
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
     * opens connection
     * queries and returns genre results from genre table
     * given genre substring
     * closes connection
     * @param genreId refers to the genre in the genre table
     * @return the amount of rows affected by this insert statement
     */
    public List<Album> getAlbumsByGenre(long genreId) {
        List<Album> albums = new ArrayList<>();
        Connection conn = DataSourceUtils.getConnection(dataSource);
        String query = ("SELECT album.album_id, album.title, album.release_date, "
                + "FROM album_has_genre "
                + "INNER JOIN song on album_has_genre.song_id = album.album_id "
                + "INNER JOIN genre on album_has_genre.genre_id = genre.genre_id "
                + "WHERE genre.genre_id=%d").formatted(genreId);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                Album album = new Album();
                album.setAlbumID(rs.getLong("album_id"));
                album.setTitle(rs.getString("title"));
                album.setReleaseDate(rs.getDate("release_date"));
                albums.add(album);
            }
        }  catch (SQLException e) {
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

    // UPDATE
    public int updateGenre(Long genreId, Genre genreDetails) {
        String stmt = "UPDATE genre SET name='%s' WHERE genre_id=%d".formatted(genreDetails.getName(), genreId);
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
    public int deleteGenre(Long genreId) {
        String stmt = "DELETE FROM genre WHERE genre_id=%d".formatted(genreId);
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

    public int deleteSongHasGenre(long songId, long genreId){
        String st = ("DELETE FROM song_has_genre WHERE (song_id=%d AND genre_id=%d)")
                .formatted(songId, genreId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(st);
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

    public int deleteAlbumHasGenre(long albumId, long genreId){
        String st = ("DELETE FROM album_has_genre WHERE (album_id=%d AND genre_id=%d)")
                .formatted(albumId, genreId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(st);
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
}