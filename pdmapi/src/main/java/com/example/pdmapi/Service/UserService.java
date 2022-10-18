package com.example.pdmapi.Service;

import com.example.pdmapi.Model.User;
import com.example.pdmapi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    // CREATE
    public User createUser(User user){
        return userRepository.save(user);
    }

    // READ
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // UPDATE
    public User updateUser(Long userId, User userDetails) {
        User user = userRepository.findById(userId).get();

        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEmail(userDetails.getEmail());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setCreationDate(userDetails.getCreationDate());
        user.setAccessDate(userDetails.getAccessDate());
        return userRepository.save(user);
    }

    // DELETE
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }




}