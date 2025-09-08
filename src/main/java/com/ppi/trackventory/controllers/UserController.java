package com.ppi.trackventory.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ppi.trackventory.models.Rol;
import com.ppi.trackventory.models.User;
import com.ppi.trackventory.services.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PreAuthorize("hasAuthority('/users:c')")
    @PostMapping("/")
    public User saveUser(@RequestBody User user) throws Exception{
        return userService.saveUser(user);
    }
    
    @PreAuthorize("hasAuthority('/users:r')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('/users:u')")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody User updatedUser) throws Exception {
        Optional<User> userData = userService.getUserById(id);

        if (userData.isPresent()) {
            User user = userData.get();
            user.setId(updatedUser.getId()); 
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setName(updatedUser.getName());
            user.setLastName(updatedUser.getLastName());
            user.setSecondLastName(updatedUser.getSecondLastName());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setDomain(updatedUser.getDomain());
            user.setEnabled(updatedUser.isEnabled());
            user.setProfile(updatedUser.getProfile());

            User savedUser = userService.saveUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PreAuthorize("hasAuthority('/users:r')")
    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username){
    	User user = userService.getUserById(username).orElse(null);
        return user;
    }

    @PreAuthorize("hasAuthority('/users:d')")
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") String userId){
    	userService.deleteUser(userId);
    }
    
    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken() {     
            return ResponseEntity.ok().body("Token is valid.");
    }
}
