package com.cap.cap10.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cap.cap10.entities.User;
import com.cap.cap10.helpers.MessageType;
import com.cap.cap10.helpers.message;
import com.cap.cap10.repositories.UserRepo;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UserRepo userRepo;


    //verify email
    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token , HttpSession session){
        
    

            User user = userRepo.findByEmailToken(token).orElse(null);

            if(user != null){

            if(user.getEmailToken().equals(token)){

                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepo.save(user);    

                session.setAttribute("message", message.builder()
                .type(MessageType.green)
                .content("Successfully verified your email")
                .build());

                return "emailVerified_page";
            }
            else{

                session.setAttribute("message", message.builder()
                .type(MessageType.red)
                .content("Something went wrong ,Email verification failed!!")
                .build());

                return "error_page";
            }

            

        }

        session.setAttribute("message", message.builder()
        .type(MessageType.red)
        .content("Something went wrong ,Email verification failed!!")
        .build());


        return "error_page";   
    }

}
