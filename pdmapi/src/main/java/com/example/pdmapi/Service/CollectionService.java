package com.example.pdmapi.Service;

import com.example.pdmapi.Model.Collection;
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

@Service
public class CollectionService {

    @Autowired
    DataSource dataSource;

    // CREATE
    public int createCollection(Collection collection) {
        String stmt = "INSERT INTO collection(title) VALUES ('%s')".formatted(collection.getTitle());
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

    // READ
    public List<Collection> getCollections() {
        String stmt = "SELECT * FROM collection";
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
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
        }
        return null;
    }

    public Collection getCollection(Long collectionId) {
        String stmt = "SELECT * FROM collection WHERE collection_id=%d".formatted(collectionId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
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
        }
        return null;
    }

    // UPDATE
    public int updateCollection(Long collectionId, Collection collectionDetails) {
        String stmt = "UPDATE collection SET title='%s' WHERE collection_id=%d".formatted(collectionDetails.getTitle(),collectionId);
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
    public int deleteCollection(Long collectionId) {
        String stmt = "DELETE FROM collection WHERE collection_id=%d".formatted(collectionId);
        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    //CollectionHoldsSong RELATIONSHIP
    public int createCollectionHoldsSong(long collectionId, long songId) {
        String st = ("INSERT INTO collection_holds_song (collection_id, song_id) VALUES (%d, %d)")
                .formatted(collectionId, songId);
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

    public int deleteCollectionHoldsSong(long collectionId, long songId){
        String st = ("DELETE FROM collection_holds_song WHERE (collection_id=%d AND song_id=%d)")
                .formatted(collectionId, songId);
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
    //CollectionHoldsAlbum RELATIONSHIP
    public int createCollectionHoldsAlbum(long collectionId, long albumId) {
        String st = ("INSERT INTO collection_holds_album (collection_id, album_id) VALUES (%d, %d)")
                .formatted(collectionId, albumId);
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

    public int deleteCollectionHoldsAlbum(long collectionId, long albumId){
        String st = ("DELETE FROM collection_holds_album WHERE (collection_id=%d AND album_id=%d)")
                .formatted(collectionId, albumId);
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