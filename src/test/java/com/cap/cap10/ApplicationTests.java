package com.cap.cap10;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cap.cap10.services.EmailService;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService emailService;

	@Test
	void sendEmail() {
		emailService.sendEmail("rishwanth.molangur@gmail.com", 
		"Testing email service",
		 "hi, how are you? if you receive it, it means your email service is working fine");
	}

}
