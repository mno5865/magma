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
        String st = "INSERT INTO collection(title) VALUES ('%s')".formatted(collection.getTitle());
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
    public List<Collection> getCollections() {
        String stmt = "SELECT * FROM collection";
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            List<Collection> collections = new ArrayList<>();
            while(rs.next()) {
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Collection getCollection(Long collectionId) {
        String stmt = "SELECT * FROM collection WHERE collection_id=%d".formatted(collectionId);
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // UPDATE
    public int updateCollection(Long collectionId, Collection collectionDetails) {
        String st = "UPDATE collection SET title='%s' WHERE collection_id=%d".formatted(collectionDetails.getTitle(),collectionId);
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

    // DELETE
    public int deleteCollection(Long collectionId) {
        String st = "DELETE FROM collection WHERE collection_id=%d".formatted(collectionId);
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