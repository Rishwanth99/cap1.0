package com.cap.cap10.forms;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.cap.cap10.validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class contactForm {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email (xyz@example.com)")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "[0-9]{10}", message = "Invalid Phone Number (10 digits)")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    private String address;;

    @ValidFile(message = "Invalid File")
    private MultipartFile contactImage;

   
    private String description;


    private String websiteLink;

   
    private String linkedInLink;

    private boolean favorite;

    private String picture;



    
}
