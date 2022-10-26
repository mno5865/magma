package com.example.pdmapi.Service;

import com.example.pdmapi.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;

@Service
public class UserService {
    @Autowired
    DataSource dataSource;

    public User getUser(Long userID) {
        String stmt = "SELECT * FROM \"user\" WHERE user_id=%d".formatted(userID);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            User user = new User();
            while(rs.next()) {
                user.setUserID(rs.getLong("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setCreationDate(rs.getDate("creation_date"));
                user.setAccessDate(rs.getTimestamp("access_date"));
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Timestamp getUserSongLastPlayTime(long userId, long songId)
    {
        Timestamp timestamp = null;
        String stmt = "SELECT date_time FROM user_listens_to_song WHERE user_id=%d AND song_id=%d"
                .formatted(userId,songId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            while(rs.next())
            {
                timestamp = rs.getTimestamp("date_time");
            }
            return timestamp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    public User getUserByUsername(String username) {
        String stmt = "SELECT * FROM \"user\" WHERE username='%s'".formatted(username);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            User user = new User();
            while(rs.next()) {
                user.setUserID(rs.getLong("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setCreationDate(rs.getDate("creation_date"));
                user.setAccessDate(rs.getTimestamp("access_date"));
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByEmail(String email) {
        String stmt = "SELECT * FROM \"user\" WHERE email='%s'".formatted(email);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            User user = new User();
            while(rs.next()) {
                user.setUserID(rs.getLong("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setCreationDate(rs.getDate("creation_date"));
                user.setAccessDate(rs.getTimestamp("access_date"));
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int createUser(User user) {
        String stmt = ("INSERT INTO \"user\"(email, username, password, first_name, last_name, creation_date, " +
                "access_date) VALUES('%s', '%s', '%s', '%s', '%s', '%tF', '%tc')").formatted(user.getEmail(),
                user.getUsername(),  user.getPassword(), user.getFirstName(), user.getLastName(),
                user.getCreationDate(), user.getAccessDate());
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
    public int updateUser(Long userId, User user) {
        String stmt = ("UPDATE \"user\" SET " +
                "username='%s', password='%s', email='%s', first_name='%s', last_name='%s', creation_date='%tF'," +
                "access_date='%tc' WHERE user_id=%d").formatted(user.getUsername(), user.getPassword(), user.getEmail(),
                user.getFirstName(), user.getLastName(), user.getCreationDate(), user.getAccessDate(), userId);
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

    public int createUserListensToSong(long userId, long songId)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String stmt = "INSERT INTO user_listens_to_song (user_id, song_id, date_time) VALUES (%d,%d,'%tc')"
                        .formatted(userId,songId,(timestamp),userId,songId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public int updateUserListensToSong(long userId, long songId)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String stmt = "UPDATE user_listens_to_song SET date_time='%tc' WHERE user_id=%d AND song_id=%d"
                .formatted(timestamp,userId,songId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    // DELETE
    public int deleteUser(Long userId) {
        String stmt = "DELETE FROM \"user\" WHERE user_id=%d".formatted(userId);
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

    public int deleteUserListensToSong(long userId, long songId)
    {
        String stmt = "DELETE FROM user_listens_to_song WHERE user_id=%d AND song_id=%d"
                .formatted(userId,songId);
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

    public int createUserListensToAlbum(long userId, long albumId)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String stmt = "INSERT INTO user_listens_to_album (user_id, album_id, date_time) VALUES (%d,%d,'%tc')"
                .formatted(userId,albumId,(timestamp),userId,albumId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public Timestamp getUserAlbumLastPlayTime(long userId, long albumId)
    {
        Timestamp timestamp = null;
        String stmt = "SELECT date_time FROM user_listens_to_album WHERE user_id=%d AND album_id=%d"
                .formatted(userId,albumId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            while(rs.next())
            {
                timestamp = rs.getTimestamp("date_time");
            }
            return timestamp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timestamp;
    }

}
