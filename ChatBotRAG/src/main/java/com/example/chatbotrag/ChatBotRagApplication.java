package com.example.chatbotrag;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

@SpringBootApplication(scanBasePackages = {
    "com.example.chatBotRAG", 
    "com.example.ChatBotRAG", 
    "com.example.chatbotrag"
}) 
public class ChatBotRagApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatBotRagApplication.class, args);
	}

}
