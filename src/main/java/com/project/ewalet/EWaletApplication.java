package com.project.ewalet;

import com.project.ewalet.property.FileStorageProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.project.ewalet.mapper")
@EnableScheduling
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class EWaletApplication {

	public static void main(String[] args) {
		SpringApplication.run(EWaletApplication.class, args);
	}

}
