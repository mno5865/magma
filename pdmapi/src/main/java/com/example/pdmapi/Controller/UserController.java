package com.example.pdmapi.Controller;

import com.example.pdmapi.Model.User;
import com.example.pdmapi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}