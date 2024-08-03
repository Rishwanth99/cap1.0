package com.cap.cap10.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cap.cap10.forms.contactForm;

@Controller
@RequestMapping("/user/contact")
public class ContactController {


    @RequestMapping("/add")
    public String addContactView(Model model) {

        contactForm contactForm = new contactForm();
        contactForm.setName("rinku");
        model.addAttribute("contactForm", contactForm);
        
        return"user/add_contact";
    }

}
