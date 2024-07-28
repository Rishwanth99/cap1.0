package com.cap.cap10.services;

import java.util.*;

import com.cap.cap10.entities.User;

public interface UserService {

    User saveUser(User user);
    Optional<User> getUserByid(String id);
    Optional<User> updateUser(User user);
    void deleteUser(String Id);
    boolean isUserExit(String userId);
    boolean isUserExistByEmail(String email);

    List<User> getAllUsers();

}
