/**
 * file: GenreService.java
 * authors: Gregory Ojiem gro3228,
 *          Melissa Burisky mpb8984,
 *          Mildness Onyekwere mno5865,
 *          Adrian Burgos awb8593
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

    /**
     * dependency injection
     */
    @Autowired
    DataSource dataSource;

    // CREATE

    /**
     * opens connection
     * updates table by executing insert statement to add a
     * genre to the table.
     * closes connection
     * @param genre model that db info is tied to
     * @return the amount of rows affected by this insert statement, if -1 there is a problem
     */
    public int[] createGenre(Genre genre) {
        String stmt = "INSERT INTO genre(name) VALUES ('%s')".formatted(genre.getName());
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            int rowsAffected = statement.executeUpdate(stmt, Statement.RETURN_GENERATED_KEYS);
            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            int key = keys.getInt(1);
            int[] results = {rowsAffected, key};
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new int[2];
    }

    /**
     * opens connection
     * updates table by executing insert statement to add a
     * song_has_genre relationship.
     * closes connection
     * @param genreId refers to the genre in the genre table
     * @param songId refers to the song in the song table
     * @return the amount of rows affected by this insert statement, if -1 there is a problem
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
     * updates table by executing insert statement to add an
     * album_has_genre relationship.
     * closes connection
     * @param genreId refers to the genre in the genre table
     * @param albumId refers to the album in the album table
     * @return the amount of rows affected by this insert statement, if -1 there is a problem
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
     * @return a genre object if it is successfully mapped
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
            //maps the result to the genre
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
     * @return a list of genres if porperly mapped
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
            //maps the results to the genre
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
     * @return list of songs that have given genre
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
            //maps the results to the song
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
     * queries and returns album results from album table
     * given genre substring
     * closes connection
     * @param genreId refers to the genre in the genre table
     * @return list of albums that hae given genre
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
            //maps results to album object
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
    /**
     * opens connection
     * updates the attributes of the genre table for a row with details from a genre object
     * closes connection
     * @param genreId refers to the genre in the genre table
     * @return the amount of rows affected by this insert statement, if -1 there is a problem
     */
    public int updateGenre(long genreId, Genre genreDetails) {
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
    /**
     * opens connection
     * deletes a genre entry from the table
     * closes connection
     * @param genreId refers to the genre in the genre table
     * @return the amount of rows affected by this insert statement, if -1 there is a problem
     */
    public int deleteGenre(long genreId) {
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

    /**
     * deletes row containing a song has genre relationship
     * @param songId refers to the song in the song table
     * @param genreId refers to the genre in the genre table
     * @return the amount of rows affected by this insert statement, if -1 there is a problem
     */
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

    /**
     * deletes row containing a song has genre relationship
     * @param albumId refers to the album in the album table
     * @param genreId refers to the genre in the genre table
     * @return the amount of rows affected by this insert statement, if -1 there is a problem
     */
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

    /**
     * Get the top 5 genres that were played since the 1st of this month
     * @return a list of 5 Genres
     */
    public List<Genre> getTop5Genres() {
        List<Genre> genres = new ArrayList<>();

        String query = ("SELECT genre.name, genre.genre_id, count(\"name\") AS top FROM user_listens_to_song\n" +
                "INNER JOIN song ON song.song_id=user_listens_to_song.song_id\n" +
                "INNER JOIN song_has_genre ON song.song_id=song_has_genre.song_id\n" +
                "INNER JOIN genre ON song_has_genre.genre_id=genre.genre_id\n" +
                "WHERE  date_part('month', user_listens_to_song.date_time) = date_part('month', (SELECT current_timestamp))\n" +
                "AND date_part('year', user_listens_to_song.date_time) = date_part('year', (SELECT current_timestamp))\n" +
                "GROUP BY (genre.name, genre.genre_id)\n" +
                "ORDER BY top desc\n" +
                "limit 5;");
        Connection conn = DataSourceUtils.getConnection(dataSource);

        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Genre genre = new Genre();
                genre.setGenreID(rs.getLong("genre_id"));
                genre.setName(rs.getString("name"));
                genres.add(genre);
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
        return genres;
    }
}