package com.cap.cap10.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.cap.cap10.entities.User;
import com.cap.cap10.helpers.Helper;
import com.cap.cap10.services.UserService;


@ControllerAdvice
public class RootController {


        private final Logger logger = LoggerFactory.getLogger(RootController.class);


        @Autowired
        private UserService userService;
    


      @ModelAttribute
        public void userLoggedUser(Model model,Authentication authentication) {

            if (authentication==null) {
                return;
                
            }
            String username = Helper.getEmailOfLoggeduser(authentication);
        
        // getting user from database
            User user = userService.getUserByEmail(username);

            logger.info("user logged in "+ username);

         
            System.out.println(user.getName());
            System.out.println(user.getEmail());
            model.addAttribute("loggedUser",user);


        }

}
