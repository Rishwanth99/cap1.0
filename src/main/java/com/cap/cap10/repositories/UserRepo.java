package com.cap.cap10.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cap.cap10.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User,String>{


    //db related function methods we can write here like queuy methods

    Optional<User> findByEmail(String email);

    
}
