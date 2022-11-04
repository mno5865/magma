/**
 * file: UserController.java
 * authors: Gregory Ojiem - gro3228, Melissa Burisky - mpb8984, Mildness Onyekwere - mno5865
 * description: A controller that maps HTTP requests to our services that run the database commands
 */

package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.Collection;
import com.example.pdmapi.Model.User;
import com.example.pdmapi.Service.UserService;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;


/**
 * description: controller that creates the api endpoint for accessing db data related to user
 */
@RestController
@RequestMapping("/api")
public class UserController {

    /**
     * service that provides connection from endpoint to db
     */
    @Autowired
    private UserService userService;

    /**
     * Gets a user using their id
     * @param id The id of the use
     * @return HTTP OK and the user
     */
    @CrossOrigin
    @GetMapping("/users/id/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Finds a user by their username
     * @param username The username of the user to find
     * @return An HTTP response OK and the user
     */
    @CrossOrigin
    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Finds a user by their email
     * @param email The email of the user to find
     * @return An HTTP response OK and the user
     */
    @CrossOrigin
    @GetMapping("/users/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Returns the last time a user has listened to a song
     * @param userId The id of the user
     * @param songId The id of the song
     * @return An HTTP OK if the timestamp isn't null along with the timestamp, BAD_REQUEST otherwise
     */
    @CrossOrigin
    @GetMapping(value = "/users/{userId}/songs/{songId}")
    public ResponseEntity<Timestamp> getUserSongLastPlayTime(@PathVariable long userId, @PathVariable long songId)
    {
        Timestamp timestamp = userService.getUserSongLastPlayTime(userId,songId);
        if(timestamp != null)
        {
            return new ResponseEntity<>(timestamp,HttpStatus.OK);
        } else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Creates a new user entity in the database, and then updates their password to a hashed version based on
     * thee user's ID
     * @param newUser A model of the user to create
     * @return HTTP CREATED response
     */
    @CrossOrigin
    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody User newUser) {
        User nullUser = new User();
        int[] results = userService.createUser(nullUser);
        //GENERATING RANDOM VALUE
        Random rand = new Random(results[1]);
        int randInt = rand.nextInt(1000000000);
        String hashedPass = Hashing.sha256().hashString(newUser.getPassword()+randInt, StandardCharsets.UTF_8)
                .toString();
        //UPDATING USER PASSWORD TO HASHED VAL
        newUser.setPassword(hashedPass);
        userService.updateUser((long) results[1], newUser);
        if (results[0] == 1 && results[1] != 0) {
            return new ResponseEntity<>(results[1], HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(results[1], HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates a user in the database to match the updatedUser param
     * @param id The id of the user to update
     * @param updatedUser The new user information
     * @return An HTTP OK response
     */
    @CrossOrigin
    @PutMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@PathVariable long id, @RequestBody User updatedUser) {
        userService.updateUser(id, updatedUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Creates a new listens to relationship between a user and a song
     * @param userId The id of the user
     * @param songId The id of the song
     * @return HTTP OK if successful, BAD_REQUEST otherwise
     */
    @CrossOrigin
    @PostMapping(value = "/users/{userId}/songs/{songId}")
    public ResponseEntity<Integer> createUserListensToSong(@PathVariable long userId, @PathVariable long songId) {
        int rowsAffected = userService.createUserListensToSong(userId,songId);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes a user in the database
     * @param id The id of the user to delete
     * @return HTTP OK response
     */
    @CrossOrigin
    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes a user listens to song relationship, deprecated
     * @param userId The id of the user
     * @param songId The id of the song
     * @return HTTP OK if successful, HTTP BAD_REQUEST otherwise
     */
    @CrossOrigin
    @DeleteMapping(value = "/users/{userId}/songs/{songId}")
    public ResponseEntity<Integer> deleteUserListensToSong(@PathVariable long userId, @PathVariable long songId) {
        int rowsAffected = userService.deleteUserListensToSong(userId,songId);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
        return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }

    //UserCreatesCollection RELATIONSHIP

    /**
     * Creates a user creates collection relationship
     * @param userId The id of the user
     * @param collectionId The id of the collection
     * @return HTTP CREATED if successful, HTTP BAD_REQUEST otherwise
     */
    @CrossOrigin
    @PostMapping(value = "/users/{userId}/collections/{collectionId}")
    public ResponseEntity createUserCreatesCollection(@PathVariable long userId, @PathVariable long collectionId) {
        int rowsAffected = userService.createUserCreatesCollection(userId, collectionId);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Gets a list of collections that belong to a certain user
     * @param userId The id of the user
     * @return HTTP OK if successful and the list of collections, HTTP NOT_FOUND otherwise
     */
    @CrossOrigin
    @GetMapping("/users/{userId}/collections")
    public ResponseEntity<List<Collection>> getCollectionsByUserID(@PathVariable long userId) {
        List<Collection> collections = userService.getCollectionsByUserID(userId);
        if (collections != null) {
            return new ResponseEntity<>(collections, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Gets a count of collections that belong to a certain user
     * @param userId The id of the user
     * @return HTTP OK if successful and the list of collections, HTTP BAD_REQUEST otherwise
     */
    @CrossOrigin
    @GetMapping("/users/{userId}/collections")
    public ResponseEntity<Integer> getCollectionCountByUserId(@PathVariable long userId) {
        int count = userService.getCollectionCountByUserId(userId);
        if (count != -1) {
            return new ResponseEntity<>(count, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes a users creates collection relationship
     * @param userId The user id of the user
     * @param collectionId The collection id of the collection
     * @return HTTP OK if successful, and BAD_REQUEST otherwise
     */
    @CrossOrigin
    @DeleteMapping("/users/{userId}/collections/{collectionId}")
    public ResponseEntity<Integer> deleteUserCreatesCollection(@PathVariable long userId, @PathVariable long collectionId) {
        int rowsAffected = userService.deleteUserCreatesCollection(userId, collectionId);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Creates a user listens to album relationship, marking where the user listened to the album, and adding a user
     * listens to song relationship for every song on the album
     * @param userId The user id of the user
     * @param albumId The album id of the album
     * @return HTTP OK if successful, and BAD_REQUEST otherwise
     */
    @CrossOrigin
    @PostMapping(value = "/users/{userId}/albums/{albumId}")
    public ResponseEntity<Integer> createUserListensToAlbum(@PathVariable long userId, @PathVariable long albumId) {
        int rowsAffected = userService.createUserListensToAlbum(userId,albumId);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }

    //UserListensToCollection RELATIONSHIP

    /**
     * Creates a user listens to collection relationship, and also updates the user listens to relationships for
     * the albums and songs in the collection
     * @param userId The id of the user
     * @param collectionId The id of the collection
     * @return HTTP OK if successful, and BAD_REQUEST otherwise
     */
    @CrossOrigin
    @PostMapping(value = "/users/{userId}/collections/listens/{collectionId}")
    public ResponseEntity<Integer> createUserListensToCollection(@PathVariable long userId, @PathVariable long collectionId) {
        int rowsAffected = userService.createUserListensToCollection(userId, collectionId);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes user listens to collection relationship, deprecatd
     * @param userId The id of the user
     * @param collectionId The id of the collection
     * @return HTTP OK if successful, and BAD_REQUEST otherwise
     */
    @CrossOrigin
    @DeleteMapping(value = "/users/{userId}/collections/listens/{collectionId}")
    public ResponseEntity<Integer> deleteUserListensToCollection(@PathVariable long userId, @PathVariable long collectionId) {
        int rowsAffected = userService.deleteUserListensToCollection(userId, collectionId);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    //UserFollowersUser RELATIONSHIP

    /**
     * Gets the list of the users that a user is following
     * @param userId The id of the user
     * @return HTTP OK and the list of users if successful, HTTP NOT_FOUND otherwise
     */
    @CrossOrigin
    @GetMapping("/users/{userId}/following")
    public ResponseEntity<List<User>> getUsersFollowing(@PathVariable long userId) {
        List<User> user = userService.getUsersFollowing(userId);
        if (user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates a user follows user relationship
     * @param userId The id of the user
     * @param friendId The id of the friend
     * @return HTTP CREATED if successful, HTTP BAD_REQUEST otherwise
     */
    @CrossOrigin
    @PostMapping(value = "/users/{userId}/following/{friendId}")
    public ResponseEntity createUserFollowsUser(@PathVariable long userId, @PathVariable long friendId) {
        int rowsAffected = userService.createUserFollowsUser(userId, friendId);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes a user follows user relationship
     * @param userId The id of the user who's unfollowing another
     * @param friendId The id of the user who the user is unfollowing
     * @return HTTP OK if successful, HTTP BAD_REQUEST otherwise
     */
    @CrossOrigin
    @DeleteMapping("/users/{userId}/following/{friendId}")
    public ResponseEntity<Integer> deleteUserFollowsUser(@PathVariable long userId, @PathVariable long friendId) {
        int rowsAffected = userService.deleteUserFollowsUser(userId, friendId);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Used to verify a user's password using SHA-256
     * @param userID The ID of the user
     * @param hashedPass The user's hashed password stored in the database
     * @param pass The password the user entered
     * @return True if the password matches the hashed password false otherwise
     */
    @CrossOrigin
    @GetMapping("/users/{userID}/verify/{hashedPass}/{pass}")
    public ResponseEntity<Boolean> verifyPassword(@PathVariable long userID, @PathVariable String hashedPass,
                                                  @PathVariable String pass) {
        return new ResponseEntity<>(userService.verifyPassword(userID, pass, hashedPass), HttpStatus.OK);
    }
}
