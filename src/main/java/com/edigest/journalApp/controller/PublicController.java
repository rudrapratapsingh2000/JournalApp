package com.edigest.journalApp.controller;

import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.service.CustomUserDetailsServiceImpl;
import com.edigest.journalApp.service.KafkaService;
import com.edigest.journalApp.service.UserService;
import com.edigest.journalApp.utils.JWTUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
@Tag(name = "Public APIs", description = "Public user related APIs")
public class PublicController {

    @Autowired
    KafkaService kafkaService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private UserService userService;

    @GetMapping("health-check")
    public String healthCheck() {
        return "Rudra Ok";
    }

    @PostMapping("/kafkaServiceCheck/{key}/{msg}")
    public String kafkaServiceCheck(@PathVariable String key, String msg) {
        kafkaService.sendMessage(key, msg);
        return "Rudra Ok";
    }

    @GetMapping("/getAll")
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping("/signup")
    public void createUser(@RequestBody User user) {
        userService.saveNewEntry(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Incorrect User name or Password", HttpStatus.BAD_REQUEST);
        }
    }
}
