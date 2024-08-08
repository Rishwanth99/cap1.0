package com.cap.cap10.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cap.cap10.entities.User;
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

    
    //search contact by name
    Page<contact> searchByName(String name,int size,int page,String sortBy,String sortDir,User user);

    //search contact by email
    Page<contact> searchByEmail(String email,int size,int page,String sortBy,String sortDir,User user);

    //search conatch by phone number
    Page<contact> searchByPhoneNumber(String phoneNumber,int size,int page,String sortBy,String sortDir,User user);


    //get contact by userId
    List<contact> getByUserId(String userId);

    //get by user
    Page<contact> getByUser(User user,int page,int size,String sortBy,String sortDir);

    

}
