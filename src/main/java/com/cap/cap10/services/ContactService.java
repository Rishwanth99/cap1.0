package com.cap.cap10.services;

import java.util.List;

import com.cap.cap10.entities.contact;

public interface ContactService {


    //save contact
    contact save(contact contact);

    //get contact
    List<contact> getAll();

    //update contact
    contact update(contact contact);

    //delete contact
    void delete(String id);

    //get contact by id
    contact getById(String id);

    
    //search contact
    List<contact> search(String name,String email,String phoneNumber);

    //get contact by userId
    List<contact> getByUserId(String userId);

    

}
