package com.project.ewalet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.project.ewalet.mapper")
@EnableScheduling
public class EWaletApplication {

	public static void main(String[] args) {
		SpringApplication.run(EWaletApplication.class, args);
	}

}
