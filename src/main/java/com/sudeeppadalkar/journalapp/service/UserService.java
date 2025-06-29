package com.sudeeppadalkar.journalapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sudeeppadalkar.journalapp.utils.StringUtils;
import com.sudeeppadalkar.journalapp.entity.User;
import com.sudeeppadalkar.journalapp.repository.UserRepository;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void saveNewUser(User user) {
        user.setPassword(StringUtils.getEncodedString(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findEntryById(ObjectId id) {
        return userRepository.findById(id);
    }

    public User findUserByUserName(String userName) {

        return userRepository.findByUserName(userName);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authUserName = authentication.getName();

        return this.findUserByUserName(authUserName);
    }

    public User saveAdminUser(User user) {
        user.setPassword(StringUtils.getEncodedString(user.getPassword()));
        user.setUserName(user.getUserName());
        user.setRoles(Arrays.asList("ADMIN", "USER"));
        return userRepository.save(user);
    }

    public List<User> getAllAdminUsers() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles() != null && user.getRoles().contains("ADMIN"))
                .toList();
    }

}
