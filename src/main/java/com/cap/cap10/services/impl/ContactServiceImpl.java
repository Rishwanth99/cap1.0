package com.cap.cap10.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cap.cap10.entities.User;
import com.cap.cap10.entities.contact;
import com.cap.cap10.helpers.ResourceNotFoundException;
import com.cap.cap10.repositories.ContactRepo;
import com.cap.cap10.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {



    @Autowired
    private ContactRepo contactRepo;

    @Override
    public void delete(String id) {
         
        var contact = contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("contact not found with id"+id));

        contactRepo.delete(contact);

    }

    @Override
    public List<contact> getAll() {
       
        return contactRepo.findAll();
    }

    @Override
    public contact getById(String id) {

        return contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("contact not found with id"+id));
    }


    @Override
    public List<contact> getByUserId(String userId) {

        return contactRepo.findByUserId(userId);
    }

    @Override
    public contact save(contact contact) {

        String contactId = UUID.randomUUID().toString();

        contact.setId(contactId);

        return contactRepo.save(contact);
    }

   

    @Override
    public contact update(contact contact) {
        var oldContact = contactRepo.findById(contact.getId())
        .orElseThrow(()-> new ResourceNotFoundException("contact not found with id"+contact.getId()));
    
        oldContact.setName(contact.getName());
        oldContact.setEmail(contact.getEmail());
        oldContact.setPhoneNumber(contact.getPhoneNumber());
        oldContact.setAddress(contact.getAddress());
        oldContact.setDescription(contact.getDescription());
        oldContact.setWebsiteLink(contact.getWebsiteLink());
        oldContact.setLinkedInLink(contact.getLinkedInLink());
        oldContact.setFavorite(contact.isFavorite());
        oldContact.setPicture(contact.getPicture());
        oldContact.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
        //oldContact.setSocialLinks(contact.getSocialLinks());
    
        return contactRepo.save(oldContact);
    }

    @Override
    public Page<contact> getByUser(User user,int page,int size, String sortBy,String direction) {

        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page,size,sort);

        return contactRepo.findByUser(user,pageable);
    }

    @Override
    public Page<contact> searchByName(String name, int size, int page, String sortBy, String sortDir, User user) {
      
        Sort sort = sortDir.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        
        var pageable = PageRequest.of(page,size,sort);
    
        return contactRepo.findByUserAndNameContaining(user,name,pageable);
    }

    @Override
    public Page<contact> searchByEmail(String email, int size, int page, String sortBy, String sortDir, User user) {
        Sort sort = sortDir.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        
        var pageable = PageRequest.of(page,size,sort);
    
        return contactRepo.findByUserAndEmailContaining(user,email,pageable);
    }

    @Override
    public Page<contact> searchByPhoneNumber(String phoneNumber, int size, int page, String sortBy, String sortDir, User user) {
        
        Sort sort = sortDir.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        
        var pageable = PageRequest.of(page,size,sort);
    
        return contactRepo.findByUserAndPhoneNumberContaining(user,phoneNumber,pageable);
    }

    
    



}
