package com.ppi.trackventory.services;

import java.util.List;
import java.util.Optional;

import com.ppi.trackventory.models.User;

public interface UserService {

    public User saveUser(User user) throws Exception;

    public User getUser(String username);

    public void deleteUser(String id);

    public Optional<User> getUserById(String id);

    public List<User> getAllUsers();

}
