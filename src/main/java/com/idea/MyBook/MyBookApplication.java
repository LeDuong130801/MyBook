package com.idea.MyBook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class MyBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBookApplication.class, args);
		log.info("Server đang chạy!!!");
	}

}
