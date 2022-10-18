package com.example.pdmapi.Controller;

import org.springframework.web.bind.annotation.*;
import com.example.pdmapi.Model.User;
import com.example.pdmapi.Service.UserService;

import java.util.List;

@RestController
@RequestMapping("")
public class UserController {

    private final UserService userService = new UserService();

    @RequestMapping(value="/users", method= RequestMethod.POST)
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @RequestMapping(value="/users", method=RequestMethod.GET)
    public List<User> readUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value="/users/{userId}", method=RequestMethod.PUT)
    public User readUsers(@PathVariable(value = "userId") Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @RequestMapping(value="/users/{userId}", method=RequestMethod.DELETE)
    public void deleteUsers(@PathVariable(value = "userId") Long id) {
        userService.deleteUser(id);
    }
}