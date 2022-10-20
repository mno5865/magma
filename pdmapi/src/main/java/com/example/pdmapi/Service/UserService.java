package com.example.pdmapi.Service;

import com.example.pdmapi.Model.User;
import com.example.pdmapi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    // CREATE
    public User createUser(User user) {

        return userRepository.save(user);
    }

    // READ
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long userId) {
        return userRepository.findById(userId);
    }

    // UPDATE
    public User updateUser(Long userId, User userDetails) {
        User user = userRepository.findById(userId).get();

        user.setUserID(userId);
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
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
