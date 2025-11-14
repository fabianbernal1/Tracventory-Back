package com.ppi.trackventory.services;

import java.util.List;
import java.util.Optional;

import com.ppi.trackventory.models.User;
import com.ppi.trackventory.models.UserWithPasswordDTO;

public interface UserService {

    public UserWithPasswordDTO saveUser(User user, Boolean assign) throws Exception;

    public User getUser(String username);

    public void deleteUser(String id);

    public Optional<User> getUserById(String id);

    public List<User> getAllUsers();

	UserWithPasswordDTO updateUserPassword(String userId) throws Exception;

}
