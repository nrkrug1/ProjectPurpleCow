package tech.fearless.purple.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"tech.fearless.purple"})
public class PurpleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PurpleApplication.class, args);
	}

}
