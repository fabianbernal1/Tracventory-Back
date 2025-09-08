package com.ppi.trackventory.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.models.Profile;
import com.ppi.trackventory.models.User;
import com.ppi.trackventory.repositories.ProfileRepository;
import com.ppi.trackventory.repositories.RolRepository;
import com.ppi.trackventory.repositories.UserRepository;
import com.ppi.trackventory.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public User saveUser(User user) throws Exception {
    	user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        User userLocal = userRepository.findByUsername(user.getUsername());
        if(userLocal != null && user.getId().isEmpty()){
            System.out.println("User already exists");
            throw new Exception("User already exist");
        }
        else{
        	if (user.getProfile() == null) {
        		Optional<Profile> profile = profileRepository.findByName("DEFAULT");
        		if(profile.isPresent()) {
        			user.setProfile(profile.get());
        		}else {
        			Profile profileDefault= new Profile();
        			profileDefault.setName("DEFAULT");
        			profileRepository.save(profileDefault);
        			user.setProfile(profileDefault);
        		}
        		
        	}
            userLocal = userRepository.save(user);
        }
        return userLocal;
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
    
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}