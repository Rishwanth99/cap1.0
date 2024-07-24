package com.cap.cap10.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageControllers {

    @RequestMapping("/home")
    public String home(){

        System.out.println("home page loding");
        return "home";
    }


    @RequestMapping("/about")
    public String aboutPage(){

        System.out.println("About page loding");
        return "about";
    }


    @RequestMapping("/services")
    public String services(){

        System.out.println("services page loding");
        return "services";
    }

    @GetMapping("/contact")
    public String contactPage(){

     return "contact";
    }

    @GetMapping("/login")
    public String loginPage(){

     return "login";
    }

    @GetMapping("/register")
    public String registerPage(){

     return "register";
    }
}
