package org.zerock.foodnamdo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FoodNamdoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodNamdoApplication.class, args);
	}

}
