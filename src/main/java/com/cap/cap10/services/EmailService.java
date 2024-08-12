package com.cap.cap10.services;

public interface EmailService {


    void sendEmail(String to, String subject, String body);

    void sendEmailWithAttachment();

    void sendEmailWithHtml();

}
