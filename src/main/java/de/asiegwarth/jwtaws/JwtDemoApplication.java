package de.asiegwarth.jwtaws;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import de.asiegwarth.jwtaws.service.UploadService;
import de.asiegwarth.jwtaws.upload.UploadProperties;

@SpringBootApplication
@EnableConfigurationProperties(UploadProperties.class)
public class JwtDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UploadService uploadService) {
		return (args) -> {
			uploadService.init();
		};
	}

}
