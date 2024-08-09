package com.cap.cap10.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cap.cap10.entities.contact;
import com.cap.cap10.services.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ContactService contactService;
    //get contact data

    @GetMapping("/contact/{contactId}")
    public contact getContact(@PathVariable String contactId) {

        return contactService.getById(contactId);
    }
    

}
