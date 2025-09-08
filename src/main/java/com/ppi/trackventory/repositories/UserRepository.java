package com.ppi.trackventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppi.trackventory.models.User;

public interface UserRepository extends JpaRepository<User,String> {

    public User findByUsername(String username);

}
