package com.goodvideo.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.goodvideo.processor"})
public class ProcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessApplication.class, args);
	}

}
