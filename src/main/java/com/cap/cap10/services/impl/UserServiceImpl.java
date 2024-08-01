package com.cap.cap10.services.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cap.cap10.entities.User;
import com.cap.cap10.helpers.AppConstants;
import com.cap.cap10.helpers.ResourceNotFoundException;
import com.cap.cap10.repositories.UserRepo;
import com.cap.cap10.services.UserService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public User saveUser(User user) {

        String userId = UUID.randomUUID().toString();
        
        user.setUserId(userId);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //set the user role

        user.setRoleList(List.of(AppConstants.ROLE_USER));
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserByid(String id) {
        
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
       
        User user2 = userRepo.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("user not found"));
        
        //update the user2 from user

        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProviders(user.getProviders());
        user2.setProviderUserId(user.getProviderUserId());


        User save = userRepo.save(user2);

        return Optional.ofNullable(save);


    }

    @Override
    public void deleteUser(String Id) {
       
        User user2 = userRepo.findById(Id).orElseThrow(()-> new ResourceNotFoundException("user not found"));

        userRepo.delete(user2);

    }

    @Override
    public boolean isUserExit(String userId) {
      
        User user2 = userRepo.findById(userId).orElse(null);

        return user2 != null ? true: false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
       
        User user2 = userRepo.findByEmail(email).orElse(null);

        return user2 != null ? true:false;
    
    }

    @Override
    public List<User> getAllUsers() {
        
    
        return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
       

        return userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found"));
    
    }

}
