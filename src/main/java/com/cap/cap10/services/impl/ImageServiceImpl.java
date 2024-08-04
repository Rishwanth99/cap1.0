package com.cap.cap10.services.impl;

import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cap.cap10.helpers.AppConstants;
import com.cap.cap10.services.imageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ImageServiceImpl  implements imageService{

    
    private Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    @Override
    public String getURLFromPublicId(String publicId) {

        return cloudinary.url().transformation( 
            new Transformation<>().width(AppConstants.CONTACT_IMAGE_WIDTH)
            .height(AppConstants.CONTACT_IMAGE_HEIGHT)
            .crop(AppConstants.CONTACT_IMAGE_FORMAT))
            .generate(publicId);
    }


    @Override
    public String uploadImage(MultipartFile contactImage, String fileName) {

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];

            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap("public_id", fileName));

            return this.getURLFromPublicId(fileName);

        } catch (IOException e) {

            e.printStackTrace();

            return null;
        }
    }   


}
