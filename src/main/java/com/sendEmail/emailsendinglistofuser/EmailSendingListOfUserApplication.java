package com.sendEmail.emailsendinglistofuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration 
public class EmailSendingListOfUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailSendingListOfUserApplication.class, args);
	}

}
