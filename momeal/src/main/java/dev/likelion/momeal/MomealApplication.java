package dev.likelion.momeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MomealApplication {

	public static void main(String[] args) {
		SpringApplication.run(MomealApplication.class, args);
	}

}
