package com.cap.cap10.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cap.cap10.entities.User;
import com.cap.cap10.forms.UserForm;
import com.cap.cap10.helpers.MessageType;
import com.cap.cap10.helpers.message;
import com.cap.cap10.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


@Controller
public class PageControllers {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(){
        return "redirect:/home";
    }


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
    public String registerPage(Model model){

        UserForm userForm = new UserForm();
        model.addAttribute("userForm",userForm);

     return "register";
    }

    //process for handling the register 
    @RequestMapping(value = "/do-register", method=RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm ,BindingResult bindingResult ,HttpSession session){

        System.out.println("System processing the register deatils");

        System.out.println(userForm);

        //validation error
        if(bindingResult.hasErrors()){
            return "register";
        }

        // we have to fetch the data and validate it and save in the database
       /* 
        User user = User.builder()
        .name(userForm.getName())
        .email(userForm.getEmail())
        .password(userForm.getPassword())
        .phoneNumber(userForm.getPhoneNumber())
        .about(userForm.getAbout())
        .profilePic("https://www.pinterest.com/pin/default-avatar-profile-icon-of-social-media-user--947022627871095943/")
        .build();
        */ 


        User user = new User();

        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAbout(userForm.getAbout());
        user.setEnabled(false);
        user.setProfilePic("https://www.pinterest.com/pin/default-avatar-profile-icon-of-social-media-user--947022627871095943/");
 
        User savedUser = userService.saveUser(user);

        System.out.println("user saved....");

       message alertMessage =  message.builder().content("Registration Successful").type(MessageType.green).build();

        session.setAttribute("message",alertMessage);
        return "redirect:/register";
    }
}
