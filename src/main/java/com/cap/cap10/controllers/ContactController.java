package com.cap.cap10.controllers;



import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cap.cap10.entities.User;
import com.cap.cap10.entities.contact;
import com.cap.cap10.forms.ContactSearchForm;
import com.cap.cap10.forms.contactForm;
import com.cap.cap10.helpers.AppConstants;
import com.cap.cap10.helpers.Helper;
import com.cap.cap10.helpers.MessageType;
import com.cap.cap10.helpers.message;
import com.cap.cap10.services.ContactService;
import com.cap.cap10.services.UserService;
import com.cap.cap10.services.imageService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contact")
public class ContactController {


    Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private imageService imageService;

    @Autowired
    private UserService userService;


    @RequestMapping("/add")
    public String addContactView(Model model) {

        contactForm contactForm = new contactForm();
        
        model.addAttribute("contactForm", contactForm);
     
        return"user/add_contact";

    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute contactForm form,BindingResult result,Authentication authentication ,HttpSession session) {
        
    


        //validate form
        if(result.hasErrors()){

            session.setAttribute("message",message
                .builder()
                .type(MessageType.red)
                .content("Please enter valid data")
                .build()
                );
            return"user/add_contact";
        }

        //to get current user
        String username = Helper.getEmailOfLoggeduser(authentication);

        //get user from database
        User user = userService.getUserByEmail(username);

        //image processing

       // logger.info("image processing : {}",form.getContactImage().getOriginalFilename());   
        // upload the file

        String fileName = UUID.randomUUID().toString();

        String fileURL = imageService.uploadImage(form.getContactImage(),fileName);

        //convert from form to contact entity

        contact contact = new contact();
        contact.setName(form.getName());
        contact.setEmail(form.getEmail());
        contact.setPhoneNumber(form.getPhoneNumber());
        contact.setAddress(form.getAddress());
        contact.setPicture(fileURL);
        contact.setDescription(form.getDescription());
        contact.setWebsiteLink(form.getWebsiteLink());
        contact.setLinkedInLink(form.getLinkedInLink());
        contact.setFavorite(form.isFavorite());
        contact.setUser(user);// i am getting from database
        contact.setCloudinaryImagePublicId(fileName);
        contactService.save(contact);
        System.out.println(form.toString());

        session.setAttribute("message",message
                .builder()
                .type(MessageType.green)
                .content("Contact added successfully")
                .build()
                );

        return "redirect:/user/contact/add";
    }


    //view Contact
    @RequestMapping
    public String viewContactView(@RequestParam(value = "page",defaultValue = "0") int page
        ,@RequestParam(value = "size",defaultValue = "" + AppConstants.PAGE_SIZE) int size
        ,@RequestParam(value = "sortBy",defaultValue = "name") String sortBy
        ,@RequestParam(value = "direction",defaultValue = "asc") String direction
        ,Authentication authentication,Model model) {

        String userName = Helper.getEmailOfLoggeduser(authentication);

        User user = userService.getUserByEmail(userName);

        //load all contacts

        Page<contact> pageContact = contactService.getByUser(user,page,size,sortBy,direction);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("pageContact", pageContact);

        model.addAttribute("contactSearchForm", new ContactSearchForm());

        return"user/view_contact";
    }


    //search contact
    @RequestMapping("/search")
    public String searchContactView(@ModelAttribute ContactSearchForm contactSearchForm
    , @RequestParam(value = "page",defaultValue = "0") int page 
    ,@RequestParam(value = "size", defaultValue = "" + AppConstants.PAGE_SIZE) int size , 
    @RequestParam(value = "sortBy",defaultValue = "name") String sortBy
    ,@RequestParam(value = "direction",defaultValue = "asc") String direction,Model model,
     Authentication authentication) {

        logger.info("field : {}, keyword : {}",contactSearchForm.getField(),contactSearchForm.getKeyword());

        var user = userService.getUserByEmail(Helper.getEmailOfLoggeduser(authentication));

        Page<contact> pageContact = null;

        if(contactSearchForm.getField().equals("name")){
          pageContact = contactService.searchByName(contactSearchForm.getKeyword(),size,page,sortBy,direction ,user);
        }
        else if (contactSearchForm.getField().equals("email")){ 
          pageContact = contactService.searchByEmail(contactSearchForm.getKeyword(), size, page, sortBy, direction, user);
        }
        else if (contactSearchForm.getField().equals("phone")){
           pageContact = contactService.searchByPhoneNumber(contactSearchForm.getKeyword(), size, page, sortBy, direction, user);
        }

        logger.info("pageContact : {}", pageContact);

        model.addAttribute("pageContact", pageContact);

        model.addAttribute("contactSearchForm", contactSearchForm);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        

        return"user/search_contact"; 
    }

}
