package com.harrison.ControllAPI.controllers;

import com.harrison.ControllAPI.model.User;
import com.harrison.ControllAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("create")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User userCreated = userService.createUser(user);
        return ResponseEntity.status(201).body(userCreated);
    }

    @GetMapping("userList")
    public ResponseEntity<List<User>> getUserList(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("byUsername")
    public ResponseEntity<User> getUserByUsername(@RequestParam("username") String username){
        User user = (User) userService.loadUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("byUserId={userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId){
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("byUserId={userId}")
    public ResponseEntity<User> updateUserById(@PathVariable("userId") Long userId, @RequestBody User changes){
        User user = userService.updateUserById(userId, changes);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("byUserId={userId}")
    public ResponseEntity<String> removeUserById(@PathVariable Long userId){
        String message = userService.removeUserById(userId);
        return ResponseEntity.ok(message);
    }

}
