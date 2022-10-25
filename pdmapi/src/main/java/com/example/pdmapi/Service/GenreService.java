package com.example.pdmapi.Service;

import com.example.pdmapi.Model.Genre;
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
public class GenreService {
    @Autowired
    DataSource dataSource;

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
}