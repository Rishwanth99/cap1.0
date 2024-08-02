package com.cap.cap10.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/user")
public class UserController {


        Logger logger = LoggerFactory.getLogger(UserController.class);

    //dashboard page

    @RequestMapping(value = "/dashboard", method=RequestMethod.GET)
    public String userDashBoard() {
        return "user/dashboard";
    }
    
    //profile page

    @RequestMapping(value = "/profile")
    public String userProfile(Model model,Authentication authentication) {

        
        return "user/profile";
    }

}
