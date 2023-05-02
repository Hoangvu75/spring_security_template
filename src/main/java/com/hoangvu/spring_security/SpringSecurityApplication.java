package com.hoangvu.spring_security;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityApplication {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

}

