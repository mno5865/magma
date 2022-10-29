/**
 * File: UserService.java
 * UserService.java: A public class that sets and gets the attributes for userservice.
 * @author MAGMA
 */
package com.example.pdmapi.Service;

/**
 * Import Statements
 */
import com.example.pdmapi.Model.Collection;
import com.example.pdmapi.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service User that defines all properties of what User should do.
 */
@Service
public class UserService {
    @Autowired
    DataSource dataSource;

    public User getUser(Long userID) {
        String stmt = "SELECT * FROM \"user\" WHERE user_id=%d".formatted(userID);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
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
     * @param userId
     * @param songId
     * @return
     */
    public Timestamp getUserSongLastPlayTime(long userId, long songId)
    {
        Timestamp timestamp = null;
        String stmt = "SELECT date_time FROM user_listens_to_song WHERE user_id=%d AND song_id=%d"
                .formatted(userId,songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
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
     * @param username
     * @return
     */
    public User getUserByUsername(String username) {
        String stmt = "SELECT * FROM \"user\" WHERE username='%s'".formatted(username);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
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
     * @param email
     * @return
     */
    public User getUserByEmail(String email) {
        String stmt = "SELECT * FROM \"user\" WHERE email='%s'".formatted(email);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
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
     * @param user
     * @return
     */
    public int createUser(User user) {
        String stmt = ("INSERT INTO \"user\"(email, username, password, first_name, last_name, creation_date, " +
                "access_date) VALUES('%s', '%s', '%s', '%s', '%s', '%tF', '%tc')").formatted(user.getEmail(),
                user.getUsername(),  user.getPassword(), user.getFirstName(), user.getLastName(),
                user.getCreationDate(), user.getAccessDate());
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

    // UPDATE

    /**
     *
     * @param userId
     * @param user
     * @return
     */
    public int updateUser(Long userId, User user) {
        String stmt = ("UPDATE \"user\" SET " +
                "username='%s', password='%s', email='%s', first_name='%s', last_name='%s', creation_date='%tF'," +
                "access_date='%tc' WHERE user_id=%d").formatted(user.getUsername(), user.getPassword(), user.getEmail(),
                user.getFirstName(), user.getLastName(), user.getCreationDate(), user.getAccessDate(), userId);
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
     * @param userId
     * @param songId
     * @return
     */
    public int createUserListensToSong(long userId, long songId)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String stmt = "INSERT INTO user_listens_to_song (user_id, song_id, date_time) VALUES (%d,%d,'%tc')"
                .formatted(userId,songId,(timestamp),userId,songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        } catch (Exception e)
        {
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
     * @param userId
     * @param songId
     * @return
     */
    public int updateUserListensToSong(long userId, long songId)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String stmt = "UPDATE user_listens_to_song SET date_time='%tc' WHERE user_id=%d AND song_id=%d"
                .formatted(timestamp,userId,songId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        } catch (Exception e)
        {
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
     * @param userId
     * @return
     */
    public int deleteUser(Long userId) {
        String stmt = "DELETE FROM \"user\" WHERE user_id=%d".formatted(userId);
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
     * @param userId
     * @param songId
     * @return
     */
    public int deleteUserListensToSong(long userId, long songId)
    {
        String stmt = "DELETE FROM user_listens_to_song WHERE user_id=%d AND song_id=%d"
                .formatted(userId,songId);
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

    //UserCreatesCollection RELATIONSHIP

    /**
     *
     * @param userId
     * @param collectionId
     * @return
     */
    public int createUserCreatesCollection(long userId, long collectionId) {
        String st = ("INSERT INTO user_creates_collection (user_id, collection_id) VALUES (%d, %d)").formatted(userId,
                collectionId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
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
     * @param userId
     * @return
     */
    public List<Collection> getCollectionsByUserID(long userId) {
        List<Collection> collections = new ArrayList<>();
        String query = ("SELECT collection.collection_id, collection.title "
                + "FROM user_creates_collection "
                + "INNER JOIN \"user\" on user_creates_collection.user_id = \"user\".user_id "
                + "INNER JOIN collection on user_creates_collection.collection_id = collection.collection_id "
                + "WHERE \"user\".user_id=%d " +
                "ORDER BY collection.title ASC").formatted(userId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                Collection collection = new Collection();
                collection.setCollectionID(rs.getLong("collection_id"));
                collection.setTitle(rs.getString("title"));
                collections.add(collection);
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
        return collections;
    }

    /**
     *
     * @param userId
     * @param collectionId
     * @return
     */
    public int deleteUserCreatesCollection(long userId, long collectionId){
        String st = ("DELETE FROM user_creates_collection WHERE (user_id=%d AND collection_id=%d)")
                .formatted(userId, collectionId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
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

    //user_listens_album RELATIONSHIP
    public int createUserListensToAlbum(long userId, long albumId) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String stmt = "INSERT INTO user_listens_to_album (user_id, album_id, date_time) VALUES (%d,%d,'%tc')"
                .formatted(userId,albumId,(timestamp),userId,albumId);
        String stmt2 = "SELECT song_id FROM album_contains_song WHERE album_id=%d".formatted(albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement2 = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement2.executeQuery(stmt2);
            while(rs.next())
            {
                timestamp = new Timestamp(System.currentTimeMillis());
                long songId = rs.getLong("song_id");
                String stmt3 = "INSERT INTO user_listens_to_song (song_id,user_id,date_time) VALUES (%d,%d,'%tc')"
                        .formatted(songId,userId,timestamp);
                Statement statement3 = conn.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                statement3.executeUpdate(stmt3);
            }
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

    public Timestamp getUserAlbumLastPlayTime(long userId, long albumId) {
        Timestamp timestamp = null;
        String stmt = "SELECT date_time FROM user_listens_to_album WHERE user_id=%d AND album_id=%d"
                .formatted(userId,albumId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            while(rs.next()) {
                timestamp = rs.getTimestamp("date_time");
            }
            return timestamp;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return timestamp;
    }
    //UserListenToCollection RELATIONSHIP
    //CREATE
    public int createUserListensToCollection(long userId, long collectionId) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String stmt = "INSERT INTO user_listens_to_collection (user_id, collection_id, date_time) VALUES (%d,%d,'%tc')"
                .formatted(userId, collectionId, (timestamp));
        String stmt2 = "SELECT album_id FROM collection_holds_album WHERE collection_id=%d".formatted(collectionId);
        String stmt3 = "SELECT song_id FROM collection_holds_song WHERE collection_id=%d".formatted(collectionId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            Statement statement2 = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs1 = statement2.executeQuery(stmt2);
            while(rs1.next())
            {
                long albumId = rs1.getLong("album_id");
                createUserListensToAlbum(userId,albumId);
            }
            Statement statement3 = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs2 = statement3.executeQuery(stmt3);
            while(rs2.next())
            {
                long songId = rs2.getLong("song_id");
                createUserListensToSong(userId,songId);
            }
            return statement.executeUpdate(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // READ
    public Timestamp getUserCollectionPlayTime(long userId, long collectionId)
    {
        Timestamp timestamp = null;
        String stmt = "SELECT date_time FROM user_listens_to_collection WHERE user_id=%d AND collection_id=%d"
                .formatted(userId, collectionId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            while(rs.next()) {
                timestamp = rs.getTimestamp("date_time");
            }
            return timestamp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    // DELETE
    public int deleteUserListensToCollection(long userId, long collectionId)
    {
        String stmt = "DELETE FROM user_listens_to_collection WHERE user_id=%d AND collection_id=%d"
                .formatted(userId, collectionId);
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
    //UserFollowersUser RELATIONSHIP
    public int createUserFollowsUser(long user1Id, long user2Id) {
        String st = ("INSERT INTO user_follows_user(user_one_id, user_two_id) VALUES (%d, %d)").
                formatted(user1Id, user2Id);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            return stmt.executeUpdate(st);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // READ
    public List<User> getUsersFollowing(long userId) {
        List<User> friends = new ArrayList<>();

        String query = ("SELECT * FROM \"user\" AS u " +
                "INNER JOIN user_follows_user ufu ON u.user_id = ufu.user_two_id " +
                "WHERE ufu.user_one_id=%d;").formatted(userId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                User user = new User();
                user.setUserID(rs.getLong("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setCreationDate(rs.getDate("creation_date"));
                user.setAccessDate(rs.getTimestamp("access_date"));
                friends.add(user);
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return friends;
    }

    // DELETE
    public int deleteUserFollowsUser(long user1Id, long user2Id) {
        String st = ("DELETE FROM user_follows_user WHERE (user_one_id=%d AND user_two_id=%d)")
                .formatted(user1Id, user2Id);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return stmt.executeUpdate(st);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}