package com.cap.cap10.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cap.cap10.entities.User;
import com.cap.cap10.helpers.Helper;
import com.cap.cap10.services.UserService;



@Controller
@RequestMapping("/user")
public class UserController {


        Logger logger = LoggerFactory.getLogger(UserController.class);

        @Autowired
        private UserService userService;
 

    //dashboard page

    @RequestMapping(value = "/dashboard", method=RequestMethod.GET)
    public String userDashBoard() {
        return "user/dashboard";
    }
    
    //profile page

    @RequestMapping(value = "/profile")
    public String userProfile(Authentication authentication) {

        //this line will give email of user
        String username = Helper.getEmailOfLoggeduser(authentication);
        
        // getting user from database
        User user = userService.getUserByEmail(username);

        logger.info("user logged in "+ username);
        return "user/profile";
    }

}
