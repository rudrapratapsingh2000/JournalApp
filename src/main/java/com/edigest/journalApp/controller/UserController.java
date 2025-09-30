package com.edigest.journalApp.controller;

import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.service.UserService;
import com.edigest.journalApp.service.WeatherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "User APIs", description = "User related APIs")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    WeatherService weatherService;

    @GetMapping("/getAll")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/findbyid_user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable ObjectId userId) {
        User old = userService.getById(userId).orElse(null);
        if (old != null) {
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PutMapping("/update_user/{userName}")
//    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName) {
//        User oldUser = userService.findByUserName(userName);
//        if (oldUser != null) {
//            oldUser.setUserName(user.getUserName());
//            oldUser.setPassword(user.getPassword());
//            userService.saveEntry(oldUser);
//            return new ResponseEntity<>(oldUser, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @PutMapping("/update_user")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User oldUser = userService.findByUserName(userName);
        oldUser.setUserName(user.getUserName());
        oldUser.setPassword(user.getPassword());
        userService.saveNewEntry(oldUser);
        return new ResponseEntity<>(oldUser, HttpStatus.OK);
    }

    @GetMapping("/greetings")
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        weatherService.getWeatherData();
        return new ResponseEntity<>("Hi " + userName, HttpStatus.OK);
    }

}
