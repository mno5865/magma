/**
 * File: SongService.java
 * SongService.java: A public class that sets and gets the attributes for songservice.
 * @author Gregory Ojiem - gro3228, Melissa Burisky - mpb8984, Mildness Onyekwere - mno5865, Adrian Burgos - awb8593
 */
package com.example.pdmapi.Service;

import com.example.pdmapi.Model.Genre;
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
     * Creates a song within the databse
     * @param song song in the database
     * @return  e.printStackTrace() or -1
     */
    public int[] createSong(Song song) {

        //String that prints a message to create a song
        String stmt = "INSERT INTO song (title,runtime, release_date) VALUES ('%s',%d,'%tF')".formatted(song.getTitle(),song.getRuntime(),song.getReleaseDate());
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //Try Catch Error Checking if song can be created
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

    // READ

    /**
     * Gets a list of songs.
     * @return List of songs in the Database
     */
    public List<Song> getSongs() {
        //String that prints a message to get the songs
        String stmt = "SELECT * FROM song";
        Connection conn = DataSourceUtils.getConnection(dataSource);

        // Try Catch Error Checking if song can be retrieved
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            List<Song> songs = new ArrayList<>();

            // If not then creates a song and then sets the title,runtime,releasedate and adds songs to list
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
     * Gets the song from the database
     * @param songId ID of song
     * @return song or null
     */
    public Song getSong(Long songId) {
        // String that prints a message to get the song
        String stmt = "SELECT * FROM song WHERE song_id=%d".formatted(songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //Try Catch Error Checking if song can be obtained
        try {
            Statement statement = conn.createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            Song song = new Song();
            //Gets song ID,Title,Runtime and releasedate
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
     * Updates song wtihin the database its ID and details
     * @param songId ID of the song
     * @param songDetails details of the song
     * @return statement.executeUpdate(stmt) or -1
     */
    public int updateSong(Long songId, Song songDetails) {
        //String that prints a message to update a song
        String stmt = "UPDATE song SET title='%s',runtime=%d,release_date='%tF' WHERE song_id=%d"
                .formatted(songDetails.getTitle(),songDetails.getRuntime(),songDetails.getReleaseDate(), songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //Try Catch Error Checking if song can be updated
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
     * Deletes a song in the database
     * @param songId ID of song
     * @return statement.executeUpdate(stmt) or -1
     */
    public int deleteSong(Long songId) {
        //String that prints a message to delete the song
        String stmt = "DELETE FROM song WHERE song_id=%d".formatted(songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //Try Catch Error Checking if song can be deleted
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
     * Gets a collection of songs
     * @param collectionId ID of collection
     * @return songs or null
     */
    public List<Song> getCollectionSongs(long collectionId) {
        // String that prints a message to get a list of
        String stmt = "SELECT song.song_id,song.title,song.runtime,song.release_date\n" +
                "FROM song,collection_holds_song\n" +
                "WHERE song.song_id=collection_holds_song.song_id\n" +
                "AND collection_holds_song.collection_id=%d".formatted(collectionId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        List<Song> songs = new ArrayList<>();

        // Try Catch Error Checking if songs list can be obtained
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);

            //If not create a new song and set its SongID, Titlte, runtime, release date and then add it to the songs database
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
     * Gets all songs by its title
     * @param songTitle title of the song
     * @param select selecting of the song
     * @param sort song that is sorted
     * @return list of songs
     */
    public List<SongInView> getSongsByTitle(String songTitle, int select,String sort) {
        List<SongInView> songs = new ArrayList<>();
        songTitle = "%" + songTitle + "%";
        String q = "refresh materialized view song_view";
        String p = "refresh view song_view_with_genre";
        //String query = ("select * from song_view s where upper(s.song_title) like upper('%s') ")
        //        .formatted(songTitle);
        String query1 = ("select s.song_id, s.album_id, s.song_title, " +
                "s.artist_name, s.album_title, s.runtime, s.listen_count " +
                "from song_view s left join song_view_with_genre sg on " +
                "s.song_title=sg.song_title where upper(s.song_title) " +
                "like upper('%s') order by case when %d = 1 then s.song_title END ").formatted(songTitle,select)
                + sort +", case when %d = 2 then s.artist_name END ".formatted(select)
                + sort +", case when %d = 3 then sg.genre END ".formatted(select)
                + sort +", case when %d = 4 then s.song_release_date END ".formatted(select) + sort + " ";
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //  Try Catch Error Checking if song can be obtained from its list
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(q);
            ResultSet rs = stmt.executeQuery(query1);

            // Creates new song in the view and adds all of its details to the specific song
            while(rs.next()){
                SongInView song = new SongInView();
                song.setSongId(rs.getLong("song_id"));
                song.setAlbumId(rs.getLong("album_id"));
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
     * Get all the songs by a specfic aritst
     * @param artistName Name of the artist
     * @param select songs that are selected
     * @param sort songs that are sorted
     * @return songs
     */
    public List<SongInView> getSongsByArtist(String artistName, int select,String sort) {
        List<SongInView> songs = new ArrayList<>();
        artistName = "%" + artistName + "%";
        String q = "refresh materialized view song_view";
        //String query = ("select * from song_view s where upper(s.artist_name) like upper('%s')").formatted(artistName);
        String query1 = ("select s.song_id, s.album_id, s.song_title, " +
                        "s.artist_name, s.album_title, s.runtime, s.listen_count " +
                        "from song_view s left join song_view_with_genre sg " +
                        "on s.song_title=sg.song_title where upper(s.artist_name) " +
                        "like upper('%s') order by case when %d = 1 then s.song_title END ").formatted(artistName,select)
                + sort +", case when %d = 2 then s.artist_name END ".formatted(select)
                + sort +", case when %d = 3 then sg.genre END ".formatted(select)
                + sort +", case when %d = 4 then s.release_date END ".formatted(select) + sort + " ";
        Connection conn = DataSourceUtils.getConnection(dataSource);
        //  Try Catch Error Checking if song can be obtained by artist
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(q);
            ResultSet rs = stmt.executeQuery(query1);

            //If not create a song in view and then set its title and other details
            while(rs.next()){
                SongInView song = new SongInView();
                song.setSongId(rs.getLong("song_id"));
                song.setAlbumId(rs.getLong("album_id"));
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
     * Get the songs in a specfic album
     * @param albumTitle title of the album
     * @param select songs that are selected
     * @param sort songs that are sort
     * @return list of songs in album
     */
    public List<SongInView> getSongsByAlbum(String albumTitle, int select,String sort) {
        List<SongInView> songs = new ArrayList<>();
        albumTitle = "%" + albumTitle + "%";
        String q = "refresh materialized view song_view";
        //String query = ("select * from song_view s where upper(s.album_title) like upper('%s')").formatted(albumTitle);
        String query1 = ("select s.song_id, s.album_id, s.song_title, " +
                        "s.artist_name, s.album_title, s.runtime, s.listen_count " +
                        "from song_view s left join song_view_with_genre sg " +
                        "on s.song_title=sg.song_title where upper(s.album_title) " +
                        "like upper('%s') order by case when %d = 1 then s.song_title END ").formatted(albumTitle,select)
                + sort +", case when %d = 2 then s.artist_name END ".formatted(select)
                + sort +", case when %d = 3 then sg.genre END ".formatted(select)
                + sort +", case when %d = 4 then s.release_date END ".formatted(select) + sort +" ";
        Connection conn = DataSourceUtils.getConnection(dataSource);

        // Try Catch Error Checking if song can be obtained from an album
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(q);
            ResultSet rs = stmt.executeQuery(query1);
            while(rs.next()){
                SongInView song = new SongInView();
                song.setSongId(rs.getLong("song_id"));
                song.setAlbumId(rs.getLong("album_id"));
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
     * Songs being returned by a specfic genre in the database
     * @param genre genre of the song
     * @param select songs that were selected
     * @param sort songs that were sorted
     * @return list of Songs
     */
    public List<SongInView> getSongsByGenre(String genre, int select,String sort) {
        List<SongInView> songs = new ArrayList<>();
        genre = "%" + genre + "%";
        //String query= "select * from song_view_with_genre s where upper(s.genre) like upper('%s')".formatted(genre);
        String query1 = ("select s.song_id, s.album_id, s.song_title, " +
                        "s.artist_name, s.album_title, s.runtime, s.listen_count " +
                        "from song_view s left join song_view_with_genre sg " +
                        "on s.song_title=sg.song_title where upper(sg.genre) " +
                        "like upper('%s') order by case when %d = 1 then s.song_title END ").formatted(genre,select)
                + sort +", case when %d = 2 then s.artist_name END ".formatted(select)
                + sort +", case when %d = 3 then sg.genre END ".formatted(select)
                + sort +", case when %d = 4 then s.release_date END ".formatted(select) + sort + " ";
        Connection conn = DataSourceUtils.getConnection(dataSource);

        //  Try Catch Error Checking if song can be obtained by genre
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query1);

            //If song does not exist in that genre create a song and its details in the genre.
            while(rs.next()){
                SongInView song = new SongInView();
                song.setSongId(rs.getLong("song_id"));
                song.setAlbumId(rs.getLong("album_id"));
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
     * Gets a user's top 50 recommended songs based on the
     * songs the people they follows listen to.
     * @param userId (long) the user's identification number
     * @return A list of the recommended songs
     */
    public List<Song> topFiftySongsOfFollowing(long userId)
    {
        List<Song> songs = new ArrayList<>();
        String stmt = "SELECT song.song_id,song.title,song.release_date,song.runtime,listen_count " +
                "from song inner join " +
                "(SELECT s.song_id,COUNT(*) as listen_count " +
                "FROM (SELECT song_id " +
                "FROM user_listens_to_song " +
                "WHERE user_id in ( " +
                "SELECT user_two_id as user_id " +
                "FROM user_follows_user " +
                "WHERE user_one_id=%d)) as s ".formatted(userId) +
                "group by s.song_id) as sg " +
                "on sg.song_id = song.song_id " +
                "order by sg.listen_count DESC " +
                "limit 50";
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            while(rs.next())
            {
                Song song = new Song();
                song.setSongId(rs.getLong("song_id"));
                song.setTitle(rs.getString("title"));
                song.setReleaseDate(rs.getDate("release_date"));
                song.setRuntime(rs.getLong("runtime"));
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
     * Get the 50 songs with the most listens in the past 30 days
     * @return a list of SongInViews containing the top 50 songs' title, artist, and listen count
     */
    public List<SongInView> getTop50Songs() {
        List<SongInView> songs = new ArrayList<>();

        String query = ("SELECT * FROM\n" +
                "(select distinct on (s.title) s.song_id, s.title,\n" +
                "                              s.runtime, s.release_date,\n" +
                "                              art.artist_id, art.name,\n" +
                "    (select count(c) from song as c\n" +
                "    inner join user_listens_to_song ults on c.song_id = ults.song_id\n" +
                "    where c.song_id = s.song_id and ults.date_time >= CURRENT_TIMESTAMP - interval '30 days') as listen_count\n" +
                "    from song as s\n" +
                "    left outer join artist_releases_song ars on s.song_id = ars.song_id\n" +
                "    left outer join artist art on ars.artist_id = art.artist_id) topInfo\n" +
                "order by listen_count desc\n" +
                "limit 50");
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                SongInView song = new SongInView();
                song.setSongId(rs.getLong("song_id"));
                song.setSongTitle(rs.getString("title"));
                song.setRuntime(rs.getLong("runtime"));
                song.setReleaseDate(rs.getDate("release_date"));
                song.setArtistId(rs.getLong("artist_id"));
                song.setArtistName(rs.getString("name"));
                song.setListenCount(rs.getInt("listen_count"));
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

    public Song getRandomSong() {
        String stmt = "SELECT * FROM song ORDER BY random() LIMIT 1";
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

    public SongInView getSongInView(long songId){
        String q = "refresh materialized view song_view";
        String stmt = ("select distinct * from song_view " +
                "where song_id = %d").formatted(songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);

        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement.executeUpdate(q);
            ResultSet rs = statement.executeQuery(stmt);
            SongInView song = new SongInView();
            while (rs.next()) {
                song.setSongId(rs.getLong("song_id"));
                song.setSongTitle(rs.getString("song_title"));
                song.setRuntime(rs.getLong("runtime"));
                song.setReleaseDate(rs.getDate("song_release_date"));
                song.setArtistId(rs.getLong("artist_id"));
                song.setArtistName(rs.getString("artist_name"));
                song.setListenCount(rs.getInt("listen_count"));
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
}