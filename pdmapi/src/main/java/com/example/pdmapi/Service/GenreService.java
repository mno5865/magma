package com.example.pdmapi.Service;

import com.example.pdmapi.Model.Genre;
import com.example.pdmapi.Repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@Service
public class GenreService {

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    DataSource dataSource;

    public Genre getGenre(Long genreID) {
        String query = "SELECT * FROM genre WHERE genre_id=" + genreID;
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(query);
            Genre genre = new Genre();
            while(rs.next()) {
                genre.setGenreID(rs.getLong("genre_id"));
                genre.setName(rs.getString("name"));
            }
            return genre;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }


    // CREATE
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    // READ
    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    // UPDATE
    public Genre updateGenre(Long genreId, Genre genreDetails) {
        Genre genre = genreRepository.findById(genreId).get();

        genre.setGenreID(genreId);
        genre.setName(genreDetails.getName());

        return genreRepository.save(genre);
    }

    // DELETE
    public void deleteGenre(Long genreId) {
        genreRepository.deleteById(genreId);
    }
}