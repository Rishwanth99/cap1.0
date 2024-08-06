package com.cap.cap10.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cap.cap10.entities.User;
import com.cap.cap10.entities.contact;

@Repository
public interface ContactRepo extends JpaRepository<contact, String> { 

    //find the contact by user

    Page<contact> findByUser(User user,Pageable pageable);


    @Query("select c from contact c where c.user.id = :userId")
    List<contact> findByUserId(@Param("userId") String userId);

}
