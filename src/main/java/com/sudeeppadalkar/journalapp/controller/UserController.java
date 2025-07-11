package com.sudeeppadalkar.journalapp.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sudeeppadalkar.journalapp.entity.User;
import com.sudeeppadalkar.journalapp.response.WeatherResponse;
import com.sudeeppadalkar.journalapp.service.UserService;
import com.sudeeppadalkar.journalapp.service.WeatherService;
import com.sudeeppadalkar.journalapp.utils.StringUtils;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/my-info")
    public ResponseEntity<User> getUser() {
        User user = userService.getLoggedInUser();

        if (user != null) {
            String greeting = "Hi, " + user.getUserName();

            user.setGreeting(greeting);
            return new ResponseEntity<>(user, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/greeting")
    public String greetMe(@RequestParam String cityString) {

        User user = userService.getLoggedInUser();
        WeatherResponse weatherResponse = weatherService.getWeather(cityString);
        String greeting = "";
        if (weatherResponse != null) {
            greeting = ", Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }
        return new String(user.getUserName() + greeting);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User newUser) {
        User user = userService.getLoggedInUser();
        if (user != null) {
            user.setUserName(newUser.getUserName() != null && !newUser.getUserName().equals("")
                    ? newUser.getUserName()
                    : user.getUserName());

            String newPassword = newUser.getPassword() != null && !newUser.getPassword().equals("")
                    ? newUser.getPassword()
                    : user.getPassword();

            user.setPassword(StringUtils.getEncodedString(newPassword));
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId id) {
        User user = userService.getLoggedInUser();

        if (user != null) {
            userService.deleteById(user.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}