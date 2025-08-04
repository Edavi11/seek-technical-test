package com.seek.test.seek_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.seek.test.seek_test.config.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class SeekTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeekTestApplication.class, args);
	}

}
