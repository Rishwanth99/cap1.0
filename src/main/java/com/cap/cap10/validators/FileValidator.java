package com.cap.cap10.validators;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile , MultipartFile>{

    private static final long MAX_FILE_SIZE = 1024 * 1024 * 2; // limit 5mb


    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

        if(file== null || file.isEmpty()){

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File cannot be empty").addConstraintViolation(); 
            return false;
        }
       
        System.out.println("File size : "+file.getSize());
        //file size
        if(file.getSize() > MAX_FILE_SIZE){

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size should be less than 2mb").addConstraintViolation();

            return false;
        }

        //resolution
        /* 
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
*/


        return true;
    }

}
