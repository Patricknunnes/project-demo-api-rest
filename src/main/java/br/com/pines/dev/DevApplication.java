package br.com.pines.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.Valid;

@SpringBootApplication
@Valid
public class DevApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevApplication.class, args);
		System.out.println(new BCryptPasswordEncoder()
				.encode("1234"));
	}

}
