package com.transon.securityDemo;

import com.cloudinary.Cloudinary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityDemoApplication.class, args);
	}

	@Bean
	public Cloudinary cloudinary(){
		String cloudName = "genson1808";
		String apiKey = "786551593633328";
		String apiSecret = "b33rVC3vCkwn2ZyxO3HPAImI9y4";
		Cloudinary cloudinary = null;
		Map<String,String> config = new HashMap();
		config.put("cloud_name", cloudName);
		config.put("api_key", apiKey);
		config.put("api_secret", apiSecret);
		cloudinary = new Cloudinary(config);
		return cloudinary;
	}
}
