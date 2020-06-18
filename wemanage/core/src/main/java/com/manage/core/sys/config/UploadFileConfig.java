package com.manage.core.sys.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadFileConfig {

	// @Value("${file.upload-dir.default}")
	// private String uploadFolder;

	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// factory.setLocation(uploadFolder);
		// factory.setMaxFileSize("5MB");
		// factory.setMaxRequestSize("10MB");
		return factory.createMultipartConfig();
	}
}
