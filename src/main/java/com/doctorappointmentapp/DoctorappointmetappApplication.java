package com.doctorappointmentapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.context.annotation.Configuration;


@CrossOrigin
@SpringBootApplication
public class DoctorappointmetappApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoctorappointmetappApplication.class, args);
	}
	
//	 @Bean
//	    public PasswordEncoder passwordEncoder() {
//	        return new BCryptPasswordEncoder();
//	    }

}
