/**
 * File: CollectionService.java
 * CollectionService.java: A public class that sets and gets the attributes for an collection.
 * @author MAGMA
 */
package com.example.pdmapi.Service;

/**
 * Import Statements
 */

import com.example.pdmapi.Model.Collection;
import com.example.pdmapi.Model.User;
import com.example.pdmapi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Collection that defines all properties of what Collection should do.
 */
@Service
public class CollectionService {

    @Autowired
    DataSource dataSource;

    // CREATE

    /**
     *
     * @param collection
     * @return
     */
    public int[] createCollection(Collection collection) {
        String stmt = "INSERT INTO collection(title) VALUES ('%s')".formatted(collection.getTitle());
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

    // READ

    /**
     *
     * @return
     */
    public List<Collection> getCollections() {
        String stmt = "SELECT * FROM collection";
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            List<Collection> collections = new ArrayList<>();
            while(rs.next())
            {
                Collection collection = new Collection();
                collection.setCollectionID(rs.getLong("collection_id"));
                collection.setTitle(rs.getString("title"));
                collections.add(collection);
            }
            return collections;
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
     * @param collectionId
     * @return
     */
    public Collection getCollection(Long collectionId) {
        String stmt = "SELECT * FROM collection WHERE collection_id=%d".formatted(collectionId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            Collection collection = new Collection();
            while(rs.next())
            {
                collection.setCollectionID(rs.getLong("collection_id"));
                collection.setTitle(rs.getString("title"));
            }
            return collection;
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
     * @param userID
     * @param title
     * @return
     */
    public Collection getCollectionByTitleAndUserID(long userID, String title) {
        String stmt = ("SELECT * FROM collection " +
                "INNER JOIN user_creates_collection ucc on collection.collection_id = ucc.collection_id " +
                "WHERE user_id=%d AND title='%s'").formatted(userID, title);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            Collection collection = new Collection();
            while(rs.next()) {
                collection.setCollectionID(rs.getLong("collection_id"));
                collection.setTitle(rs.getString("title"));
            }
            return collection;
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
     *
     * @param collectionId
     * @param collectionDetails
     * @return
     */
    public int updateCollection(Long collectionId, Collection collectionDetails) {
        String stmt = "UPDATE collection SET title='%s' WHERE collection_id=%d".formatted(collectionDetails.getTitle(),collectionId);
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

    /**
     *
     * @param collectionId
     * @return
     */
    public int deleteCollection(Long collectionId) {
        String stmt = "DELETE FROM collection WHERE collection_id=%d".formatted(collectionId);
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

    //CollectionHoldsSong RELATIONSHIP

    /**
     *
     * @param collectionId
     * @param songId
     * @return
     */
    public int createCollectionHoldsSong(long collectionId, long songId) {
        String st = ("INSERT INTO collection_holds_song (collection_id, song_id) VALUES (%d, %d)")
                .formatted(collectionId, songId);
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
     * @param collectionId
     * @param songId
     * @return
     */
    public int deleteCollectionHoldsSong(long collectionId, long songId){
        String st = ("DELETE FROM collection_holds_song WHERE (collection_id=%d AND song_id=%d)")
                .formatted(collectionId, songId);
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
    //CollectionHoldsAlbum RELATIONSHIP

    /**
     *
     * @param collectionId
     * @param albumId
     * @return
     */
    public int createCollectionHoldsAlbum(long collectionId, long albumId) {
        String st = ("INSERT INTO collection_holds_album (collection_id, album_id) VALUES (%d, %d)")
                .formatted(collectionId, albumId);
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
     * @param collectionId
     * @param albumId
     * @return
     */
    public int deleteCollectionHoldsAlbum(long collectionId, long albumId){
        String st = ("DELETE FROM collection_holds_album WHERE (collection_id=%d AND album_id=%d)")
                .formatted(collectionId, albumId);
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
     * @param collectionId
     * @return
     */
    public int getSongCountFromCollection(long collectionId)
    {
        int i = 0;
        String stmt1 = "SELECT COUNT(song.title) AS total_songs " +
                "FROM song,collection_holds_song,collection " +
                "WHERE collection_holds_song.song_id=song.song_id " +
                "AND collection_holds_song.collection_id=collection.collection_id " +
                "AND collection.collection_id=%d".formatted(collectionId);

        String stmt2 = "SELECT COUNT(song.title) AS total_songs " +
                "FROM song,album_contains_song,album,collection_holds_album,collection " +
                "WHERE (album_contains_song.song_id=song.song_id\n" +
                "    AND album_contains_song.album_id=album.album_id) " +
                "AND (collection_holds_album.album_id=album.album_id\n" +
                "    AND collection_holds_album.collection_id=collection.collection_id) " +
                "AND collection.collection_id=%d".formatted(collectionId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement1 = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            Statement statement2 = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs1 = statement1.executeQuery(stmt1);
            ResultSet rs2 = statement2.executeQuery(stmt2);
            while(rs1.next())
            {
                i = i + rs1.getInt("total_songs");
            }
            while(rs2.next())
            {
                i = i + rs2.getInt("total_songs");
            }
            return i;
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
     * @param collectionId
     * @return
     */
    public int getTotalCollectionRuntime(long collectionId)
    {
        int i = 0;
        String stmt1 = "SELECT sum(song.runtime) as total_runtime\n" +
                "FROM song,collection_holds_song,collection\n" +
                "WHERE collection_holds_song.song_id=song.song_id\n" +
                "AND collection_holds_song.collection_id=collection.collection_id\n" +
                "AND collection.collection_id=%d".formatted(collectionId);

        String stmt2 = "SELECT sum(song.runtime) as total_runtime\n" +
                "FROM song,album_contains_song,album,collection_holds_album,collection\n" +
                "WHERE (album_contains_song.song_id=song.song_id\n" +
                "    AND album_contains_song.album_id=album.album_id)\n" +
                "AND (collection_holds_album.album_id=album.album_id\n" +
                "    AND collection_holds_album.collection_id=collection.collection_id)\n" +
                "AND collection.collection_id=%d".formatted(collectionId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement1 = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            Statement statement2 = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs1 = statement1.executeQuery(stmt1);
            ResultSet rs2 = statement2.executeQuery(stmt2);
            while(rs1.next())
            {
                i = i + rs1.getInt("total_runtime");
            }
            while(rs2.next())
            {
                i = i + rs2.getInt("total_runtime");
            }
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        } finally
        {
            try
            {
                conn.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return -1;
    }

    /**
     *
     * @param collectionId
     * @return
     */
    public int deleteAll(long collectionId)
    {
        String stmt1 = "SELECT song_id from collection_holds_song WHERE collection_id=%d"
                .formatted(collectionId);
        String stmt2 = "SELECT album_id from collection_holds_album WHERE collection_id=%d"
                .formatted(collectionId);
        String stmt3 = "DELETE FROM user_creates_collection WHERE collection_id=%d"
                .formatted(collectionId);
        String stmt4 = "DELETE FROM user_listens_to_collection WHERE collection_id=%d"
                .formatted(collectionId);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try
        {
            Statement statement1 = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            Statement statement2 = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            Statement statement3 = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            Statement statement4 = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs1 = statement1.executeQuery(stmt1);
            while(rs1.next())
            {
                int i = deleteCollectionHoldsSong(collectionId,rs1.getLong("song_id"));
                if(i == -1) {return -1;}
            }
            ResultSet rs2 = statement2.executeQuery(stmt2);
            while(rs2.next())
            {
                int i = deleteCollectionHoldsAlbum(collectionId,rs2.getLong("album_id"));
                if(i == -1) {return -1;}
            }
            int i = statement3.executeUpdate(stmt3);
            if(i == -1) {return -1;}
            int j = statement4.executeUpdate(stmt4);
            if(j == -1) {return -1;}
            int k = deleteCollection(collectionId);
            if(k == -1) {return -1;}
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally
        {
            try
            {
                conn.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return -1;
    }
}