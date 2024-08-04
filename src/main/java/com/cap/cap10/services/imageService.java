package com.cap.cap10.services;

import org.springframework.web.multipart.MultipartFile;

public interface imageService {

    String uploadImage(MultipartFile contactImage,String fileName);

    String getURLFromPublicId(String publicId);



}
