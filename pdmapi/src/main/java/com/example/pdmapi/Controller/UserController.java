package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.Collection;
import com.example.pdmapi.Model.User;
import com.example.pdmapi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping("/users/id/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

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

    @CrossOrigin
    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody User newUser) {
        userService.createUser(newUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin
    @PutMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@PathVariable long id, @RequestBody User updatedUser) {
        userService.updateUser(id, updatedUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

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

    @CrossOrigin
    @PutMapping(value = "/users/{userId}/songs/{songId}")
    public ResponseEntity<Integer> updateUserListensToSong(@PathVariable long userId, @PathVariable long songId) {
        int rowsAffected = userService.updateUserListensToSong(userId,songId);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

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

    @CrossOrigin
    @PostMapping(value = "/users/{userId}/albums/{albumId}")
    public ResponseEntity<Integer> createUserListensTAlbum(@PathVariable long userId, @PathVariable long albumId) {
        int rowsAffected = userService.createUserListensToAlbum(userId,albumId);
        if(rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rowsAffected,HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/users/{userId}/albums/{albumId}")
    public ResponseEntity<Timestamp> getUserAlbumLastPlayTime(@PathVariable long userId, @PathVariable long albumId) {
        Timestamp timestamp = userService.getUserAlbumLastPlayTime(userId,albumId);
        if(timestamp != null) {
            return new ResponseEntity<>(timestamp,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //UserListensToCollection RELATIONSHIP
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

    @CrossOrigin
    @GetMapping(value = "/users/{userId}/collections/listens/{collectionId}")
    public ResponseEntity<Timestamp> getUserCollectionPlayTime(@PathVariable long userId, @PathVariable long collectionId) {
        Timestamp timestamp = userService.getUserCollectionPlayTime(userId,collectionId);
        if(timestamp != null) {
            return new ResponseEntity<>(timestamp, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

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

    @CrossOrigin
    @DeleteMapping("/users/{userId}/following/{friendId}")
    public ResponseEntity<Integer> deleteUserFollowsUser(@PathVariable long userId, @PathVariable long friendId) {
        int rowsAffected = userService.deleteUserFollowsUser(userId, friendId);
        if (rowsAffected == 1) {
            return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
        } else  {
            rowsAffected = userService.deleteUserFollowsUser(friendId, userId);
            if (rowsAffected == 1) {
                return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
            }
            return new ResponseEntity<>(rowsAffected, HttpStatus.BAD_REQUEST);
        }
    }

}
