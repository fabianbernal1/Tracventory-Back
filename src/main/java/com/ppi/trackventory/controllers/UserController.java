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
import com.ppi.trackventory.models.UserWithPasswordDTO;
import com.ppi.trackventory.services.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/")
    public User saveUser(@RequestBody UserWithPasswordDTO dto) throws Exception{
    	dto.getUser().setPassword(dto.getPassword());
    	Optional<User> userData = userService.getUserById(dto.getUser().getId());
    	User userData2 = userService.getUser(dto.getUser().getUsername());
    	if (userData.isPresent() ) {
    		 throw new Exception("Usuario con este documento ya existe");
    	}
    	if (userData2 !=null) {
   		 throw new Exception("Usuario con este Nombre de Usuario ya existe");
    	}
        return userService.saveUser(dto.getUser(),false);
    }
    
    @PreAuthorize("hasAuthority('/users:r')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('/users:u')")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody UserWithPasswordDTO dto) throws Exception {
        Optional<User> userData = userService.getUserById(id);
        Boolean assign= false;
        if (userData.isPresent()) {
            User user = userData.get();
            user.setId(dto.getUser().getId()); 
            user.setUsername(dto.getUser().getUsername());
            user.setName(dto.getUser().getName());
            if (dto.getPassword() != null) {
            	user.setPassword(dto.getPassword());
            	assign= true;
            }
            user.setLastName(dto.getUser().getLastName());
            user.setSecondLastName(dto.getUser().getSecondLastName());
            user.setPhoneNumber(dto.getUser().getPhoneNumber());
            user.setDomain(dto.getUser().getDomain());
            user.setEnabled(dto.getUser().isEnabled());
            user.setProfile(dto.getUser().getProfile());

            User savedUser = userService.saveUser(user,assign);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PreAuthorize("hasAuthority('/users:r')")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") String id){
    	User user = userService.getUserById(id).orElse(null);
        return user;
    }
    
    @PreAuthorize("hasAuthority('/users:r')")
    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable("username") String username){
    	User user = userService.getUser(username);
        return user;
    }
    
    @PutMapping("/UpdatePassword/{username}")
    public User UpdatePassword(@PathVariable("username") String username) throws Exception{
    	User user = userService.getUser(username);
    	userService.updateUserPassword(user.getId());
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
