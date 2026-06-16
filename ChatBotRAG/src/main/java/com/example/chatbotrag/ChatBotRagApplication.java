

package com.example.chatbotrag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.chatbotrag.Service.ChatBotRAGService;

@SpringBootApplication
public class ChatBotRagApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatBotRagApplication.class, args);
	}

}

// Moving the controller here guarantees Spring Boot instantiates it
@RestController
@RequestMapping("/chat")
class ChatBotRAGController {
    private final ChatBotRAGService service;

    public ChatBotRAGController(ChatBotRAGService service) {
        this.service = service;
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String message) {
        return service.ask(message);
    }
}