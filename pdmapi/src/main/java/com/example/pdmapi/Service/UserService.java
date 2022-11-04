/**
 * file: UserService.java
 * authors: Gregory Ojiem - gro3228, Melissa Burisky - mpb8984, Mildness Onyekwere - mno5865
 * description: A service that runs SQL statements and queries to modify or retrieve from the database
 */

package com.example.pdmapi.Service;

import com.example.pdmapi.Model.Artist;
import com.example.pdmapi.Model.Collection;
import com.example.pdmapi.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    /**
     * Provides the connection to the database
     */
    @Autowired
    DataSource dataSource;

    /**
     * Gets a user using their user id
     * @param userID The id of the user
     * @return A user object corresponding to the id
     */
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
     * Gets the last time a user listened to a song, deprecated function
     * @param userId The id of the user
     * @param songId The id of the song
     * @return A timestamp with the time the user last listened to the song
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
     * Gets a user with their username
     * @param username The username of the user
     * @return A user object corresponding to the username
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
     * Gets a user by email
     * @param email The email of the user
     * @return A user whose email corresponds to the email given
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
     * Creates a new user in the database
     * @param user The user object that maps to the database user table
     * @return 1 if successful, -1 otherwise
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

    /**
     * Updates a user's information
     * @param userId The id of the user whose information is being updated
     * @param user A user object to replace the old user's information with
     * @return 1 if successful, -1 otherwise
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
     * Creates a new user listens to song relationship
     * @param userId The id of the user
     * @param songId The id of the song
     * @return 1 if successful, -1 otherwise
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
     * Deletes a user in the database
     * @param userId The id of the user
     * @return 1 if successful, -1 otherwise
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
     * Deletes a user listens to song relationship, deprecated
     * @param userId The id of the user
     * @param songId The id of the song
     * @return 1 if successful, -1 otherwise
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
     * Ceates a new user creates collection relationship
     * @param userId The id of the user
     * @param collectionId The id of the collection
     * @return 1 if successful, -1 otherwise
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
     * Gets a list of collection that belong to a user
     * @param userId The id of the user
     * @return A list of collections belonging to the user
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

    //TODO make this also delete the collection automatically?
    /**
     * Deletes a user creates collection relationship
     * @param userId The id of the user
     * @param collectionId The id of the collection
     * @return 1 if successful, -1 otherwise
     */
    public int deleteUserCreatesCollection(long userId, long collectionId) {
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
    /**
     * Creates a user listens to album relationship
     * @param userId The id of the user
     * @param albumId The id of the album
     * @return 1 if successful, -1 otherwise
     */
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

    //UserListenToCollection RELATIONSHIP
    /**
     * Creates a user listens to collection relationship, also updating user listens
     * @param userId The id of the user
     * @param collectionId The id of the collection
     * @return 1 if successful, -1 otherwise
     */
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

    /**
     * Deletes a user listens to collection relationship, deprecated
     * @param userId The id of the user
     * @param collectionId The id of the collection
     * @return 1 if successful, -1 otherwise
     */
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

    /**
     * Creates a user follows user relationship
     * @param user1Id The id of the user who's following a different user
     * @param user2Id The id of the user being followed
     * @return 1 if successful, -1 otherwise
     */
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

    /**
     * Get a list of the users who a user is following
     * @param userId The id of the user
     * @return A list of users
     */
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

    /**
     * Deletes a user follows user relationship
     * @param user1Id The user who's unfollowing the other user
     * @param user2Id The user who's being unfollowed
     * @return 1 if successful, -1 otherwise
     */
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

    /**
     * Returns the number of Collections based off the User logged in.
     * @param userID The id of the user
     */
    public int countCollectionsByUserID(long userID){
        String query = ("SELECT COUNT(collection_id) as num_colls FROM user_creates_collection WHERE user_id = $id").formatted(userID);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        int numColls=0;
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                numColls = rs.getInt("num_colls");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return numColls;

    }

    /**
     * Returns the number of Followers based off the User logged in.
     * @param userID The id of the user
     */
    public int countNumOfFollowers(long userID){
        String query = ("SELECT COUNT(user_one_id) as num FROM user_follows_user WHERE user_two_id = $id").formatted(userID);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        int numColls=0;
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                numColls = rs.getInt("num");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return numColls;
    }

    /**
     * Returns the number of Following based off the User logged in.
     * @param userID The id of the user
     */
    public int countNumOfFollowing(long userID){
        String query = ("SELECT COUNT(user_two_id) as num FROM user_follows_user WHERE user_one_id = $id").formatted(userID);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        int numColls=0;
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                numColls = rs.getInt("num");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return numColls;

    }

    /**
     * Returns the top ten artists played by the User logged in.
     * @param userID The id of the user
     */
    public List<String> topTenArtistsByPlays(long userID){
        List<String> result = new ArrayList<>();
        String query = ("SELECT  a2.name as name, Count(s.song_id) as count FROM user_listens_to_song as s\n" +
                "JOIN artist_releases_song as a on a.song_id = s.song_id AND s.user_id = 2\n" +
                "JOIN artist as a2 on a.artist_id = a2.artist_id\n" +
                "GROUP BY a2.name\n" +
                "ORDER BY Count(s.song_id) DESC\n" +
                "LIMIT 10").formatted(userID);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        int numColls=0;
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                result.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;

    }

    /**
     * Returns the top ten artists by collections played by the User logged in.
     * @param userID The id of the user
     */
    public List<String> topTenArtistsByCollections(long userID){
        List<String> result = new ArrayList<>();
        String query = ("SELECT  a3.name as name , Count(a2.song_id) as count FROM user_creates_collection as s\n" +
                "JOIN collection_holds_song as a on a.collection_id = s.collection_id AND s.user_id = $id\n" +
                "Join artist_releases_song as a2 on a2.song_id = a.song_id\n" +
                "JOIN artist as a3 on a3.artist_id = a2.artist_id\n" +
                "GROUP BY a3.name\n" +
                "ORDER BY count DESC\n" +
                "LIMIT 10").formatted(userID);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        int numColls=0;
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                result.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Returns the top ten artists by plays and collections played by the User logged in.
     * @param userID The id of the user
     */
    public void topTenArtistsByPlaysAndCollections(long userID){
        List<String> result = new ArrayList<>();
        String query = ("");
        Connection conn = DataSourceUtils.getConnection(dataSource);
        int numColls=0;
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                result.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}