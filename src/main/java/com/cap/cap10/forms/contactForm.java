package com.cap.cap10.forms;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class contactForm {

    private String name;

    private String email;

    private String phoneNumber;

    private String address;;

    private MultipartFile picture;

    private String description;

    private String websiteLink;

    private String linkedInLink;

    
}
