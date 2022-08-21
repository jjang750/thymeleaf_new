package com.aegisep.thymeleaf;

import com.aegisep.thymeleaf.config.WebSecurityConfig;
import com.aegisep.thymeleaf.repository.CustomUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class ThymeleafApplication {
	private static final Logger log = LoggerFactory.getLogger(ThymeleafApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ThymeleafApplication.class, args);
		String uuid = UUID.randomUUID().toString();
		log.info( "UUID : " + uuid );
	}

}
