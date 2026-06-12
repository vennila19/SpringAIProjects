package com.example.BasicChatBot;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RestController

public class BasicChatBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicChatBotApplication.class, args);
	}

	 @GetMapping("/chat")
    public String chat(@RequestParam String question) {
        return "Welcome to Chatbot" + question;
    }

	
}
